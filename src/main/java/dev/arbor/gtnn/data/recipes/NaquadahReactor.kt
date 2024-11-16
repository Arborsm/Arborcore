package dev.arbor.gtnn.data.recipes

import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.common.data.GTItems
import com.gregtechceu.gtceu.common.data.GTMachines
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.data.recipe.CustomTags
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper
import dev.arbor.gtnn.data.GTNNMachines
import dev.arbor.gtnn.data.GTNNRecipeTypes
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object NaquadahReactor {
    fun init(consumer: Consumer<FinishedRecipe>) {
        machine(consumer)
        GTNNRecipeTypes.NAQUADAH_REACTOR_RECIPES.recipeBuilder("naquadah_reactor_I")
            .inputItems(TagPrefix.bolt, GTMaterials.NaquadahEnriched)
            .outputItems(TagPrefix.bolt, GTMaterials.Naquadah)
            .duration((50000000L / GTValues.V[GTValues.EV]).toInt())
            .EUt(-GTValues.V[GTValues.EV])
            .save(consumer)
        GTNNRecipeTypes.NAQUADAH_REACTOR_RECIPES.recipeBuilder("naquadah_reactor_II")
            .inputItems(TagPrefix.rod, GTMaterials.NaquadahEnriched)
            .outputItems(TagPrefix.rod, GTMaterials.Naquadah)
            .duration((250000000L / GTValues.V[GTValues.IV]).toInt())
            .EUt(-GTValues.V[GTValues.IV])
            .save(consumer)
        GTNNRecipeTypes.NAQUADAH_REACTOR_RECIPES.recipeBuilder("naquadah_reactor_III")
            .inputItems(TagPrefix.rodLong, GTMaterials.NaquadahEnriched)
            .outputItems(TagPrefix.rodLong, GTMaterials.Naquadah)
            .duration((500000000L / GTValues.V[GTValues.LuV]).toInt())
            .EUt(-GTValues.V[GTValues.LuV])
            .save(consumer)
        GTNNRecipeTypes.NAQUADAH_REACTOR_RECIPES.recipeBuilder("naquadah_reactor_IV")
            .inputItems(TagPrefix.bolt, GTMaterials.Naquadria)
            .outputItems(TagPrefix.bolt, GTMaterials.Naquadah)
            .duration((250000000L / GTValues.V[GTValues.ZPM]).toInt())
            .EUt(-GTValues.V[GTValues.ZPM])
            .save(consumer)
        GTNNRecipeTypes.NAQUADAH_REACTOR_RECIPES.recipeBuilder("naquadah_reactor_V")
            .inputItems(TagPrefix.rod, GTMaterials.Naquadria)
            .outputItems(TagPrefix.rod, GTMaterials.Naquadah)
            .duration((1000000000L / GTValues.V[GTValues.UV]).toInt())
            .EUt(-GTValues.V[GTValues.UV])
            .save(consumer)
    }

    private fun machine(provider: Consumer<FinishedRecipe>) {
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "naquadah_reactor_ev", GTNNMachines.NAQUADAH_REACTOR[GTValues.EV]!!
                .asStack(),
            "ABA", "CDC", "EBE",
            'A', UnificationEntry(TagPrefix.rod, GTMaterials.Uranium235),
            'B', CustomTags.IV_CIRCUITS,
            'C', GTItems.FIELD_GENERATOR_EV,
            'D', GTMachines.HULL[GTValues.EV].asStack(),
            'E', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Aluminium)
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "naquadah_reactor_iv", GTNNMachines.NAQUADAH_REACTOR[GTValues.IV]!!
                .asStack(),
            "ABA", "CDC", "EBE",
            'A', UnificationEntry(TagPrefix.rod, GTMaterials.Plutonium241),
            'B', CustomTags.LuV_CIRCUITS,
            'C', GTItems.FIELD_GENERATOR_IV,
            'D', GTMachines.HULL[GTValues.IV].asStack(),
            'E', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Tungsten)
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "naquadah_reactor_luv", GTNNMachines.NAQUADAH_REACTOR[GTValues.LuV]!!
                .asStack(),
            "ABA", "CDC", "EBE",
            'A', UnificationEntry(TagPrefix.rod, GTMaterials.Europium),
            'B', CustomTags.ZPM_CIRCUITS,
            'C', GTItems.FIELD_GENERATOR_LuV,
            'D', GTMachines.HULL[GTValues.LuV].asStack(),
            'E', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.HSSG)
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "naquadah_reactor_zpm", GTNNMachines.NAQUADAH_REACTOR[GTValues.ZPM]!!
                .asStack(),
            "ABA", "CDC", "EBE",
            'A', UnificationEntry(TagPrefix.rod, GTMaterials.Americium),
            'B', CustomTags.UV_CIRCUITS,
            'C', GTItems.FIELD_GENERATOR_ZPM,
            'D', GTMachines.HULL[GTValues.ZPM].asStack(),
            'E', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Naquadah)
        )
        if (GTCEuAPI.isHighTier()){
            VanillaRecipeHelper.addShapedRecipe(
            provider, true, "naquadah_reactor_uv", GTNNMachines.NAQUADAH_REACTOR[GTValues.UV]!!
                .asStack(),
            "ABA", "CDC", "EBE",
            'A', UnificationEntry(TagPrefix.rod, GTMaterials.NaquadahAlloy),
            'B', CustomTags.UHV_CIRCUITS,
            'C', GTItems.FIELD_GENERATOR_UV,
            'D', GTMachines.HULL[GTValues.UV].asStack(),
            'E', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.EnrichedNaquadahTriniumEuropiumDuranide)
        )
        }
    }
}
