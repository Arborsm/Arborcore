package dev.arbor.gtnn.data.recipes

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.material.material.info.MaterialIconSet
import com.gregtechceu.gtceu.api.tag.TagPrefix
import com.gregtechceu.gtceu.data.material.GTMaterials
import com.gregtechceu.gtceu.data.recipe.GTRecipeTypes
import dev.arbor.gtnn.data.GTNNMaterials
import dev.arbor.gtnn.data.GTNNRecipeTypes
import net.minecraft.data.recipes.RecipeOutput

object BrineChain {
    //  Code from GT lite core by Magic_Sweep
    //  Complete Bromine-Iodine Chain
    //  in gcys, this is infinite bromine until Sodium Chloride Solution is separate from Salt Water

    fun init(provider: RecipeOutput) {
        iodineChain(provider)
        bromineChain(provider)
    }

    fun init() {
        GTNNMaterials.addFluid(GTMaterials.Bromine)
        GTNNMaterials.addDust(GTMaterials.Iodine)
        GTNNMaterials.IodizedBrine = GTNNMaterials.Builder("iodized_brine")
            .fluid().color(0x525246)
            .buildAndRegister()
            .setFormula("I?", false)
        GTNNMaterials.IodineBrineMixture = GTNNMaterials.Builder("iodine_brine_mixture")
            .fluid()
            .color(0x525234)
            .buildAndRegister()
            .setFormula("I?Cl", false)

        //  18059 Brominated Brine
        GTNNMaterials.BrominatedBrine = GTNNMaterials.Builder("brominated_brine")
            .fluid()
            .color(0xA9A990)
            .buildAndRegister()
            .setFormula("Br?", false)

        //  24039 Iodine Slurry
        GTNNMaterials.IodineSlurry = GTNNMaterials.Builder("iodine_slurry")
            .fluid()
            .color(0x292923)
            .buildAndRegister()
            .setFormula("I?", false)

        //  24040 Acidic Brominated Brine
        GTNNMaterials.AcidicBrominatedBrine = GTNNMaterials.Builder("acidic_brominated_brine")
            .acid()
            .color(0xC6A76F)
            .buildAndRegister()
            .setFormula("Br?(H2SO4)Cl", true)

        //  24041 Bromine Sulfate Solutions
        GTNNMaterials.BromineSulfateSolution = GTNNMaterials.Builder("bromine_sulfate_solution")
            .fluid()
            .color(0xCC9966)
            .buildAndRegister()
            .setFormula("H2SO4Br(H2O)Cl2", true)

        //  24042 Overheated Bromine Sulfate Gas
        GTNNMaterials.OverheatedBromineSulfateSolution = GTNNMaterials.Builder("overheated_bromine_sulfate_gas")
            .gas()
            .color(0xC69337)
            .iconSet(MaterialIconSet.DULL)
            .buildAndRegister()
            .setFormula("H2SO4Br(H2O)2Cl2", true)

        //  24043 Wet Bromine
        GTNNMaterials.WetBromine = GTNNMaterials.Builder("wet_bromine")
            .gas()
            .color(0xDB5C5C)
            .iconSet(MaterialIconSet.DULL)
            .buildAndRegister()
            .setFormula("Br(H2O)", true)

        //  24044 Debrominated Water
        GTNNMaterials.DebrominatedWater = GTNNMaterials.Builder("debrominated_water")
            .fluid()
            .color(0x24A3A3)
            .components(GTMaterials.Hydrogen, 2, GTMaterials.Oxygen, 1)
            .buildAndRegister()
    }

    private fun iodineChain(provider: RecipeOutput) {
        //  KNO3 + HCl -> K + I? todo INDUSTRIAL_ROASTER_RECIPES

        GTRecipeTypes.BLAST_RECIPES.recipeBuilder("iodine_brine")
            .inputItems(TagPrefix.dust, GTMaterials.Saltpeter)
            .inputFluids(GTMaterials.SaltWater.getFluid(1000))
            .outputItems(TagPrefix.dust, GTMaterials.Potassium)
            .outputFluids(GTNNMaterials.IodizedBrine.getFluid(1000))
            .circuitMeta(1)
            .EUt(1280)
            .duration(240)
            .blastFurnaceTemp(1128)
            .save(provider)

        //  I? + 0.3 Cl -> I?Cl
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("iodine_brine_mixture")
            .inputFluids(GTNNMaterials.IodizedBrine.getFluid(1000))
            .inputFluids(GTMaterials.Chlorine.getFluid(300))
            .outputFluids(GTNNMaterials.IodineBrineMixture.getFluid(1300))
            .EUt(480)
            .duration(240)
            .save(provider)

        //  I?Cl -> Br? + I?
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("brominated_brine")
            .inputFluids(GTNNMaterials.IodineBrineMixture.getFluid(1300))
            .outputFluids(GTNNMaterials.BrominatedBrine.getFluid(1000))
            .outputFluids(GTNNMaterials.IodineSlurry.getFluid(300))
            .EUt(980)
            .duration(120)
            .save(provider)

        //  I? -> I
        GTNNRecipeTypes.DEHYDRATOR_RECIPES.recipeBuilder("iodine")
            .inputFluids(GTNNMaterials.IodineSlurry.getFluid(1200))
            .outputItems(TagPrefix.dust, GTMaterials.Iodine)
            .EUt(1280)
            .duration(200)
            .save(provider)
    }

    private fun bromineChain(provider: RecipeOutput) {
        //  Br? + H2SO4 -> Br?(H2SO4)

        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("acidic_brominated_brine")
            .inputFluids(GTNNMaterials.BrominatedBrine.getFluid(1000))
            .inputFluids(GTMaterials.SulfuricAcid.getFluid(1000))
            .outputFluids(GTNNMaterials.AcidicBrominatedBrine.getFluid(1000))
            .EUt(480)
            .duration(200)
            .save(provider)

        //  Br?(H2SO4) + SO2 + H2O -> H2SO4Br(H2O)Cl2
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("bromine_sulfate_solution")
            .inputFluids(GTNNMaterials.AcidicBrominatedBrine.getFluid(1000))
            .inputFluids(GTMaterials.SulfurDioxide.getFluid(1000))
            .inputFluids(GTMaterials.Water.getFluid(1000))
            .circuitMeta(3)
            .outputFluids(GTNNMaterials.BromineSulfateSolution.getFluid(1000))
            .outputFluids(GTMaterials.SaltWater.getFluid(1000))
            .EUt(480)
            .duration(200)
            .save(provider)

        //  2H2SO4Br(H2O)Cl2 + H2O -> 3H2SO4Br(H2O)2Cl2 todo INDUSTRIAL_ROASTER_RECIPES
        GTRecipeTypes.CRACKING_RECIPES.recipeBuilder("overheated_bromine_sulfate_gas")
            .inputFluids(GTNNMaterials.BromineSulfateSolution.getFluid(2000))
            .inputFluids(GTMaterials.Steam.getFluid(1000))
            .outputFluids(GTNNMaterials.OverheatedBromineSulfateSolution.getFluid(3000))
            .EUt(GTValues.VA[GTValues.HV].toLong())
            .duration(400)
            .save(provider)

        //  3H2SO4Br(H2O)2Cl2 -> Br(H2O) + H2O + 2Cl + H2SO4
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("overheated_bromine_sulfate_gas")
            .inputFluids(GTNNMaterials.OverheatedBromineSulfateSolution.getFluid(3000))
            .outputFluids(GTNNMaterials.WetBromine.getFluid(1000))
            .outputFluids(GTNNMaterials.DebrominatedWater.getFluid(1000))
            .outputFluids(GTMaterials.Chlorine.getFluid(2000))
            .outputFluids(GTMaterials.SulfuricAcid.getFluid(1000))
            .EUt(GTValues.VA[GTValues.HV].toLong())
            .duration(280)
            .save(provider)

        //  Br(H2O) -> Br + H2O (lost)
        GTNNRecipeTypes.DEHYDRATOR_RECIPES.recipeBuilder("wet_bromine")
            .inputFluids(GTNNMaterials.WetBromine.getFluid(1000))
            .outputFluids(GTMaterials.Bromine.getFluid(1000))
            .EUt(360)
            .duration(80)
            .save(provider)

        //  Salt Water recycle
        GTNNRecipeTypes.DEHYDRATOR_RECIPES.recipeBuilder("debrominated_water")
            .inputFluids(GTNNMaterials.DebrominatedWater.getFluid(1000))
            .outputFluids(GTMaterials.SaltWater.getFluid(100))
            .EUt(360)
            .duration(80)
            .save(provider)
    }
}