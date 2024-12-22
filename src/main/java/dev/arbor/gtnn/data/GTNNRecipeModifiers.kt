package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.RecipeHelper
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier
import com.mojang.datafixers.util.Pair
import dev.arbor.gtnn.api.machine.feature.IParallelMachine
import javax.annotation.Nonnull
import kotlin.math.max
import kotlin.math.min

object GTNNRecipeModifiers {
    val GTNN_PARALLEL: RecipeModifier =
        RecipeModifier { machine: MetaMachine, recipe: GTRecipe -> gtnnParallel(machine, recipe).first }

    private fun gtnnParallel(
        machine: MetaMachine, @Nonnull recipe: GTRecipe?): Pair<ModifierFunction, Int> {
        if (machine is IMultiController && machine.isFormed) {
            if (machine is IParallelMachine) {
                var parallel: Int = machine.parallelNumber
                if (machine is WorkableElectricMultiblockMachine) {
                    parallel = min(
                        parallel.toDouble(),
                        max((machine.maxVoltage / RecipeHelper.getInputEUt(recipe)).toDouble(), 1.0)
                    ).toInt()
                }
                return Pair(ModifierFunction { recipe }, ParallelLogic.getParallelAmount(machine, recipe!!, parallel))
            }
        }
        return Pair(ModifierFunction.IDENTITY, 1)
    }
}
