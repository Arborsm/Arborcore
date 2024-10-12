package dev.arbor.gtnn.api.item.behaviors

import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.item.ComponentItem
import com.gregtechceu.gtceu.api.item.component.IAddInformation
import com.gregtechceu.gtceu.api.item.component.ICustomDescriptionId
import com.gregtechceu.gtceu.client.renderer.item.TagPrefixItemRenderer
import com.lowdragmc.lowdraglib.Platform
import net.minecraft.client.color.item.ItemColor
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class TagPrefixBehavior(private val tagPrefix: TagPrefix, val material: Material) :
    IAddInformation, ICustomDescriptionId {
    override fun onAttached(item: Item) {
        if (Platform.isClient()) {
            TagPrefixItemRenderer.create(
                item, tagPrefix.materialIconType(), material.materialIconSet
            )
        }
    }

    override fun appendHoverText(
        stack: ItemStack,
        level: Level?,
        tooltipComponents: List<Component>,
        isAdvanced: TooltipFlag
    ) {
        if (tagPrefix.tooltip() != null) {
            tagPrefix.tooltip()!!.accept(material, tooltipComponents)
        }
    }

    override fun getItemName(stack: ItemStack): Component? {
        return tagPrefix.getLocalizedName(material)
    }

    companion object {
        @OnlyIn(Dist.CLIENT)
        fun tintColor(): ItemColor {
            return ItemColor { itemStack: ItemStack, index: Int ->
                val behavior = getBehaviour(itemStack)
                if (behavior != null) {
                    return@ItemColor behavior.material.getLayerARGB(index)
                }
                -1
            }
        }

        private fun getBehaviour(itemStack: ItemStack): TagPrefixBehavior? {
            val componentItem = itemStack.item as? ComponentItem
            if (componentItem != null) {
                for (component in componentItem.components) {
                    if (component is TagPrefixBehavior) {
                        return component
                    }
                }
            }
            return null
        }
    }
}