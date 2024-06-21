package dev.arbor.gtnn.api.recipe

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability
import com.gregtechceu.gtceu.api.recipe.content.Content
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.crafting.SizedIngredient

interface RecipeReplacer {
    fun setGTRecipeOutputs(
        outputs: Map<RecipeCapability<*>?, List<Content>>,
        item: ItemStack,
        sizedIngredient: SizedIngredient?
    ) {
        val contents = outputs[ItemRecipeCapability.CAP]!!
        for (content in contents) {
            val ing = content.content
            if (ing is Ingredient) {
                if (ing.items.any { itemStack: ItemStack -> itemStack.item === item.item }
                ) {
                    content.content = sizedIngredient
                }
            }
        }
    }
}
