package dev.arbor.gtnn.api.machine

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.SimpleGeneratorMachine
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.RecipeHelper
import com.gregtechceu.gtceu.api.recipe.logic.OCParams
import com.gregtechceu.gtceu.api.recipe.logic.OCResult
import it.unimi.dsi.fastutil.ints.Int2IntFunction

class GTNNGeneratorMachine(
    holder: IMachineBlockEntity, tier: Int, name: String, tankScalingFunction: Int2IntFunction, vararg args: Any
) : SimpleGeneratorMachine(holder, tier, tankScalingFunction, args) {
    private val efficiency = getEfficiency(tier, name)

    companion object {
        fun getEfficiency(tier: Int, name: String): Int {
            return when (name) {
                "naquadah_reactor" -> if (tier == 4) 80 else (tier - 5) * 50 + 100
                "rocket_engine" -> 80 - (tier - 4) * 10
                else -> tier * 20 + 100
            }
        }

        @Suppress("UNUSED_PARAMETER")
        fun nonParallel(machine: MetaMachine, recipe: GTRecipe, ocParams: OCParams, ocResult: OCResult): GTRecipe? {
            if (machine is GTNNGeneratorMachine) {
                val eut = RecipeHelper.getOutputEUt(recipe)
                val recipeModified = recipe.copy()
                RecipeHelper.setOutputEUt(
                    recipeModified, eut * machine.efficiency / 100
                )
                return recipeModified
            }
            return null
        }

        fun parallel(machine: MetaMachine, recipe: GTRecipe, ocParams: OCParams, ocResult: OCResult): GTRecipe? {
            val recipeModifier = nonParallel(machine, recipe, ocParams, ocResult)
            return recipeModifier?.let { recipeModifier(machine, it, ocParams, ocResult) }
        }
    }

}
