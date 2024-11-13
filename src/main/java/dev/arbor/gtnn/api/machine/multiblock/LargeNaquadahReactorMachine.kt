package dev.arbor.gtnn.api.machine.multiblock

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability
import com.gregtechceu.gtceu.api.capability.recipe.IO
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.RecipeHelper
import com.gregtechceu.gtceu.api.recipe.content.Content
import com.gregtechceu.gtceu.api.recipe.logic.OCParams
import com.gregtechceu.gtceu.api.recipe.logic.OCResult
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.data.GTNNMaterials
import dev.arbor.gtnn.data.GTNNRecipeTypes
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps
import net.minecraft.MethodsReturnNonnullByDefault
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fluids.FluidStack
import javax.annotation.ParametersAreNonnullByDefault

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
class LargeNaquadahReactorMachine(holder: IMachineBlockEntity) : WorkableElectricMultiblockMachine(holder),
    IExplosionMachine {
    private val activeFluid: Map<Fluid, Int> = mapOf(
        GTMaterials.Caesium.getFluid() to 2,
        GTMaterials.Uranium235.getFluid() to 3,
        GTMaterials.Naquadah.getFluid() to 4
    )
    private val activeFluidCost: Map<Fluid, Int> = mapOf(
        GTMaterials.Caesium.getFluid() to 180,
        GTMaterials.Uranium235.getFluid() to 180,
        GTMaterials.Naquadah.getFluid() to 20
    )
    private val fuelFluids: List<Fluid> = listOf(
        GTNNMaterials.ThoriumBasedLiquidFuelExcited.getFluid(),
        GTNNMaterials.UraniumBasedLiquidFuelExcited.getFluid(),
        GTNNMaterials.PlutoniumBasedLiquidFuelExcited.getFluid()
    )
    private var hatchPartMachines: Set<FluidHatchPartMachine>? = null

    @Persisted
    private var hasAir = false

    @Persisted
    private var hasCool = false

    @Persisted
    private var activeFluidPower = 1
    private var lockFluid: Fluid? = null

    private fun checkHatch(machine: LargeNaquadahReactorMachine, duration: Int) {
        machine.hasCool = false
        machine.hasAir = false
        machine.activeFluidPower = 1
        for (hatch in machine.hatchPartMachines!!) {
            val tank = hatch.tank
            val io = tank.getHandlerIO()
            if (io == IO.IN || io == IO.BOTH) {
                for (i in tank.storages) {
                    val fluid = i.fluid
                    if (i.fluid == FluidStack.EMPTY) continue
                    checkLockFluid(machine, fluid)
                    active(machine, fluid, duration)
                    if (cool(fluid, duration)) machine.hasCool = true
                    if (air(fluid, duration)) machine.hasAir = true
                }
            }
            if (machine.hasCool && machine.hasAir && machine.activeFluidPower != 1) {
                return
            }
        }
    }

    private fun checkLockFluid(machine: LargeNaquadahReactorMachine, fluid: FluidStack) {
        if (machine.fuelFluids.contains(fluid.fluid)) {
            if (machine.lockFluid == null) {
                machine.lockFluid = fluid.fluid
            } else if (machine.lockFluid != fluid.fluid) {
                machine.doExplosion((4 * 32).toFloat())
            }
        }
    }


    //////////////////////////////////////
    //******     RECIPE LOGIC    *******//
    //////////////////////////////////////

    private fun air(fluid: FluidStack, duration: Int): Boolean {
        if (fluid.fluid.isSame(GTMaterials.LiquidAir.getFluid())) {
            val airAmount = 2400 / 20 * duration
            if (fluid.amount >= airAmount) {
                fluid.setAmount(fluid.amount - airAmount)
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    private fun active(machine: LargeNaquadahReactorMachine, fluid: FluidStack, duration: Int) {
        if (activeFluid.containsKey(fluid.fluid)) {
            val activeFluidCostI = activeFluidCost[fluid.fluid]!! / 20 * duration
            val activeFluidPower = activeFluid[fluid.fluid]
            if (machine.activeFluidPower <= activeFluidPower!! && fluid.amount >= activeFluidCostI) {
                machine.activeFluidPower = activeFluidPower
                fluid.setAmount(fluid.amount - activeFluidCostI)
            }
        }
    }

    private fun cool(fluid: FluidStack, duration: Int): Boolean {
        if (fluid.fluid.isSame(GTMaterials.PCBCoolant.getFluid())) {
            val coldAmount = 1000 / 20 * duration
            if (fluid.amount >= coldAmount) {
                fluid.setAmount(fluid.amount - coldAmount)
                return true
            }
        }
        return false
    }

    //////////////////////////////////////
    //***    Multiblock LifeCycle    ***//
    //////////////////////////////////////
    override fun onStructureFormed() {
        super.onStructureFormed()
        val matchContext = getMultiblockState().matchContext
        val ioMap = matchContext.getOrCreate<Long2ObjectMap<Any>>("ioMap", Long2ObjectMaps::emptyMap)
        // Cache the result of getParts() to prevent repetitive calls
        val parts = getParts()
        for (part in parts) {
            val io = ioMap.getOrDefault(part.self().pos.asLong(), IO.BOTH)
            if (io == IO.NONE) continue
            if (part is FluidHatchPartMachine) {
                hatchPartMachines = APartAbility.getOrDefault(hatchPartMachines, ::mutableSetOf)
                (hatchPartMachines as MutableSet).add(part)
            }
        }
    }

    override fun onStructureInvalid() {
        super.onStructureInvalid()
        hatchPartMachines = hashSetOf()
        lockFluid = null
    }

    override fun alwaysTryModifyRecipe(): Boolean {
        return true
    }

    fun getFinalPowerRate(): Int {
        var activeFluidPower = this.activeFluidPower
        if (hasCool) {
            activeFluidPower = (activeFluidPower * 1.5).toInt()
        }
        return activeFluidPower
    }

    //////////////////////////////////////
    //******       NBT SAVE      *******//
    //////////////////////////////////////
    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    companion object {
        private val MANAGED_FIELD_HOLDER =
            ManagedFieldHolder(LargeNaquadahReactorMachine::class.java, WorkableMultiblockMachine.MANAGED_FIELD_HOLDER)

        @Suppress("UNUSED_PARAMETER")
        fun modifyRecipe(machine: MetaMachine, recipe: GTRecipe, ocParams: OCParams, ocResult: OCResult): GTRecipe? {
            if (recipe.recipeType != GTNNRecipeTypes.LARGE_NAQUADAH_REACTOR_RECIPES) return null
            if (machine is LargeNaquadahReactorMachine) {
                val duration = recipe.duration
                machine.checkHatch(machine, duration)
                val copyRecipe = recipe.copy()
                if (!machine.hasAir) {
                    copyRecipe.tickOutputs.clear()
                    copyRecipe.outputs.clear()
                    return copyRecipe
                }
                var eut = RecipeHelper.getOutputEUt(copyRecipe)
                if (machine.hasCool) {
                    eut = (eut * 1.5).toLong()
                }
                eut *= machine.activeFluidPower
                copyRecipe.tickOutputs[EURecipeCapability.CAP] = listOf(Content(eut, 1, 1, 0, "", ""))
                return copyRecipe
            }
            return null
        }
    }
}
