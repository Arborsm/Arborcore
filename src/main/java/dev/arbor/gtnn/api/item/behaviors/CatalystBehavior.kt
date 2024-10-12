package dev.arbor.gtnn.api.item.behaviors

import com.gregtechceu.gtceu.api.item.ComponentItem
import com.gregtechceu.gtceu.api.item.component.IAddInformation
import com.gregtechceu.gtceu.api.item.component.IItemComponent
import net.minecraft.client.color.item.ItemColor
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.function.Supplier
import kotlin.math.min

class CatalystBehavior(private val maxDurability: Int) : IItemComponent, IMODurabilityBar, IAddInformation {
    private fun getCatalystStatsTag(itemStack: ItemStack): CompoundTag? {
        return itemStack.getTagElement("GTNN.CatalystStats")
    }

    private fun getOrCreateCatalystStatsTag(itemStack: ItemStack): CompoundTag {
        return itemStack.getOrCreateTagElement("GTNN.CatalystStats")
    }

    override fun getMaxDurability(itemStack: ItemStack): Int {
        return maxDurability
    }

    override fun getDamage(itemStack: ItemStack?): Int {
        val compound = itemStack?.let { getCatalystStatsTag(it) }
        if (compound == null || !compound.contains("Damage", Tag.TAG_ANY_NUMERIC.toInt())) {
            return 0
        }
        return compound.getInt("Damage")
    }

    override fun setDamage(itemStack: ItemStack?, damage: Int) {
        val compound = itemStack?.let { getOrCreateCatalystStatsTag(it) }
        compound?.putInt("Damage", min(maxDurability.toDouble(), damage.toDouble()).toInt())
    }

    override fun appendHoverText(
        itemStack: ItemStack, level: Level?, list: MutableList<Component>, tooltipFlag: TooltipFlag
    ) {
        val damage = this.getDamage(itemStack)
        list.add(
            Component.translatable(
                "metaitem.tool.tooltip.durability", maxDurability - damage, maxDurability
            )
        )
    }

    companion object {
        @OnlyIn(Dist.CLIENT)
        fun getItemStackColor(color: Int): Supplier<ItemColor?> {
            return Supplier { ItemColor { _: ItemStack?, i: Int -> if (i == 1) color else -1 } }
        }

        fun getBehaviour(itemStack: ItemStack): CatalystBehavior? {
            val componentItem = itemStack.item as? ComponentItem
            if (componentItem != null) {
                for (component in componentItem.components) {
                    if (component is CatalystBehavior) {
                        return component
                    }
                }
            }
            return null
        }
    }
}