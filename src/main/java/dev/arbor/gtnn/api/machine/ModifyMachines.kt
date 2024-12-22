package dev.arbor.gtnn.api.machine

import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifierList
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers
import com.gregtechceu.gtceu.common.data.GTRecipeTypes
import com.gregtechceu.gtceu.common.data.machines.GCYMMachines
import dev.arbor.gtnn.data.GTNNRecipeTypes
import net.minecraft.network.chat.Component

object ModifyMachines {
    fun init() {
        GTRecipeTypes.ASSEMBLER_RECIPES.setMaxTooltips(4)
        GTRecipeTypes.BREWING_RECIPES.setMaxTooltips(4)
        GTRecipeTypes.FLUID_HEATER_RECIPES.setMaxTooltips(4)
        modifyGTAssembly()
    }

    private fun modifyGTAssembly() {
        val largeAssembler = GCYMMachines.LARGE_ASSEMBLER
        val gtRecipeTypes = largeAssembler.recipeTypes?.toMutableList()
        gtRecipeTypes?.add(GTNNRecipeTypes.PRECISION_ASSEMBLY_RECIPES)
        val gtRecipeTypesArray = gtRecipeTypes?.toTypedArray()
        largeAssembler.recipeTypes = gtRecipeTypesArray
        largeAssembler.tooltipBuilder = largeAssembler.tooltipBuilder.andThen { _, components ->
            run {
                components.add(Component.translatable("gtnn.precision_assembly.tooltip.1"))
                components.add(Component.translatable("gtnn.precision_assembly.tooltip.2"))
            }
        }
        largeAssembler.setRecipeModifier(::assemblyRecipeModifier)
    }

    private fun assemblyRecipeModifier(machine: MetaMachine, gtRecipe: GTRecipe): ModifierFunction {
        return if (gtRecipe.recipeType == GTNNRecipeTypes.PRECISION_ASSEMBLY_RECIPES) {
            GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK).getModifier(machine, gtRecipe)
        } else {
            RecipeModifierList(
                GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK)
            ).getModifier(machine, gtRecipe)
        }
    }
}