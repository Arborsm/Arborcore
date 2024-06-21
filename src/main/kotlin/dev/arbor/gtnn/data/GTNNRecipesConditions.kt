package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType
import com.gregtechceu.gtceu.api.registry.GTRegistries
import dev.arbor.gtnn.api.recipe.NeutronActivatorCondition
import dev.arbor.gtnn.api.recipe.PlantCasingCondition

object GTNNRecipesConditions {
    val NA = GTRegistries.RECIPE_CONDITIONS.register(
        "neutron_activator_condition",
        RecipeConditionType(::NeutronActivatorCondition, NeutronActivatorCondition.CODEC)
    )!!

    val PLANT_CASING = GTRegistries.RECIPE_CONDITIONS.register(
        "plant_casing_condition",
        RecipeConditionType(::PlantCasingCondition, PlantCasingCondition.CODEC)
    )!!

    fun init() {
    }
}