package dev.arbor.gtnn.api.machine.multiblock.part

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.capability.recipe.IO
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.TickableSubscription
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer
import com.gregtechceu.gtceu.common.machine.multiblock.part.EnergyHatchPartMachine
import com.lowdragmc.lowdraglib.syncdata.ISubscription
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import net.minecraft.MethodsReturnNonnullByDefault
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.BlockHitResult
import java.util.concurrent.ThreadLocalRandom
import javax.annotation.ParametersAreNonnullByDefault
import kotlin.math.abs

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
class NeutronAcceleratorMachine(
    holder: IMachineBlockEntity,
    tier: Int,
    vararg args: Any
) : EnergyHatchPartMachine(holder, tier, IO.IN, 1, args) {
    private var powerListener: ISubscription? = null
    private var powerSubs: TickableSubscription? = null
    //////////////////////////////////////
    //*****     Initialization    ******//
    //////////////////////////////////////
    override fun createEnergyContainer(vararg args: Any): NotifiableEnergyContainer {
        val maxCapacity = GTValues.V[tier] * 72L * amperage
        val container = NotifiableEnergyContainer.receiverContainer(
            this,
            maxCapacity,
            GTValues.V[tier],
            amperage.toLong()
        )
        container.setSideInputCondition { it == this.getFrontFacing() && this.isWorkingEnabled }
        container.setCapabilityValidator { it == null || it == this.getFrontFacing() }
        return container
    }

    fun consumeEnergy(): Long {
        return if (this.isWorkingEnabled && this.energyContainer.energyStored > 0) {
            abs(this.energyContainer.changeEnergy(-getMaxEUConsume())) * (10 + ThreadLocalRandom.current().nextInt(11))
        } else {
            0L
        }
    }

    override fun onLoad() {
        super.onLoad()
        powerListener = energyContainer.addChangedListener { this.updateSubscription() }
        updateSubscription()
    }

    override fun onUnload() {
        super.onUnload()
        if (powerListener != null) {
            powerListener!!.unsubscribe()
            powerListener = null
        }
    }

    private fun updateSubscription() {
        if (energyContainer.energyStored > 0) {
            powerSubs = subscribeServerTick(powerSubs) { this.energyChanged() }
        } else if (powerSubs != null) {
            powerSubs!!.unsubscribe()
            powerSubs = null
        }
    }

    private fun energyChanged() {
        if (energyContainer.energyStored > 0 && !isWorkingEnabled) {
            energyContainer.changeEnergy(-GTValues.V[tier])
        }
    }

    //////////////////////////////////////
    //**********     GUI     ***********//
    //////////////////////////////////////

    override fun shouldOpenUI(player: Player, hand: InteractionHand, hit: BlockHitResult): Boolean {
        return true
    }

    //////////////////////////////////////
    //**********     Data     **********//
    //////////////////////////////////////

    private fun getMaxEUConsume(): Long {
        return Math.round(GTValues.V[tier] * 0.8)
    }

    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    companion object {
        private val MANAGED_FIELD_HOLDER = ManagedFieldHolder(
            NeutronAcceleratorMachine::class.java,
            EnergyHatchPartMachine.MANAGED_FIELD_HOLDER
        )
    }
}
