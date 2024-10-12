package dev.arbor.gtnn.api.item.behaviors

import com.gregtechceu.gtceu.api.item.component.IDurabilityBar
import net.minecraft.world.item.ItemStack
import kotlin.math.max

interface IMODurabilityBar : IDurabilityBar {
    override fun getMaxDurability(itemStack: ItemStack): Int

    fun getDamage(itemStack: ItemStack?): Int

    fun setDamage(itemStack: ItemStack?, damage: Int)

    fun applyDamage(itemStack: ItemStack, damageApplied: Int) {
        if (getMaxDurability(itemStack) < 1) return
        val maxDurability = getMaxDurability(itemStack)
        val resultDamage = getDamage(itemStack) + damageApplied
        if (resultDamage >= maxDurability) {
            itemStack.shrink(1)
        } else {
            setDamage(itemStack, resultDamage)
        }
    }

    fun getDurability(itemStack: ItemStack): Int {
        return max((getMaxDurability(itemStack) - getDamage(itemStack)).toDouble(), 0.0).toInt()
    }

    override fun getDurabilityForDisplay(itemStack: ItemStack): Float {
        return getDurability(itemStack).toFloat() / getMaxDurability(itemStack)
    }

    override fun showEmptyBar(itemStack: ItemStack): Boolean {
        return getMaxDurability(itemStack) > 0
    }

    override fun isBarVisible(stack: ItemStack): Boolean {
        return getMaxDurability(stack) > 0
    }
}