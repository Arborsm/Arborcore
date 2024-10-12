package dev.arbor.gtnn.api.machine.multiblock

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.GTValues.VNF
import com.gregtechceu.gtceu.api.block.ICoilType
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.RecipeHelper
import com.gregtechceu.gtceu.api.recipe.logic.OCParams
import com.gregtechceu.gtceu.api.recipe.logic.OCResult
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.api.block.BlockMaps
import dev.arbor.gtnn.api.block.IChemicalPlantCasing
import dev.arbor.gtnn.api.block.ITierType
import dev.arbor.gtnn.api.machine.feature.IGTPPMachine
import dev.arbor.gtnn.api.machine.feature.IParallelMachine
import net.minecraft.MethodsReturnNonnullByDefault
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState
import javax.annotation.ParametersAreNonnullByDefault
import kotlin.math.max
import kotlin.math.min

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
class ChemicalPlantMachine(holder: IMachineBlockEntity) : WorkableElectricMultiblockMachine(holder), IParallelMachine,
    IGTPPMachine {
    @Persisted
    @DescSynced
    @RequireRerender
    var casingTier = 0
    private var coilLevel = 0
    private var tubeTier = 0
    private var voltageTier = 0

    @Persisted
    private val parallelStats = IParallelMachine.ParallelStats(this) {
        if (it is ChemicalPlantMachine) {
            return@ParallelStats max(((it.tubeTier) - 1) * 2 + 1, 1)
        }
        return@ParallelStats 1
    }

    //////////////////////////////////////
    //***    Multiblock LifeCycle    ***//
    //////////////////////////////////////

    override fun onStructureFormed() {
        super.onStructureFormed()
        val context = multiblockState.matchContext
        val coilType: Any = context.get("CoilType")
        val casingTier: Any = context.get("PlantCasing")
        val tubeTier: Any = context.get("Pipe")
        val voltageTier: Any = context.get("MachineCasing")
        this.coilLevel = APartAbility.getOrDefault(
            { coilType is ICoilType }, { (coilType as ICoilType).tier }, 0
        )
        this.tubeTier = APartAbility.getOrDefault(
            { tubeTier is ITierType }, { (tubeTier as ITierType).tier }, 0
        )
        this.voltageTier = APartAbility.getOrDefault(
            { voltageTier is ITierType }, { (voltageTier as ITierType).tier }, 0
        )
        this.casingTier = APartAbility.getOrDefault(
            { casingTier is IChemicalPlantCasing },
            { (casingTier as IChemicalPlantCasing).tier },
            0
        )
    }

    override fun onStructureInvalid() {
        super.onStructureInvalid()
        coilLevel = 0
        casingTier = 0
        tubeTier = 0
        voltageTier = 0
    }

    //////////////////////////////////////
    //******       NBT SAVE      *******//
    //////////////////////////////////////

    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    //////////////////////////////////////
    //******     RECIPE LOGIC    *******//
    //////////////////////////////////////

    override fun getRealRecipe(recipe: GTRecipe, params: OCParams, result: OCResult): GTRecipe? {
        if (voltageTier < GTValues.UHV && RecipeHelper.getRecipeEUtTier(recipe) > voltageTier) {
            return null
        }
        val modified = super.getRealRecipe(recipe, params, result)
        if (casingTier > 0) {
            val copied = if (recipe == modified) modified.copy() else modified
            if (copied != null) {
                copied.duration = (copied.duration / (1 + coilLevel * 0.5)).toInt()
            }
            return copied
        }
        return modified
    }


    //////////////////////////////////////
    // ***        Multiblock UI       ***//
    //////////////////////////////////////
    override fun addDisplayText(components: MutableList<Component?>) {
        super.addDisplayText(components)
        if (isFormed()) {
            components.add(
                Component.translatable(
                    "gtnn.multiblock.chemical_plant.heating_coil", coilLevel * 50
                )
            )
            components.add(
                Component.translatable(
                    "gtnn.multiblock.chemical_plant.parallel_level", tubeTier * 2
                )
            )
            components.add(
                Component.translatable(
                    "gtnn.multiblock.chemical_plant.tier", VNF[voltageTier + 1]
                )
            )
            components.add(
                Component.translatable(
                    "gtnn.multiblock.chemical_plant.chance", getChance()
                )
            )
        }
    }

    override var parallelNumber: Int
        get() = parallelStats.parallelNumber
        set(value) {
            parallelStats.parallelNumber = value
        }

    override val maxParallel: Int
        get() = parallelStats.maxParallel

    //////////////////////////////////////
    // ***       Multiblock Data      ***//
    //////////////////////////////////////
    fun getChance(): Int {
        return min((100 - 20 * (tubeTier - 1)).toDouble(), 100.0).toInt()
    }

    override fun getPartAppearance(
        part: IMultiPart, side: Direction, sourceState: BlockState, sourcePos: BlockPos
    ): BlockState? {
        val appearanceBlock: BlockState? = APPEARANCE_MAP[casingTier]
        return appearanceBlock ?: super.getPartAppearance(part, side, sourceState, sourcePos)
    }

    override fun getBlockAppearance(
        state: BlockState,
        level: BlockAndTintGetter,
        pos: BlockPos,
        side: Direction,
        sourceState: BlockState,
        sourcePos: BlockPos
    ): BlockState {
        val appearanceBlock: BlockState = getAppearance()
        return appearanceBlock
    }

    override fun locationGetter(): ResourceLocation {
        if (isFormed()) {
            return TEXTURE_MAP[casingTier]!!
        }
        return TEXTURE_MAP[1]!!
    }

    override fun getAppearance(): BlockState {
        if (isFormed()) {
            return APPEARANCE_MAP[casingTier]!!
        }
        return APPEARANCE_MAP[1]!!
    }

    companion object {
        private val TEXTURE_MAP: Map<Int, ResourceLocation> =
            BlockMaps.ALL_CP_CASINGS.keys
                .filterIsInstance<IChemicalPlantCasing>()
                .associate { it.tier to it.texture }

        private val APPEARANCE_MAP: Map<Int, BlockState> =
            BlockMaps.ALL_CP_CASINGS.keys
                .associate { it.tier to BlockMaps.ALL_CP_CASINGS[it]!!.get().defaultBlockState() }

        private val MANAGED_FIELD_HOLDER =
            ManagedFieldHolder(ChemicalPlantMachine::class.java, WorkableMultiblockMachine.MANAGED_FIELD_HOLDER)
    }
}
