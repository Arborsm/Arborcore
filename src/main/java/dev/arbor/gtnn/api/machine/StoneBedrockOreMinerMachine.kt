package dev.arbor.gtnn.api.machine

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.api.recipe.GTNNBedrockOreMinerLogic
import dev.arbor.gtnn.api.recipe.OresHelper.ALLOW_ITEM
import net.minecraft.world.item.Item

class StoneBedrockOreMinerMachine(holder: IMachineBlockEntity) :
    SimpleTieredMachine(holder, 0, GTMachineUtils.defaultTankSizeFunction) {

    init {
        this.importItems.setFilter { ALLOW_ITEM.contains(it.item) }
    }

    override fun createEnergyContainer(vararg args: Any?): NotifiableEnergyContainer {
        return NotifiableEnergyContainer.emitterContainer(this, 0, 0, 0)
    }

    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    override fun createRecipeLogic(vararg args: Any?): RecipeLogic {
        return GTNNBedrockOreMinerLogic(this)
    }

    fun getFilter(): Item? {
        if (!this.importItems.isEmpty()) {
            return this.importItems.getStackInSlot(0).item
        }
        return null
    }

    companion object {
        private val MANAGED_FIELD_HOLDER: ManagedFieldHolder =
            ManagedFieldHolder(StoneBedrockOreMinerMachine::class.java, SimpleTieredMachine.MANAGED_FIELD_HOLDER)
    }
}