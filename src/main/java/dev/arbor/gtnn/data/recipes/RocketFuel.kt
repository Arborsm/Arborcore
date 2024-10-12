package dev.arbor.gtnn.data.recipes

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.common.data.GTItems
import com.gregtechceu.gtceu.common.data.GTMachines
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.common.data.GTRecipeTypes
import com.gregtechceu.gtceu.data.recipe.CustomTags
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper
import dev.arbor.gtnn.data.GTNNTagPrefix
import dev.arbor.gtnn.api.recipe.PlantCasingCondition
import dev.arbor.gtnn.data.GTNNMachines
import dev.arbor.gtnn.data.GTNNMaterials
import dev.arbor.gtnn.data.GTNNRecipeTypes
import dev.arbor.gtnn.data.GTNNRecipes
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object RocketFuel {
    fun init(provider: Consumer<FinishedRecipe>) {
        rocketEngine(provider)
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("black_matter")
            .inputItems(ChemicalHelper.get(TagPrefix.dust, GTMaterials.Lead, 3))
            .inputItems(ChemicalHelper.get(TagPrefix.dust, GTMaterials.Manganese, 5))
            .inputItems(ChemicalHelper.get(TagPrefix.dust, GTMaterials.Carbon, 12))
            .outputItems(ChemicalHelper.get(TagPrefix.dust, GTNNMaterials.BlackMatter, 20))
            .circuitMeta(13)
            .duration(82).EUt(GTValues.VA[GTValues.MV].toLong()).save(provider)

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("chemical_plant")
            .inputItems(GTMachines.HULL[GTValues.MV].asStack(4))
            .inputItems(ChemicalHelper.get(TagPrefix.plate, GTMaterials.AnnealedCopper, 16))
            .inputItems(ChemicalHelper.get(TagPrefix.pipeLargeFluid, GTMaterials.Polyethylene, 4))
            .inputItems(ChemicalHelper.get(TagPrefix.gear, GTMaterials.Aluminium, 4))
            .inputItems(ChemicalHelper.get(TagPrefix.frameGt, GTNNMaterials.BlackMatter, 4))
            .inputFluids(GTMaterials.BlackSteel.getFluid(1000))
            .outputItems(GTNNMachines.CHEMICAL_PLANT.asStack())
            .circuitMeta(19)
            .duration(GTNNRecipes.dur(120.0)).EUt(GTValues.VA[GTValues.MV].toLong()).save(provider)


        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("rp_1_mixed_fuel")
            .addCondition(GTNNRecipes.setPlantCasing(PlantCasingCondition.STAINLESS_STEEL))
            .inputFluids(GTMaterials.Oxygen.getFluid(2000))
            .inputFluids(GTNNMaterials.RP1RocketFuel.getFluid(500))
            .outputFluids(GTNNMaterials.RP1.getFluid(1000))
            .circuitMeta(1)
            .duration(GTNNRecipes.dur(15.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)

        GTRecipeTypes.DISTILLERY_RECIPES.recipeBuilder("rp_1_rocket_fuel")
            .inputFluids(GTNNMaterials.Kerosene.getFluid(20))
            .outputFluids(GTNNMaterials.RP1RocketFuel.getFluid(15))
            .circuitMeta(23)
            .duration(GTNNRecipes.dur(5.0)).EUt(GTValues.VA[GTValues.MV].toLong()).save(provider)

        GTRecipeTypes.DISTILLERY_RECIPES.recipeBuilder("kerosene")
            .inputFluids(GTMaterials.Diesel.getFluid(250))
            .outputFluids(GTNNMaterials.Kerosene.getFluid(150))
            .circuitMeta(23)
            .duration(16).EUt(GTValues.VA[GTValues.MV].toLong()).save(provider)

        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("dense_hydrazine_mixed_fuel")
            .addCondition(GTNNRecipes.setPlantCasing(PlantCasingCondition.TITANIUM))
            .inputFluids(GTMaterials.Methanol.getFluid(6000))
            .inputFluids(GTNNMaterials.Hydrazine.getFluid(4000))
            .outputFluids(GTNNMaterials.DenseHydrazineMixedFuel.getFluid(10000))
            .circuitMeta(2)
            .duration(GTNNRecipes.dur(30.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)

        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("hydrazine")
            .addCondition(GTNNRecipes.setPlantCasing(PlantCasingCondition.STEEL))
            .inputFluids(GTMaterials.Ammonia.getFluid(1000))
            .inputFluids(GTNNMaterials.HydrogenPeroxide.getFluid(1000))
            .outputFluids(GTNNMaterials.Hydrazine.getFluid(1000))
            .circuitMeta(2)
            .duration(GTNNRecipes.dur(10.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)

        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("hydrogen_peroxide_oxygen")
            .addCondition(GTNNRecipes.setPlantCasing(PlantCasingCondition.STEEL))
            .inputFluids(GTMaterials.Oxygen.getFluid(10000))
            .inputFluids(GTNNMaterials.EthylAnthraHydroQuinone.getFluid(5000))
            .inputFluids(GTNNMaterials.Anthracene.getFluid(50))
            .outputFluids(GTNNMaterials.HydrogenPeroxide.getFluid(5000))
            .outputFluids(GTNNMaterials.EthylAnthraQuinone.getFluid(5000))
            .circuitMeta(4)
            .duration(GTNNRecipes.dur(5.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)

        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("hydrogen_peroxide_air")
            .addCondition(GTNNRecipes.setPlantCasing(PlantCasingCondition.STEEL))
            .inputFluids(GTMaterials.Air.getFluid(20000))
            .inputFluids(GTNNMaterials.EthylAnthraHydroQuinone.getFluid(5000))
            .inputFluids(GTNNMaterials.Anthracene.getFluid(50))
            .outputFluids(GTNNMaterials.HydrogenPeroxide.getFluid(5000))
            .outputFluids(GTNNMaterials.EthylAnthraQuinone.getFluid(5000))
            .circuitMeta(4)
            .duration(GTNNRecipes.dur(30.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)

        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("ethyl_anthra_quinone")
            .addCondition(GTNNRecipes.setPlantCasing(PlantCasingCondition.ALUMINIUM))
            .inputItems(ChemicalHelper.get(TagPrefix.dust, GTNNMaterials.PhthalicAnhydride, 15))
            .inputFluids(GTMaterials.Ethylbenzene.getFluid(1000))
            .outputFluids(GTNNMaterials.EthylAnthraQuinone.getFluid(1000))
            .circuitMeta(4)
            .duration(GTNNRecipes.dur(15.0)).EUt(GTValues.VA[GTValues.MV].toLong()).save(provider)

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("phthalic_anhydride")
            .inputItems(ChemicalHelper.get(TagPrefix.dustSmall, GTNNMaterials.VanadiumPentoxide, 1))
            .inputFluids(GTMaterials.Naphthalene.getFluid(1000))
            .inputFluids(GTMaterials.Air.getFluid(1000))
            .outputItems(ChemicalHelper.get(TagPrefix.dust, GTNNMaterials.PhthalicAnhydride, 15))
            .duration(GTNNRecipes.dur(40.0)).EUt(GTValues.VA[GTValues.EV].toLong()).save(provider)

        GTRecipeTypes.BLAST_RECIPES.recipeBuilder("vanadium_pentoxide")
            .inputItems(ChemicalHelper.get(TagPrefix.dust, GTMaterials.Vanadium, 2))
            .inputFluids(GTMaterials.Oxygen.getFluid(5000))
            .outputItems(ChemicalHelper.get(TagPrefix.dust, GTNNMaterials.VanadiumPentoxide, 7))
            .circuitMeta(24)
            .blastFurnaceTemp(2500)
            .duration(GTNNRecipes.dur(10.0)).EUt(GTValues.VA[GTValues.MV].toLong()).save(provider)

        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("orange_metal_catalyst")
            .inputItems(ChemicalHelper.get(TagPrefix.dust, GTMaterials.Vanadium, 1))
            .inputItems(ChemicalHelper.get(TagPrefix.dust, GTMaterials.Palladium, 1))
            .outputItems(ChemicalHelper.get(GTNNTagPrefix.catalyst, GTNNMaterials.OrangeMetal, 1))
            .circuitMeta(32)
            .duration(GTNNRecipes.dur(8.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)

        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("ethyl_anthra_hydro_quinone")
            .addCondition(GTNNRecipes.setPlantCasing(PlantCasingCondition.ALUMINIUM))
            .inputFluids(GTNNMaterials.EthylAnthraQuinone.getFluid(1000))
            .inputFluids(GTMaterials.Hydrogen.getFluid(2000))
            .inputItems(ChemicalHelper.get(GTNNTagPrefix.catalyst, GTNNMaterials.OrangeMetal, 1))
            .outputFluids(GTNNMaterials.EthylAnthraHydroQuinone.getFluid(1000))
            .circuitMeta(4)
            .duration(GTNNRecipes.dur(40.0)).EUt(GTValues.VA[GTValues.MV].toLong()).save(provider)

        GTRecipeTypes.DISTILLATION_RECIPES.recipeBuilder("distill_coal_tar")
            .inputFluids(GTMaterials.CoalTar.getFluid(1000))
            .outputItems(TagPrefix.dustSmall, GTMaterials.Coke)
            .outputFluids(GTMaterials.Naphthalene.getFluid(400))
            .outputFluids(GTMaterials.HydrogenSulfide.getFluid(300))
            .outputFluids(GTMaterials.Creosote.getFluid(200))
            .outputFluids(GTMaterials.Phenol.getFluid(100))
            .outputFluids(GTNNMaterials.Anthracene.getFluid(50))
            .duration(80).EUt(GTValues.VA[GTValues.MV].toLong())
            .save(provider)

        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("methyl_hydrazine")
            .addCondition(GTNNRecipes.setPlantCasing(PlantCasingCondition.ALUMINIUM))
            .inputItems(ChemicalHelper.get(TagPrefix.dust, GTMaterials.Carbon, 1))
            .inputFluids(GTMaterials.Hydrogen.getFluid(2000))
            .inputFluids(GTNNMaterials.Hydrazine.getFluid(1000))
            .outputFluids(GTNNMaterials.MethylHydrazine.getFluid(1000))
            .circuitMeta(21)
            .duration(GTNNRecipes.dur(48.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)

        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("methylhydrazine_nitrate_rocket_fuel")
            .addCondition(GTNNRecipes.setPlantCasing(PlantCasingCondition.STAINLESS_STEEL))
            .inputFluids(GTNNMaterials.MethylHydrazine.getFluid(2000))
            .inputFluids(GTMaterials.NitricAcid.getFluid(1000))
            .outputFluids(GTNNMaterials.MethylhydrazineNitrateRocketFuel.getFluid(2000))
            .circuitMeta(3)
            .duration(GTNNRecipes.dur(45.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)
    }

    private fun rocketEngine(provider: Consumer<FinishedRecipe>) {
        GTNNRecipeTypes.ROCKET_ENGINE_RECIPES.recipeBuilder(GTNNMaterials.RP1.name)
            .inputFluids(GTNNMaterials.RP1.getFluid(4))
            .EUt(-GTValues.V[GTValues.EV])
            .duration(3)
            .save(provider)
        GTNNRecipeTypes.ROCKET_ENGINE_RECIPES.recipeBuilder(GTNNMaterials.DenseHydrazineMixedFuel.name)
            .inputFluids(GTNNMaterials.DenseHydrazineMixedFuel.getFluid(2))
            .EUt(-GTValues.V[GTValues.EV])
            .duration(3)
            .save(provider)
        GTNNRecipeTypes.ROCKET_ENGINE_RECIPES.recipeBuilder(GTNNMaterials.MethylhydrazineNitrateRocketFuel.name)
            .inputFluids(GTNNMaterials.MethylhydrazineNitrateRocketFuel.getFluid(1))
            .EUt(-GTValues.V[GTValues.EV])
            .duration(3)
            .save(provider)
        GTNNRecipeTypes.ROCKET_ENGINE_RECIPES.recipeBuilder(GTNNMaterials.UDMHRocketFuel.name)
            .inputFluids(GTNNMaterials.UDMHRocketFuel.getFluid(1))
            .EUt(-GTValues.V[GTValues.EV])
            .duration(6)
            .save(provider)
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "rocket_engine_ev", GTNNMachines.Rocket_Engine[GTValues.EV]!!
                .asStack(),
            "ABA", "CDC", "EFE",
            'A', GTItems.ELECTRIC_PISTON_EV,
            'B', CustomTags.EV_CIRCUITS,
            'C', GTItems.ELECTRIC_MOTOR_EV,
            'D', GTMachines.HULL[GTValues.EV].asStack(),
            'E', UnificationEntry(TagPrefix.gear, GTMaterials.TungstenSteel),  //todo
            'F', UnificationEntry(TagPrefix.cableGtDouble, GTMaterials.Aluminium)
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "rocket_engine_iv", GTNNMachines.Rocket_Engine[GTValues.IV]!!
                .asStack(),
            "ABA", "CDC", "EFE",
            'A', GTItems.ELECTRIC_PISTON_IV,
            'B', CustomTags.IV_CIRCUITS,
            'C', GTItems.ELECTRIC_MOTOR_IV,
            'D', GTMachines.HULL[GTValues.IV].asStack(),
            'E', UnificationEntry(TagPrefix.gear, GTMaterials.Titanium),  //todo
            'F', UnificationEntry(TagPrefix.cableGtDouble, GTMaterials.Platinum)
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "rocket_engine_luv", GTNNMachines.Rocket_Engine[GTValues.LuV]!!
                .asStack(),
            "ABA", "CDC", "EFE",
            'A', GTItems.ELECTRIC_PISTON_LUV,
            'B', CustomTags.LuV_CIRCUITS,
            'C', GTItems.ELECTRIC_MOTOR_LuV,
            'D', GTMachines.HULL[GTValues.LuV].asStack(),
            'E', UnificationEntry(TagPrefix.gear, GTMaterials.Zeron100),
            'F', UnificationEntry(TagPrefix.cableGtDouble, GTMaterials.Tungsten)
        )
    }
}
