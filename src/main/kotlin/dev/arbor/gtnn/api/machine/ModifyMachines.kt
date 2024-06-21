package dev.arbor.gtnn.api.machine

import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifierList
import com.gregtechceu.gtceu.data.machine.GCyMMachines
import com.gregtechceu.gtceu.data.recipe.GTRecipeModifiers
import com.gregtechceu.gtceu.data.recipe.GTRecipeTypes
import dev.arbor.gtnn.data.GTNNRecipeTypes
import net.minecraft.network.chat.Component

object ModifyMachines {
    fun init() {
        if (GTRecipeTypes.ASSEMBLER_RECIPES.maxTooltips == 3) GTRecipeTypes.ASSEMBLER_RECIPES.setMaxTooltips(4)
        if (GTRecipeTypes.BREWING_RECIPES.maxTooltips == 3) GTRecipeTypes.ASSEMBLER_RECIPES.setMaxTooltips(4)
        if (GTRecipeTypes.FLUID_HEATER_RECIPES.maxTooltips == 3) GTRecipeTypes.ASSEMBLER_RECIPES.setMaxTooltips(4)
        modifyGTAssembly()
    }

    private fun modifyGTAssembly() {
        val largeAssembler = GCyMMachines.LARGE_ASSEMBLER
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
        largeAssembler.setRecipeModifier(ModifyMachines::assemblyRecipeModifier)
    }

    private fun assemblyRecipeModifier(machine: MetaMachine, gtRecipe: GTRecipe): GTRecipe? {
        return if (gtRecipe.recipeType == GTNNRecipeTypes.PRECISION_ASSEMBLY_RECIPES) {
            GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK).apply(machine, gtRecipe)
        } else {
            RecipeModifierList(
                GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK)
            ).apply(machine, gtRecipe)
        }
    }
}