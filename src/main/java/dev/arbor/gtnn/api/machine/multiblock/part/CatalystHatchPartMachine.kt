package dev.arbor.gtnn.api.machine.multiblock.part

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.capability.recipe.IO
import com.gregtechceu.gtceu.api.gui.GuiTextures
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.TickableSubscription
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture
import com.lowdragmc.lowdraglib.gui.widget.ImageWidget
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget
import com.lowdragmc.lowdraglib.gui.widget.Widget
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer
import com.lowdragmc.lowdraglib.side.item.IItemTransfer
import com.lowdragmc.lowdraglib.syncdata.ISubscription
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.api.item.behaviors.CatalystBehavior
import dev.arbor.gtnn.api.machine.multiblock.ChemicalPlantMachine
import net.minecraft.MethodsReturnNonnullByDefault
import net.minecraft.world.item.crafting.Ingredient
import java.util.function.Function
import javax.annotation.ParametersAreNonnullByDefault
import kotlin.math.ceil
import kotlin.math.sqrt

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
class CatalystHatchPartMachine(holder : IMachineBlockEntity) : TieredIOPartMachine(holder, GTValues.IV, IO.IN) {
    @Persisted
    val buffer : NotifiableItemStackHandler = NotifiableItemStackHandler(this, 16, IO.NONE, IO.BOTH)
    @Persisted
    val inventory: NotifiableItemStackHandler = createInventory()
    private var bufferSubs: ISubscription? = null
    private var inventorySubs: ISubscription? = null
    private var transferSubs: TickableSubscription? = null


    //////////////////////////////////////
    // *****     Initialization    ******//
    //////////////////////////////////////
    override fun onLoad() {
        super.onLoad()
        if (!isRemote) {
            bufferSubs = buffer.addChangedListener { this.onInventoryChanged() }
            inventorySubs = inventory.addChangedListener { this.onInventoryChanged() }
        }
    }

    override fun onUnload() {
        super.onUnload()
        bufferSubs?.unsubscribe()
        inventorySubs?.unsubscribe()
    }

    private fun createInventory(): NotifiableItemStackHandler {
        return object : NotifiableItemStackHandler(
            this, 16, IO.IN, IO.OUT,
            Function { slots: Int? ->
                object : ItemStackTransfer(slots!!) {
                    override fun getSlotLimit(slot: Int): Int {
                        return 1
                    }
                }
            }) {
            override fun getSlotNames(): Set<String> {
                return setOf(CATALYST)
            }

            override fun handleRecipeInner(
                io: IO,
                recipe: GTRecipe,
                left: MutableList<Ingredient>,
                slotName: String?,
                simulate: Boolean
            ): List<Ingredient>? {
                if (io != handlerIO) return left
                if (slotName != null && slotName != CATALYST) return left

                val capability = if (simulate) storage.copy() else storage
                val iterator = left.iterator()
                if (io == IO.IN) {
                    while (iterator.hasNext()) {
                        val ingredient = iterator.next()
                        SLOT_LOOKUP@ for (i in 0 until capability.slots) {
                            val itemStack = capability.getStackInSlot(i)
                            // Does not look like a good implementation, but I think it's at least equal to
                            // vanilla Ingredient::test
                            if (ingredient.test(itemStack)) {
                                val ingredientStacks = ingredient.items
                                for (ingredientStack in ingredientStacks) {
                                    if (ingredientStack.`is`(itemStack.item)) {
                                        val behavior: CatalystBehavior? = CatalystBehavior.getBehaviour(itemStack)
                                        var count = ingredientStack.count
                                        if (!simulate) {
                                            val chance = getChance()
                                            val u = (count * chance).toDouble()
                                            val e = (count * chance * (1 - chance)).toDouble()
                                            count = ceil(sqrt(e) * GTValues.RNG.nextGaussian() + u).toInt()
                                        }
                                        if (behavior != null) {
                                            val damage = count.coerceAtMost(behavior.getDurability(itemStack))
                                            behavior.applyDamage(itemStack, damage)
                                            ingredientStack.shrink(damage)
                                            if (itemStack.isEmpty || ingredientStack.isEmpty) {
                                                transferItems()
                                            }
                                        } else {
                                            val extracted = capability.extractItem(i, count, false)
                                            ingredientStack.shrink(extracted.count)
                                        }
                                        if (ingredientStack.isEmpty) {
                                            iterator.remove()
                                            break@SLOT_LOOKUP
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return if (left.isEmpty()) null else left
            }
        }
    }


    //////////////////////////////////////
    // ********   Subscriptions  ********//
    //////////////////////////////////////
    private fun onInventoryChanged() {
        if (isWorkingEnabled && !buffer.isEmpty) {
            transferSubs = subscribeServerTick(transferSubs) { this.transferItems() }
        } else unsubscribe()
    }

    private fun transferItems() {
        for (i in 0 until buffer.slots) {
            val stack = buffer.getStackInSlot(i)
            if (stack.isEmpty || !inventory.getStackInSlot(i).isEmpty) continue
            if (!buffer.extractItem(i, 1, true).isEmpty) {
                val copy = stack.copyWithCount(1)
                if (inventory.insertItemInternal(i, copy, true).isEmpty) {
                    buffer.extractItem(i, 1, false)
                    inventory.insertItemInternal(i, copy, false)
                }
            }
        }
        unsubscribe()
    }

    private fun unsubscribe() {
        transferSubs?.unsubscribe()
        transferSubs = null
    }

    //////////////////////////////////////
    // **********     GUI     ***********//
    //////////////////////////////////////
    override fun createUIWidget(): Widget {
        val group = WidgetGroup(0, 0, 18 * 8 + 31, 18 * 4 + 16)
        val slotsContainer = WidgetGroup(4, 4, 18 * 8 + 23, 18 * 4 + 8)
        slotsContainer.addWidget(ImageWidget(75, 31, 18, 18, SMALL_ARROW_OVERLAY))
        addSlots(slotsContainer, buffer, 4, 4, true)
        addSlots(slotsContainer, inventory, 91, 4, false)
        slotsContainer.setBackground(GuiTextures.BACKGROUND_INVERSE)
        group.addWidget(slotsContainer)
        return group
    }

    private fun addSlots(
        container: WidgetGroup, handler: IItemTransfer, x: Int, @Suppress("SameParameterValue") y: Int, canPutItems: Boolean
    ) {
        var index = 0
        for (j in 0..3) {
            for (i in 0..3) {
                container.addWidget(
                    SlotWidget(handler, index++, x + i * 18, y + j * 18, true, canPutItems)
                        .setBackground(GuiTextures.SLOT)
                )
            }
        }
    }


    //////////////////////////////////////
    // **********     Data     **********//
    //////////////////////////////////////
    private fun getChance(): Float {
        for (controller in controllers) {
            if (controller is ChemicalPlantMachine) {
                return controller.getChance() / 100f
            }
        }
        return 1f
    }

    override fun setWorkingEnabled(workingEnabled: Boolean) {
        super.setWorkingEnabled(workingEnabled)
        onInventoryChanged()
    }

    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    companion object {
        val MANAGED_FIELD_HOLDER = ManagedFieldHolder(CatalystHatchPartMachine::class.java, TieredIOPartMachine.MANAGED_FIELD_HOLDER)
        val SMALL_ARROW_OVERLAY = ResourceTexture("gtnn:textures/gui/arrows/small_arrow_overlay.png")
        const val CATALYST: String = "catalyst"
    }
}