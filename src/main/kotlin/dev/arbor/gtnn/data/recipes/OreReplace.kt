package dev.arbor.gtnn.data.recipes

import dev.arbor.gtnn.api.recipe.RecipeReplacer
import net.minecraft.world.item.crafting.Recipe

object OreReplace {
    private val INSTANCE = OreReplace()

    class OreReplace : RecipeReplacer

    @JvmStatic
    fun init(recipe: Recipe<*>) {
    }
}
