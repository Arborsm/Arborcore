package dev.arbor.gtnn.data.recipes

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.common.data.GTRecipeTypes
import dev.arbor.gtnn.data.GTNNTagPrefix
import dev.arbor.gtnn.data.GTNNMaterials
import dev.arbor.gtnn.data.GTNNRecipeTypes
import dev.arbor.gtnn.data.GTNNRecipes
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object NaquadahLine {
    fun init(consumer: Consumer<FinishedRecipe>) {
        remove(consumer)
        epLine(consumer)
    }

    private fun remove(consumer: Consumer<FinishedRecipe>) {
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("impure_enriched_naquadah_solution_separation").save(consumer)
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("acidic_enriched_naquadah_separation").save(consumer)
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("impure_naquadria_solution_separation").save(consumer)
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("acidic_naquadria_solution_separation").save(consumer)
    }

    // Author: Magic_Sweepy
    private fun epLine(consumer: Consumer<FinishedRecipe>) {
        //  Impure Enriched Naquadah Solution + Fluorine -> Hexafluoride Enriched Naquadah Solution
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("hexafluoride_enriched_naquadah_solution")
            .inputFluids(GTMaterials.ImpureEnrichedNaquadahSolution.getFluid(1000))
            .inputFluids(GTMaterials.Fluorine.getFluid(6000))
            .outputFluids(GTNNMaterials.HexafluorideEnrichedNaquadahSolution.getFluid(1000))
            .EUt(GTValues.VA[GTValues.IV].toLong())
            .duration(400)
            .save(consumer)

        //  Hexafluoride Enriched Naquadah Solution + Xenon -> Xenon Hexafluoro Enriched Naquadate
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("xenon_hexafluoro_enriched_naquadate")
            .inputFluids(GTNNMaterials.HexafluorideEnrichedNaquadahSolution.getFluid(1000))
            .inputFluids(GTMaterials.Xenon.getFluid(1000))
            .outputFluids(GTNNMaterials.XenonHexafluoroEnrichedNaquadate.getFluid(1000))
            .EUt(GTValues.VA[GTValues.HV].toLong())
            .duration(200)
            .cleanroom(CleanroomType.CLEANROOM)
            .save(consumer)

        //  Palladium on Carbon + Gold Trifluoride + Xenon Hexafluoro Enriched Naquadate + Fluoroantimonic Acid + Hydrogen -> Enriched Naquadah Solution + Enriched Naquadah Residue Solution + Hydrofluoric Acid
        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("enriched_naquadah_residue_solution")
            .inputItems(GTNNTagPrefix.catalyst, GTNNMaterials.PalladiumOnCarbon, 1)
            .inputItems(TagPrefix.dust, GTNNMaterials.GoldTrifluoride, 4)
            .inputFluids(GTNNMaterials.XenonHexafluoroEnrichedNaquadate.getFluid(1000))
            .inputFluids(GTMaterials.FluoroantimonicAcid.getFluid(1000))
            .inputFluids(GTMaterials.Hydrogen.getFluid(9000))
            .outputFluids(GTMaterials.EnrichedNaquadahSolution.getFluid(1000))
            .outputFluids(GTNNMaterials.EnrichedNaquadahResidueSolution.getFluid(1000))
            .outputFluids(GTMaterials.HydrofluoricAcid.getFluid(8000))
            .EUt(GTValues.VA[GTValues.LuV].toLong())
            .duration(1200)
            .addCondition(GTNNRecipes.setPlantCasing(6))
            .save(consumer)

        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("palladium_on_carbon")
            .inputItems(TagPrefix.dust, GTMaterials.Carbon)
            .inputItems(TagPrefix.dust, GTMaterials.Palladium)
            .outputItems(GTNNTagPrefix.catalyst, GTNNMaterials.PalladiumOnCarbon)
            .circuitMeta(32)
            .duration(GTNNRecipes.dur(8.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(consumer)

        //  Enriched Naquadah Residue Solution -> Trinium Sulfide + Xenoauric Fluoroantimonic Acid
        GTNNRecipeTypes.DEHYDRATOR_RECIPES.recipeBuilder("xenoauric_fluoroantimonic_acid")
            .inputFluids(GTNNMaterials.EnrichedNaquadahResidueSolution.getFluid(2000))
            .outputItems(TagPrefix.dust, GTMaterials.TriniumSulfide)
            .outputFluids(GTNNMaterials.XenoauricFluoroantimonicAcid.getFluid(1000))
            .EUt(GTValues.VA[GTValues.EV].toLong())
            .duration(240)
            .save(consumer)

        //  Xenoauric Fluoroantimonic Acid Cycle
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder("xenoauric_fluoroantimonic_acid")
            .inputFluids(GTNNMaterials.XenoauricFluoroantimonicAcid.getFluid(1000))
            .outputItems(TagPrefix.dust, GTMaterials.Gold)
            .outputItems(TagPrefix.dust, GTMaterials.AntimonyTrifluoride)
            .outputFluids(GTMaterials.Xenon.getFluid(1000))
            .outputFluids(GTMaterials.HydrofluoricAcid.getFluid(3000))
            .EUt(GTValues.VA[GTValues.IV].toLong())
            .duration(1200)
            .save(consumer)

        //  Gold Chloride + Bromine Trifluoride -> Gold Trifluoride + Bromine + Chlorine
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gold_chloride")
            .inputFluids(GTNNMaterials.GoldChloride.getFluid(1000))
            .inputFluids(GTNNMaterials.BromineTrifluoride.getFluid(2000))
            .outputItems(TagPrefix.dust, GTNNMaterials.GoldTrifluoride, 8)
            .outputFluids(GTMaterials.Bromine.getFluid(2000))
            .outputFluids(GTMaterials.Chlorine.getFluid(6000))
            .EUt(GTValues.VA[GTValues.MV].toLong())
            .duration(200)
            .save(consumer)

        //  Bromine + Fluorine -> Bromine Trifluoride
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("bromine_trifluoride")
            .inputFluids(GTMaterials.Bromine.getFluid(1000))
            .inputFluids(GTMaterials.Fluorine.getFluid(3000))
            .circuitMeta(3)
            .outputFluids(GTNNMaterials.BromineTrifluoride.getFluid(1000))
            .EUt(GTValues.VA[GTValues.LV].toLong())
            .duration(120)
            .save(consumer)

        //  Gold + Chlorine -> Gold Chloride
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder("gold_chloride")
            .inputItems(TagPrefix.dust, GTMaterials.Gold, 2)
            .inputFluids(GTMaterials.Chlorine.getFluid(6000))
            .outputFluids(GTNNMaterials.GoldChloride.getFluid(1000))
            .EUt(GTValues.VA[GTValues.MV].toLong())
            .duration(360)
            .save(consumer)

        //  Impure Naquadria Solution + Fluorine -> Hexafluoride Naquadria Solution
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("hexafluoride_naquadria_solution")
            .inputFluids(GTMaterials.ImpureNaquadriaSolution.getFluid(1000))
            .inputFluids(GTMaterials.Fluorine.getFluid(6000))
            .outputFluids(GTNNMaterials.HexafluorideNaquadriaSolution.getFluid(1000))
            .EUt(GTValues.VA[GTValues.IV].toLong())
            .duration(400)
            .save(consumer)

        //  HexafluorideNaquadriaSolution + Radon Difluoride -> Radon Naquadria Octafluoride + Naquadria Residue Solution
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("radon_naquadria_octafluoride")
            .inputFluids(GTNNMaterials.HexafluorideNaquadriaSolution.getFluid(1000))
            .inputFluids(GTNNMaterials.RadonDifluoride.getFluid(1000))
            .outputFluids(GTNNMaterials.RadonNaquadriaOctafluoride.getFluid(1000))
            .outputFluids(GTNNMaterials.NaquadriaResidueSolution.getFluid(1000))
            .EUt(GTValues.VA[GTValues.HV].toLong())
            .duration(800)
            .cleanroom(CleanroomType.CLEANROOM)
            .save(consumer)

        //  Radon + Fluorine -> Radon Difluoride
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("radon_naquadria")
            .inputFluids(GTMaterials.Radon.getFluid(1000))
            .inputFluids(GTMaterials.Fluorine.getFluid(2000))
            .circuitMeta(2)
            .outputFluids(GTNNMaterials.RadonDifluoride.getFluid(1000))
            .EUt(GTValues.VA[GTValues.MV].toLong())
            .duration(100)
            .save(consumer)

        //  Naquadria Residue Solution -> Indium Phosphide + Naquadria Solution
        // todo BURNER_REACTOR_RECIPES
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder("naquadria_solution")
            .inputFluids(GTNNMaterials.NaquadriaResidueSolution.getFluid(2000))
            .outputItems(TagPrefix.dust, GTMaterials.IndiumPhosphide)
            .outputFluids(GTMaterials.NaquadriaSolution.getFluid(1000))
            .EUt(GTValues.VA[GTValues.IV].toLong())
            .duration(1200)
            .blastFurnaceTemp(880)
            .save(consumer)

        //  Naquadria Solution -> Sulfur + Naquadria Waste + Sulfur dust
        GTNNRecipeTypes.DEHYDRATOR_RECIPES.recipeBuilder("naquadria_waste")
            .inputFluids(GTMaterials.NaquadriaSolution.getFluid(3000))
            .outputItems(TagPrefix.dust, GTMaterials.Sulfur, 6)
            .outputFluids(GTMaterials.NaquadriaWaste.getFluid(1000))
            .EUt(GTValues.VA[GTValues.HV].toLong())
            .duration(100)
            .save(consumer)

        //  Caesium Xenontrioxide Fluoride
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("caesium_xenontrioxide_fluoride")
            .inputFluids(GTNNMaterials.CaesiumFluoride.getFluid(1000))
            .inputFluids(GTNNMaterials.XenonTrioxide.getFluid(1000))
            .outputFluids(GTNNMaterials.CaesiumXenontrioxideFluoride.getFluid(1000))
            .EUt(GTValues.VA[GTValues.MV].toLong())
            .duration(480)
            .save(consumer)

        //  Caesium + Fluorine -> Cesium Fluoride
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("caesium_fluoride")
            .inputItems(TagPrefix.dust, GTMaterials.Caesium)
            .inputFluids(GTMaterials.Fluorine.getFluid(1000))
            .circuitMeta(1)
            .outputFluids(GTNNMaterials.CaesiumFluoride.getFluid(1000))
            .EUt(GTValues.VA[GTValues.MV].toLong())
            .duration(100)
            .save(consumer)

        //  Xenon + Oxygen -> Xenon Trioxide
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("xenon_trioxide")
            .inputFluids(GTMaterials.Xenon.getFluid(1000))
            .inputFluids(GTMaterials.Oxygen.getFluid(3000))
            .outputFluids(GTNNMaterials.XenonTrioxide.getFluid(1000))
            .EUt(GTValues.VA[GTValues.MV].toLong())
            .duration(100)
            .save(consumer)

        //  Radon Naquadria Octafluoride + Caesium Xenontrioxide Fluoride -> Naquadria Caesium Xenonnonfluoride + Radon Trioxide
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("naquadria_caesium_xenonnonfluoride")
            .inputFluids(GTNNMaterials.RadonNaquadriaOctafluoride.getFluid(1000))
            .inputFluids(GTNNMaterials.CaesiumXenontrioxideFluoride.getFluid(1000))
            .outputFluids(GTNNMaterials.NaquadriaCaesiumXenonnonfluoride.getFluid(1000))
            .outputFluids(GTNNMaterials.RadonTrioxide.getFluid(1000))
            .EUt(GTValues.VA[GTValues.IV].toLong())
            .duration(800)
            .cleanroom(CleanroomType.CLEANROOM)
            .save(consumer)

        //  Naquadria Caesium Xenonnonfluoride + Nitryl Fluoride -> Naquadria Caesiumfluoride + Nitrosonium Octafluoroxenate
        // todo CRYOGENIC_REACTOR_RECIPES
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("nitrosonium_octafluoroxenate")
            .inputFluids(GTNNMaterials.NaquadriaCaesiumXenonnonfluoride.getFluid(1000))
            .inputFluids(GTNNMaterials.NitrylFluoride.getFluid(2000))
            .outputFluids(GTNNMaterials.NaquadriaCaesiumfluoride.getFluid(1000))
            .outputFluids(GTNNMaterials.NitrosoniumOctafluoroxenate.getFluid(1000))
            .EUt(GTValues.VA[GTValues.EV].toLong())
            .duration(400) // .temperature(75)
            .cleanroom(CleanroomType.CLEANROOM)
            .save(consumer)

        //  Nitrogen Dioxide + Fluorine -> Nitryl Fluoride
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("nitryl_fluoride")
            .inputFluids(GTMaterials.NitrogenDioxide.getFluid(1000))
            .inputFluids(GTMaterials.Fluorine.getFluid(1000))
            .outputFluids(GTNNMaterials.NitrylFluoride.getFluid(1000))
            .EUt(GTValues.VA[GTValues.MV].toLong())
            .duration(160)
            .save(consumer)

        //  Sulfuric Acid + Naquadria Caesiumfluoride -> Acidic Naquadria Caesiumfluoride
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("acidic_naquadria_caesiumfluoride")
            .inputFluids(GTMaterials.SulfuricAcid.getFluid(2000))
            .inputFluids(GTNNMaterials.NaquadriaCaesiumfluoride.getFluid(1000))
            .outputFluids(GTNNMaterials.AcidicNaquadriaCaesiumfluoride.getFluid(3000))
            .EUt(GTValues.VA[GTValues.IV].toLong())
            .duration(360)
            .save(consumer)

        /*  Acidic Naquadria Caesiumfluoride -> Naquadria Sulfate + Caesium + Fluorine
        ELECTROLYZER_RECIPES.recipeBuilder("acidic_naquadria_caesiumfluoride")
                .inputFluids(AcidicNaquadriaCaesiumfluoride.getFluid(1000))
                .outputItems(dust, NaquadriaSulfate)
                .outputItems(dust, Caesium)
                .outputFluids(Fluorine.getFluid(3000))
                .EUt(VA[LuV])
                .duration(120)
                .save(consumer); */

        //  Acidic Naquadria Solution Cycle
        // todo BURNER_REACTOR_RECIPES
        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder("impure_enriched_naquadah_solution")
            .inputFluids(GTMaterials.AcidicNaquadriaSolution.getFluid(3000))
            .outputFluids(GTMaterials.NaquadriaWaste.getFluid(1000))
            .outputFluids(GTMaterials.ImpureEnrichedNaquadahSolution.getFluid(1000))
            .EUt(GTValues.VA[GTValues.HV].toLong())
            .addCondition(GTNNRecipes.setPlantCasing(3))
            .duration(1000)
            //.blastFurnaceTemp(1280)
            .save(consumer)

        GTNNRecipeTypes.NEUTRON_ACTIVATOR_RECIPES.recipeBuilder("naquadria")
            .inputFluids(GTNNMaterials.AcidicNaquadriaCaesiumfluoride.getFluid(9000))
            .outputItems(TagPrefix.dust, GTMaterials.NaquadriaSulfate, 3)
            .outputItems(TagPrefix.dust, GTMaterials.Caesium, 3)
            .outputFluids(GTMaterials.Fluorine.getFluid(18000))
            .duration(GTNNRecipes.dur(5.0))
            .addCondition(GTNNRecipes.setNA(1100, 1050))
            .save(consumer)

        GTNNRecipeTypes.NEUTRON_ACTIVATOR_RECIPES.recipeBuilder("enriched_naquadah")
            .inputFluids(GTMaterials.AcidicEnrichedNaquadahSolution.getFluid(16000))
            .outputItems(TagPrefix.dust, GTMaterials.EnrichedNaquadahSulfate, 15)
            .outputFluids(GTMaterials.ImpureNaquadriaSolution.getFluid(1000))
            .duration(GTNNRecipes.dur(6.0))
            .addCondition(GTNNRecipes.setNA(480, 460))
            .save(consumer)

        GTNNRecipeTypes.NEUTRON_ACTIVATOR_RECIPES.recipeBuilder("naquadah")
            .inputItems(TagPrefix.dust, GTNNMaterials.NaquadahOxideMixture, 4)
            .inputFluids(GTMaterials.FluoroantimonicAcid.getFluid(6000))
            .outputItems(TagPrefix.dust, GTMaterials.TitaniumTrifluoride, 2)
            .outputItems(TagPrefix.dust, GTMaterials.Naquadah, 2)
            .chancedOutput(TagPrefix.dust, GTMaterials.Gallium, 5000, 0)
            .duration(GTNNRecipes.dur(5.0))
            .addCondition(GTNNRecipes.setNA(230, 220))
            .save(consumer)

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(GTNNMaterials.EnrichedNaquadahOxideMixture.name)
            .inputItems(TagPrefix.dust, GTNNMaterials.EnrichedNaquadahOxideMixture, 2)
            .inputFluids(GTMaterials.FluoroantimonicAcid.getFluid(3000))
            .outputItems(TagPrefix.dust, GTMaterials.TitaniumTrifluoride, 1)
            .outputFluids(GTMaterials.ImpureEnrichedNaquadahSolution.getFluid(2000))
            .EUt(GTValues.VA[GTValues.EV].toLong())
            .duration(GTNNRecipes.dur(10.0))
            .save(consumer)

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(GTNNMaterials.NaquadriaOxideMixture.name)
            .inputItems(TagPrefix.dust, GTNNMaterials.NaquadriaOxideMixture, 2)
            .inputFluids(GTMaterials.FluoroantimonicAcid.getFluid(3000))
            .outputItems(TagPrefix.dust, GTMaterials.TitaniumTrifluoride, 1)
            .outputFluids(GTMaterials.ImpureNaquadriaSolution.getFluid(2000))
            .EUt(GTValues.VA[GTValues.IV].toLong())
            .duration(GTNNRecipes.dur(20.0))
            .save(consumer)
    }
}
