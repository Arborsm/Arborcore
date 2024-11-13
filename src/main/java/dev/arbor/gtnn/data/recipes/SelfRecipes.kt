package dev.arbor.gtnn.data.recipes

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys
import com.gregtechceu.gtceu.common.data.*
import com.gregtechceu.gtceu.data.recipe.CustomTags
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper
import dev.arbor.gtnn.data.*
import dev.arbor.gtnn.data.recipes.DefaultRecipes.addBOOMRecipes
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object SelfRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        machineRecipes(provider)
        blockRecipes(provider)
        materialRecipes(provider)
    }

    private fun materialRecipes(provider: Consumer<FinishedRecipe>) {
        addBOOMRecipes(
            "enriched_uranium_nugget",
            GTNNItems.EncapsulatedUranium,
            GTNNItems.EnrichedUraniumNugget,
            GTNNRecipes.dur(1.0),
            1,
            provider
        )
        addBOOMRecipes(
            "enriched_thorium_nugget",
            GTNNItems.EncapsulatedThorium,
            GTNNItems.EnrichedThoriumNugget,
            GTNNRecipes.dur(1.0),
            1,
            provider
        )
        addBOOMRecipes(
            "enriched_plutonium_nugget",
            GTNNItems.EncapsulatedPlutonium,
            GTNNItems.EnrichedPlutoniumNugget,
            GTNNRecipes.dur(1.0),
            1,
            provider
        )
        GTNNRecipeTypes.PRECISION_ASSEMBLY_RECIPES.recipeBuilder("quark_core")
            .inputItems(CustomTags.IV_CIRCUITS, 2)
            .inputItems(TagPrefix.lens, GTMaterials.Diamond, 8)
            .inputItems(GTItems.NAND_MEMORY_CHIP.asStack(16))
            .inputItems(TagPrefix.rotor, GTMaterials.Aluminium)
            .inputFluids(GTMaterials.Polyethylene.getFluid(576))
            .inputFluids(GTMaterials.SodiumPotassium.getFluid(288))
            .inputFluids(GTMaterials.Lubricant.getFluid(144))
            .inputFluids(GTMaterials.StyreneButadieneRubber.getFluid(144))
            .outputItems(GTNNItems.QuarkCore)
            .EUt(GTValues.VA[GTValues.LuV].toLong()).duration(GTNNRecipes.dur(5.0)).save(provider)
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("graphite_uranium_mixture")
            .inputItems(TagPrefix.dust, GTMaterials.Graphite, 3)
            .inputItems(TagPrefix.dust, GTMaterials.Uranium238)
            .outputItems(TagPrefix.dust, GTNNMaterials.GraphiteUraniumMixture, 4)
            .EUt(GTValues.VA[GTValues.LV].toLong()).duration(GTNNRecipes.dur(1.7)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("encapsulated_uranium")
            .inputItems(TagPrefix.dust, GTNNMaterials.GraphiteUraniumMixture, 4)
            .inputItems(TagPrefix.foil, GTMaterials.TungstenCarbide, 2)
            .outputItems(GTNNItems.EncapsulatedUranium)
            .EUt(GTValues.VA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(70.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("uranium_carbide_thorium_mixture")
            .inputItems(TagPrefix.dust, GTMaterials.Thorium, 11)
            .inputItems(TagPrefix.dust, GTNNMaterials.Thorium232)
            .inputItems(TagPrefix.dust, GTMaterials.Uranium235)
            .inputItems(TagPrefix.dust, GTMaterials.Carbon, 3)
            .outputItems(TagPrefix.dust, GTNNMaterials.UraniumCarbideThoriumMixture, 16)
            .EUt(GTValues.VA[GTValues.LV].toLong()).duration(GTNNRecipes.dur(2.35)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("encapsulated_thorium")
            .inputItems(TagPrefix.dust, GTNNMaterials.UraniumCarbideThoriumMixture, 64)
            .inputItems(TagPrefix.foil, GTMaterials.TungstenSteel, 4)
            .outputItems(GTNNItems.EncapsulatedThorium)
            .EUt(GTValues.VA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("plutonium_oxide_uranium_mixture")
            .inputItems(TagPrefix.dust, GTMaterials.Plutonium239, 10)
            .inputItems(TagPrefix.dust, GTMaterials.Uranium238, 2)
            .inputItems(TagPrefix.dust, GTMaterials.Carbon, 8)
            .inputFluids(GTMaterials.Oxygen.getFluid(12000))
            .outputItems(TagPrefix.dust, GTNNMaterials.PlutoniumOxideUraniumMixture, 32)
            .EUt(GTValues.VA[GTValues.LV].toLong()).duration(GTNNRecipes.dur(1.25)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("encapsulated_plutonium")
            .inputItems(TagPrefix.dust, GTNNMaterials.PlutoniumOxideUraniumMixture, 8)
            .inputItems(TagPrefix.foil, GTMaterials.HSSS, 4)
            .outputItems(GTNNItems.EncapsulatedPlutonium)
            .EUt(GTValues.VA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(70.0)).save(provider)
        GTRecipeTypes.COMPRESSOR_RECIPES.recipeBuilder("enriched_uranium")
            .inputItems(GTNNItems.EnrichedUraniumNugget.asStack(9))
            .outputItems(GTNNItems.EnrichedUranium)
            .EUt(GTValues.VA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(30.0)).save(provider)
        GTRecipeTypes.COMPRESSOR_RECIPES.recipeBuilder("enriched_thorium")
            .inputItems(GTNNItems.EnrichedThoriumNugget.asStack(9))
            .outputItems(GTNNItems.EnrichedThorium)
            .EUt(GTValues.VA[GTValues.MV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        GTRecipeTypes.COMPRESSOR_RECIPES.recipeBuilder("enriched_plutonium")
            .inputItems(GTNNItems.EnrichedPlutoniumNugget.asStack(9))
            .outputItems(GTNNItems.EnrichedPlutonium)
            .EUt(GTValues.VA[GTValues.MV].toLong()).duration(GTNNRecipes.dur(60.0)).save(provider)
        GTRecipeTypes.FUSION_RECIPES.recipeBuilder("californium")
            .inputFluids(GTMaterials.Plutonium239.getFluid(48))
            .inputFluids(GTMaterials.Beryllium.getFluid(48))
            .outputFluids(GTMaterials.Californium.getFluid(48))
            .fusionStartEU(120000000)
            .EUt(GTValues.VA[GTValues.ZPM].toLong()).duration(GTNNRecipes.dur(12.0)).save(provider)
        GTRecipeTypes.FUSION_RECIPES.recipeBuilder("oganesson")
            .inputFluids(GTMaterials.Californium.getFluid(32))
            .inputFluids(GTMaterials.Calcium.getFluid(720))
            .outputFluids(GTMaterials.Oganesson.getFluid(720))
            .fusionStartEU(600000000)
            .EUt(GTValues.VA[GTValues.ZPM].toLong()).duration(GTNNRecipes.dur(12.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("inverter")
            .inputItems(TagPrefix.plate, GTMaterials.NetherQuartz, 2)
            .inputItems(CustomTags.MV_CIRCUITS)
            .inputItems(GTItems.COVER_SCREEN)
            .inputItems(GTItems.DIODE.asStack(16))
            .inputItems(TagPrefix.wireGtSingle, GTMaterials.Aluminium, 8)
            .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
            .outputItems(GTNNItems.INVERTER)
            .EUt(GTValues.VA[GTValues.MV].toLong()).duration(GTNNRecipes.dur(12.0)).save(provider)
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("thorium_based_liquid_fuel")
            .inputItems(GTNNItems.EnrichedThorium)
            .inputItems(TagPrefix.dust, GTMaterials.Lithium, 4) //.inputItems(dust, ) Dragon
            .inputFluids(GTMaterials.Mercury.getFluid(1000))
            .outputFluids(GTNNMaterials.ThoriumBasedLiquidFuel.getFluid(1000))
            .circuitMeta(2)
            .EUt(GTValues.VHA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(150.0)).save(provider)
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("uranium_based_liquid_fuel")
            .inputItems(GTNNItems.EnrichedUranium)
            .inputItems(TagPrefix.dust, GTMaterials.Potassium, 8) //.inputItems(dust, ) Qt
            .inputFluids(GTMaterials.Radon.getFluid(1000))
            .outputFluids(GTNNMaterials.UraniumBasedLiquidFuel.getFluid(1000))
            .circuitMeta(1)
            .EUt(GTValues.VHA[GTValues.LuV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("plutonium_based_liquid_fuel")
            .inputItems(GTNNItems.EnrichedPlutonium)
            .inputItems(TagPrefix.dust, GTNNMaterials.NeutroniumMixture, 8)
            .inputItems(TagPrefix.dust, GTMaterials.Caesium, 16)
            .inputItems(TagPrefix.dust, GTMaterials.Naquadah, 2)
            .outputFluids(GTNNMaterials.PlutoniumBasedLiquidFuel.getFluid(1000))
            .circuitMeta(1)
            .EUt(GTValues.VA[GTValues.LuV].toLong()).duration(GTNNRecipes.dur(18.0)).save(provider)
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("thorium_based_liquid_fuel_excited")
            .inputFluids(GTNNMaterials.ThoriumBasedLiquidFuel.getFluid(1000))
            .inputFluids(GTMaterials.Helium.getFluid(250))
            .outputFluids(GTNNMaterials.ThoriumBasedLiquidFuelExcited.getFluid(1000))
            .circuitMeta(1)
            .EUt(GTValues.VHA[GTValues.IV].toLong()).duration(GTNNRecipes.dur(6.0)).save(provider)
        GTNNRecipeTypes.NEUTRON_ACTIVATOR_RECIPES.recipeBuilder("uranium_based_liquid_fuel_excited")
            .notConsumable(TagPrefix.plate, GTMaterials.Tungsten)
            .inputFluids(GTNNMaterials.UraniumBasedLiquidFuel.getFluid(100))
            .outputFluids(GTNNMaterials.UraniumBasedLiquidFuelExcited.getFluid(100))
            .addCondition(GTNNRecipes.setNA(550, 450))
            .duration(GTNNRecipes.dur(4.0)).save(provider)
        GTRecipeTypes.FUSION_RECIPES.recipeBuilder("uranium_based_liquid_fuel_excited")
            .inputFluids(GTNNMaterials.UraniumBasedLiquidFuel.getFluid(10))
            .inputFluids(GTMaterials.Hydrogen.getFluid(100))
            .outputFluids(GTNNMaterials.UraniumBasedLiquidFuelExcited.getFluid(10))
            .fusionStartEU(200000000)
            .EUt(GTValues.VA[GTValues.IV].toLong()).duration(GTNNRecipes.dur(2.0)).save(provider)
        GTNNRecipeTypes.NEUTRON_ACTIVATOR_RECIPES.recipeBuilder("plutonium_based_liquid_fuel_excited")
            .notConsumable(TagPrefix.plate, GTMaterials.Tritanium)
            .inputFluids(GTNNMaterials.PlutoniumBasedLiquidFuel.getFluid(100))
            .outputFluids(GTNNMaterials.PlutoniumBasedLiquidFuelExcited.getFluid(100))
            .addCondition(GTNNRecipes.setNA(600, 500))
            .duration(GTNNRecipes.dur(4.0)).save(provider)
        GTRecipeTypes.FUSION_RECIPES.recipeBuilder("plutonium_based_liquid_fuel_excited")
            .inputFluids(GTMaterials.Lutetium.getFluid(16))
            .inputFluids(GTNNMaterials.PlutoniumBasedLiquidFuel.getFluid(20))
            .outputFluids(GTNNMaterials.PlutoniumBasedLiquidFuelExcited.getFluid(20))
            .fusionStartEU(220000000)
            .EUt(GTValues.VA[GTValues.LuV].toLong()).duration(GTNNRecipes.dur(1.0)).save(provider)
        GTNNRecipeTypes.NEUTRON_ACTIVATOR_RECIPES.recipeBuilder("thorium_based_liquid_fuel_depleted")
            .inputFluids(GTNNMaterials.ThoriumBasedLiquidFuelExcited.getFluid(200))
            .outputFluids(GTNNMaterials.ThoriumBasedLiquidFuelDepleted.getFluid(200))
            .addCondition(GTNNRecipes.setNA(700, 500))
            .duration(GTNNRecipes.dur(500.0)).save(provider)
        GTNNRecipeTypes.LARGE_NAQUADAH_REACTOR_RECIPES.recipeBuilder("thorium_based_liquid_fuel_depleted")
            .inputFluids(GTNNMaterials.ThoriumBasedLiquidFuelExcited.getFluid(1000))
            .outputFluids(GTNNMaterials.ThoriumBasedLiquidFuelDepleted.getFluid(1000))
            .EUt(-2200)
            .duration(GTNNRecipes.dur(25.0)).save(provider)
        GTNNRecipeTypes.LARGE_NAQUADAH_REACTOR_RECIPES.recipeBuilder("uranium_based_liquid_fuel_depleted")
            .inputFluids(GTNNMaterials.UraniumBasedLiquidFuelExcited.getFluid(1000))
            .outputFluids(GTNNMaterials.UraniumBasedLiquidFuelDepleted.getFluid(1000))
            .EUt(-12960)
            .duration(GTNNRecipes.dur(5.0)).save(provider)
        GTNNRecipeTypes.LARGE_NAQUADAH_REACTOR_RECIPES.recipeBuilder("plutonium_based_liquid_fuel_depleted")
            .inputFluids(GTNNMaterials.PlutoniumBasedLiquidFuelExcited.getFluid(1000))
            .outputFluids(GTNNMaterials.PlutoniumBasedLiquidFuelDepleted.getFluid(1000))
            .EUt(-32400)
            .duration(GTNNRecipes.dur(7.5)).save(provider)
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("thorium_based_liquid_fuel_depleted")
            .inputFluids(GTNNMaterials.ThoriumBasedLiquidFuelDepleted.getFluid(500))
            .outputItems(TagPrefix.dust, GTNNMaterials.Thorium232, 32)
            .chancedOutput(TagPrefix.dust, GTNNMaterials.Thorium232, 8, 8000, 0)
            .outputItems(TagPrefix.dust, GTMaterials.Praseodymium, 32)
            .chancedOutput(TagPrefix.dust, GTMaterials.Praseodymium, 16, 8000, 0)
            .chancedOutput(TagPrefix.dust, GTMaterials.Boron, 3000, 0)
            .chancedOutput(TagPrefix.dust, GTMaterials.Indium, 2, 5000, 0)
            .circuitMeta(1)
            .EUt(1040).duration(GTNNRecipes.dur(37.5)).save(provider)
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("uranium_based_liquid_fuel_depleted")
            .inputFluids(GTNNMaterials.UraniumBasedLiquidFuelDepleted.getFluid(1000))
            .chancedOutput(TagPrefix.dust, GTMaterials.Lead, 16, 6000, 0)
            .chancedOutput(TagPrefix.dust, GTMaterials.Bismuth, 1000, 0)
            .chancedOutput(TagPrefix.dust, GTMaterials.Barium, 6, 5000, 0)
            .circuitMeta(1)
            .EUt(1040).duration(GTNNRecipes.dur(50.0)).save(provider)
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("plutonium_based_liquid_fuel_depleted")
            .inputFluids(GTNNMaterials.PlutoniumBasedLiquidFuelDepleted.getFluid(1000))
            .chancedOutput(TagPrefix.dust, GTMaterials.Tritanium, 9, 5000, 0)
            .chancedOutput(TagPrefix.dust, GTMaterials.Cerium, 4, 8000, 0)
            .chancedOutput(TagPrefix.dust, GTMaterials.Gold, 2, 7500, 0)
            .outputFluids(GTMaterials.Krypton.getFluid(144))
            .circuitMeta(1)
            .EUt(GTValues.VA[GTValues.IV].toLong()).duration(GTNNRecipes.dur(125.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("plate_radiation")
            .inputItems(TagPrefix.plateDense, GTMaterials.Iridium, 8)
            .inputItems(TagPrefix.plateDense, GTMaterials.NaquadahAlloy, 8)
            .inputFluids(GTMaterials.Lead.getFluid(1152))
            .outputItems(GTNNItems.PlateRadiationProtection)
            .circuitMeta(1)
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(20.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("plate_radiation_2")
            .inputItems(TagPrefix.plateDense, GTMaterials.Lanthanum, 4)
            .inputItems(TagPrefix.plateDense, GTMaterials.NaquadahAlloy, 8)
            .inputFluids(GTMaterials.Lead.getFluid(1152))
            .outputItems(GTNNItems.PlateRadiationProtection)
            .circuitMeta(1)
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(20.0)).save(provider)
        GTRecipeTypes.BENDER_RECIPES.recipeBuilder("dense_lanthanum")
            .inputItems(TagPrefix.plate, GTMaterials.Lanthanum, 9)
            .outputItems(TagPrefix.plateDense, GTMaterials.Lanthanum)
            .circuitMeta(9)
            .EUt(96).duration(GTNNRecipes.dur(62.1)).save(provider)
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("naquadah_based_liquid_fuel")
            .inputItems(TagPrefix.dust, GTMaterials.Naquadria, 42)
            .inputItems(TagPrefix.dust, GTMaterials.Cerium, 16)
            .inputItems(TagPrefix.dust, GTMaterials.Neodymium, 16)
            .outputFluids(GTNNMaterials.NaquadahBasedLiquidFuel.getFluid(1000))
            .EUt(GTValues.VA[GTValues.IV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.FUSION_RECIPES.recipeBuilder("naquadah_based_liquid_fuel_excited")
            .inputFluids(GTNNMaterials.NaquadahBasedLiquidFuel.getFluid(800))
            .inputFluids(GTMaterials.Radon.getFluid(200))
            .outputFluids(GTNNMaterials.NaquadahBasedLiquidFuelExcited.getFluid(100))
            .fusionStartEU(320000000)
            .EUt(26000).duration(GTNNRecipes.dur(25.0)).save(provider)
        GTNNRecipeTypes.LARGE_NAQUADAH_REACTOR_RECIPES.recipeBuilder("naquadah_based_liquid_fuel_depleted")
            .inputFluids(GTNNMaterials.NaquadahBasedLiquidFuelExcited.getFluid(1))
            .outputFluids(GTNNMaterials.NaquadahBasedLiquidFuelDepleted.getFluid(1))
            .EUt(-975000L)
            .duration(GTNNRecipes.dur(3.0)).save(provider)
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("naquadah_based_liquid_fuel_depleted")
            .inputFluids(GTNNMaterials.NaquadahBasedLiquidFuelDepleted.getFluid(125))
            .chancedOutput(TagPrefix.dust, GTMaterials.Naquadah, 8, 9000, 0)
            .chancedOutput(TagPrefix.dust, GTMaterials.Naquadah, 6, 8500, 0)
            .chancedOutput(TagPrefix.dust, GTMaterials.Naquadah, 4, 5000, 0)
            .chancedOutput(TagPrefix.dust, GTMaterials.Neodymium, 4, 4000, 0)
            .chancedOutput(TagPrefix.dust, GTMaterials.Europium, 4, 2000, 0)
            .outputFluids(GTMaterials.Xenon.getFluid(18))
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(30.0)).save(provider)
        GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder(GTNNMaterials.Thorium232.name)
            .inputItems(TagPrefix.dust, GTMaterials.Thorium, 16)
            .inputItems(TagPrefix.dust, GTMaterials.Borax, 12)
            .inputFluids(GTMaterials.DistilledWater.getFluid(2000))
            .inputFluids(GTMaterials.HydrochloricAcid.getFluid(1000))
            .outputItems(TagPrefix.dustSmall, GTMaterials.Thorium, 32)
            .outputItems(TagPrefix.dust, GTNNMaterials.Thorium232, 2)
            .chancedOutput(
                TagPrefix.dustSmall,
                GTNNMaterials.Thorium232,
                2,
                1000,
                0
            ) //.chancedOutput(dustSmall, Uranium232, 2, 1000, 0)
            .addCondition(GTNNRecipes.setPlantCasing(5))
            .circuitMeta(1)
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(300.0)).save(provider)
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("ammonium_chloride")
            .inputFluids(GTMaterials.Ammonia.getFluid(1000))
            .inputFluids(GTMaterials.HydrochloricAcid.getFluid(1000))
            .outputFluids(GTMaterials.AmmoniumChloride.getFluid(1000))
            .circuitMeta(1)
            .EUt(GTValues.VA[GTValues.LV].toLong()).duration(GTNNRecipes.dur(0.75)).save(provider)
    }

    private fun blockRecipes(provider: Consumer<FinishedRecipe>) {
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "clean_machine_casing", GTNNCasingBlocks.PROCESS_MACHINE_CASING.asStack(),
            "ABA", "BCB", "ABA",
            'A', UnificationEntry(TagPrefix.foil, GTMaterials.StainlessSteel),
            'B', CustomTags.IV_CIRCUITS,
            'C', GTBlocks.CASING_STEEL_SOLID
        )
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("high_speed_pipe_block")
            .inputItems(TagPrefix.pipeHugeFluid, GTMaterials.StainlessSteel)
            .inputItems(TagPrefix.frameGt, GTMaterials.BlueAlloy, 32)
            .inputItems(TagPrefix.wireGtSingle, GTMaterials.MercuryBariumCalciumCuprate, 32)
            .inputItems(TagPrefix.plate, GTMaterials.Beryllium, 32)
            .inputItems(CustomTags.IV_CIRCUITS)
            .outputItems(GTNNMachines.HIGH_SPEED_PIPE_BLOCK.asStack())
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("radiation_proof_machine_casing")
            .inputItems(TagPrefix.plateDense, GTMaterials.Lead, 6)
            .inputItems(TagPrefix.frameGt, GTMaterials.TungstenSteel)
            .inputFluids(GTMaterials.Concrete.getFluid(1296))
            .outputItems(GTNNCasingBlocks.RADIATION_PROOF_MACHINE_CASING)
            .EUt(GTValues.VA[GTValues.IV].toLong()).duration(GTNNRecipes.dur(2.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("mar_casing")
            .inputItems(GTNNItems.PlateRadiationProtection.asStack(6))
            .inputItems(TagPrefix.frameGt, GTMaterials.Europium)
            .inputItems(GTItems.FIELD_GENERATOR_MV)
            .outputItems(GTNNCasingBlocks.MAR_CASING)
            .circuitMeta(1)
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(20.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("frame_radiation")
            .inputItems(TagPrefix.rodLong, GTMaterials.NaquadahAlloy, 8)
            .inputItems(TagPrefix.frameGt, GTMaterials.HSSE, 4)
            .outputItems(TagPrefix.frameGt, GTNNMaterials.RadiationProtection)
            .circuitMeta(24)
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(16.0)).save(provider)
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("neutronium_mixture")
            .inputItems(TagPrefix.dust, GTNNMaterials.NeutroniumMixture)
            .inputFluids(GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 144))
            .outputItems(TagPrefix.dust, GTMaterials.Neutronium)
            .circuitMeta(1)
            .EUt(GTValues.VA[GTValues.ZPM].toLong()).duration(GTNNRecipes.dur(11.25)).save(provider)
        VanillaRecipeHelper.addShapelessRecipe(
            provider,
            "centrifuged_ore_to_dust_neutronium",
            ChemicalHelper.get(TagPrefix.dust, GTNNMaterials.NeutroniumMixture),
            'h',
            ChemicalHelper.get(TagPrefix.crushedRefined, GTMaterials.Neutronium)
        )
    }

    private fun machineRecipes(provider: Consumer<FinishedRecipe>) {
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("catalyst_hatch")
            .inputItems(CustomTags.MV_CIRCUITS)
            .inputItems(GTMachines.ITEM_IMPORT_BUS[GTValues.MV])
            .outputItems(GTNNMachines.CATALYST_HATCH)
            .EUt(GTValues.VA[GTValues.MV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("large_naquadah_reactor_casing")
            .inputItems(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.asItem())
            .inputItems(TagPrefix.wireGtHex, GTMaterials.IndiumTinBariumTitaniumCuprate, 4)
            .inputItems(GTItems.BATTERY_LUV_VANADIUM.asStack(1))
            .inputItems(GTItems.ROBOT_ARM_EV.asStack(4))
            .inputItems(TagPrefix.plate, GTMaterials.Zeron100, 8)
            .inputItems(CustomTags.IV_CIRCUITS, 8)
            .inputFluids(GTMaterials.Zeron100.getFluid(2880))
            .outputItems(GTNNMachines.LARGE_DEHYDRATOR)
            .EUt(GTValues.VA[GTValues.LuV].toLong()).duration(GTNNRecipes.dur(120.0)).save(provider)
        GTRecipeTypes.ASSEMBLY_LINE_RECIPES.recipeBuilder("large_naquadah_reactor")
            .inputItems(TagPrefix.frameGt, GTMaterials.Neutronium, 8)
            .inputItems(GTNNItems.PlateRadiationProtection.asStack(16))
            .inputItems(GTItems.FIELD_GENERATOR_ZPM.asStack(2))
            .inputItems(GTItems.ELECTRIC_PUMP_ZPM.asStack(8))
            .inputItems(CustomTags.UV_CIRCUITS, 4)
            .inputItems(TagPrefix.wireGtOctal, GTMaterials.IndiumTinBariumTitaniumCuprate, 8)
            .inputItems(TagPrefix.pipeHugeFluid, GTMaterials.Naquadah, 4)
            .inputItems(TagPrefix.plate, GTMaterials.NaquadahAlloy, 8)
            .inputItems(TagPrefix.screw, GTMaterials.Osmium, 16)
            .outputItems(GTNNMachines.LargeNaquadahReactor)
            .scannerResearch(GTNNMachines.NAQUADAH_REACTOR[GTValues.LuV]!!.asStack())
            .EUt(GTValues.VA[GTValues.ZPM].toLong()).duration(GTNNRecipes.dur(210.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("neutron_accelerator_mv")
            .inputItems(GTNNItems.INVERTER.asStack())
            .inputItems(GTMachines.HULL[GTValues.MV].asStack())
            .inputItems(TagPrefix.cableGtSingle, GTMaterials.Copper, 2)
            .inputItems(TagPrefix.plate, GTMaterials.Polyethylene)
            .inputItems(TagPrefix.plate, GTMaterials.Beryllium, 2)
            .inputItems(GTItems.ELECTRIC_MOTOR_MV)
            .outputItems(GTNNMachines.NEUTRON_ACCELERATOR[GTValues.MV]!!.asStack())
            .EUt(GTValues.VA[GTValues.MV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("neutron_accelerator_hv")
            .inputItems(GTNNItems.INVERTER.asStack())
            .inputItems(GTMachines.HULL[GTValues.HV].asStack())
            .inputItems(TagPrefix.cableGtSingle, GTMaterials.Gold, 2)
            .inputItems(TagPrefix.plate, GTMaterials.PolyvinylChloride)
            .inputItems(TagPrefix.plateDouble, GTMaterials.Beryllium, 2)
            .inputItems(GTItems.ELECTRIC_MOTOR_HV.asStack(2))
            .outputItems(GTNNMachines.NEUTRON_ACCELERATOR[GTValues.HV]!!.asStack())
            .EUt(GTValues.VA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("neutron_accelerator_ev")
            .inputItems(GTNNItems.INVERTER.asStack())
            .inputItems(GTMachines.HULL[GTValues.EV].asStack())
            .inputItems(TagPrefix.cableGtSingle, GTMaterials.Aluminium, 2)
            .inputItems(TagPrefix.plate, GTMaterials.StyreneButadieneRubber)
            .inputItems(TagPrefix.plate, GTMaterials.IronMagnetic, 4)
            .inputItems(TagPrefix.plate, GTMaterials.TungstenCarbide, 2)
            .inputItems(GTItems.ELECTRIC_MOTOR_EV.asStack(2))
            .outputItems(GTNNMachines.NEUTRON_ACCELERATOR[GTValues.EV]!!.asStack())
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("neutron_accelerator_iv")
            .inputItems(GTNNItems.INVERTER.asStack())
            .inputItems(GTMachines.HULL[GTValues.IV].asStack())
            .inputItems(TagPrefix.cableGtSingle, GTMaterials.Tungsten, 2)
            .inputItems(TagPrefix.plate, GTMaterials.SiliconeRubber)
            .inputItems(TagPrefix.plate, GTMaterials.SteelMagnetic, 4)
            .inputItems(TagPrefix.plateDouble, GTMaterials.TungstenCarbide, 2)
            .inputItems(GTItems.ELECTRIC_MOTOR_IV.asStack(2))
            .outputItems(GTNNMachines.NEUTRON_ACCELERATOR[GTValues.IV]!!.asStack())
            .EUt(GTValues.VA[GTValues.IV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.ASSEMBLY_LINE_RECIPES.recipeBuilder("neutron_accelerator_luv")
            .inputItems(GTNNItems.INVERTER.asStack(2))
            .inputItems(GTMachines.HULL[GTValues.LuV].asStack())
            .inputItems(TagPrefix.cableGtSingle, GTMaterials.YttriumBariumCuprate, 2)
            .inputItems(TagPrefix.plate, GTMaterials.NetherStar)
            .inputItems(TagPrefix.plate, GTMaterials.Polybenzimidazole, 4)
            .inputItems(TagPrefix.plate, GTMaterials.NeodymiumMagnetic, 4)
            .inputItems(TagPrefix.plate, GTMaterials.NeodymiumMagnetic, 4)
            .inputItems(GTItems.ELECTRIC_MOTOR_LuV.asStack(2)) // .inputItems(wireGtQuadruple, ) todo mv超导
            .inputFluids(GTMaterials.Argon.getFluid(3000))
            .outputItems(GTNNMachines.NEUTRON_ACCELERATOR[GTValues.LuV]!!.asStack())
            .scannerResearch(GTNNMachines.NEUTRON_ACCELERATOR[GTValues.IV]!!.asStack())
            .EUt(GTValues.VA[GTValues.LuV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.ASSEMBLY_LINE_RECIPES.recipeBuilder("neutron_accelerator_zpm")
            .inputItems(GTNNItems.INVERTER.asStack(2))
            .inputItems(GTMachines.HULL[GTValues.ZPM].asStack())
            .inputItems(TagPrefix.cableGtSingle, GTMaterials.VanadiumGallium, 2)
            .inputItems(TagPrefix.plate, GTMaterials.NetherStar)
            .inputItems(TagPrefix.plate, GTMaterials.Polybenzimidazole, 8)
            .inputItems(TagPrefix.plate, GTMaterials.SamariumMagnetic, 4)
            .inputItems(TagPrefix.plate, GTMaterials.SamariumMagnetic, 4)
            .inputItems(GTItems.ELECTRIC_MOTOR_ZPM.asStack(2))
            .inputItems(TagPrefix.wireGtQuadruple, GTMaterials.UraniumTriplatinum, 4)
            .inputFluids(GTMaterials.Xenon.getFluid(3000))
            .outputItems(GTNNMachines.NEUTRON_ACCELERATOR[GTValues.ZPM]!!.asStack())
            .scannerResearch(GTNNMachines.NEUTRON_ACCELERATOR[GTValues.LuV]!!.asStack())
            .EUt(GTValues.VA[GTValues.ZPM].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.ASSEMBLY_LINE_RECIPES.recipeBuilder("neutron_accelerator_uv")
            .inputItems(GTNNItems.INVERTER.asStack(4))
            .inputItems(GTMachines.HULL[GTValues.UV].asStack())
            .inputItems(TagPrefix.cableGtSingle, GTMaterials.NaquadahAlloy, 2)
            .inputItems(TagPrefix.plate, GTMaterials.NetherStar, 2)
            .inputItems(TagPrefix.plate, GTMaterials.Polybenzimidazole, 4)
            .inputItems(GTItems.VOLTAGE_COIL_ZPM.asStack(4))
            .inputItems(TagPrefix.rodLong, GTMaterials.NickelZincFerrite, 16)
            .inputItems(GTItems.VOLTAGE_COIL_ZPM.asStack(4))
            .inputItems(GTItems.ELECTRIC_MOTOR_UV.asStack(2))
            .inputItems(TagPrefix.wireGtQuadruple, GTMaterials.IndiumTinBariumTitaniumCuprate, 4)
            .inputFluids(GTMaterials.Oganesson.getFluid(3000))
            .outputItems(GTNNMachines.NEUTRON_ACCELERATOR[GTValues.UV]!!.asStack())
            .scannerResearch(GTNNMachines.NEUTRON_ACCELERATOR[GTValues.ZPM]!!.asStack())
            .EUt(GTValues.VA[GTValues.UV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("neutron_sensor")
            .inputItems(GTBlocks.MACHINE_CASING_IV.asStack())
            .inputItems(GTItems.COVER_ACTIVITY_DETECTOR)
            .inputItems(GTItems.COVER_SCREEN)
            .inputItems(TagPrefix.plate, GTMaterials.VanadiumGallium, 4)
            .inputItems(CustomTags.EV_CIRCUITS)
            .inputItems(GTItems.SENSOR_HV.asStack(2))
            .circuitMeta(1)
            .outputItems(GTNNMachines.NEUTRON_SENSOR.asStack())
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(15.0)).save(provider)
        GTNNRecipeTypes.PRECISION_ASSEMBLY_RECIPES.recipeBuilder("neutron_activator")
            .inputItems(GTNNItems.QuarkCore.asStack(2))
            .inputItems(GTItems.SENSOR_EV.asStack(2))
            .inputItems(GTNNItems.NeutronSource)
            .inputFluids(GTMaterials.StainlessSteel.getFluid(576))
            .inputFluids(GTMaterials.TungstenCarbide.getFluid(144))
            .outputItems(GTNNMachines.NEUTRON_ACTIVATOR.asStack())
            .EUt(GTValues.VA[GTValues.IV].toLong()).duration(GTNNRecipes.dur(5.0)).save(provider)
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "neutron_source", GTNNItems.NeutronSource.asStack(),
            " A ", "ABA", " A ",
            'A', UnificationEntry(TagPrefix.plateDense, GTMaterials.Steel),
            'B', GTNNItems.EnrichedUranium.asStack()
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, "neutron_accelerator_ulv", GTNNMachines.NEUTRON_ACCELERATOR[GTValues.ULV]!!
                .asStack(),
            "ABC", "DEF", "ABC",
            'A', UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Lead),
            'B', UnificationEntry(TagPrefix.plate, GTMaterials.Lead),
            'C', UnificationEntry(TagPrefix.rotor, GTMaterials.Lead),
            'D', UnificationEntry(TagPrefix.plate, GTMaterials.Wood),
            'E', GTMachines.HULL[GTValues.ULV].asStack(),
            'F', GTNNItems.INVERTER.asStack()
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, "neutron_accelerator_lv", GTNNMachines.NEUTRON_ACCELERATOR[GTValues.LV]!!
                .asStack(),
            "ABC", "DEF", "ABC",
            'A', UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Tin),
            'B', UnificationEntry(TagPrefix.plateDouble, GTMaterials.Lead),
            'C', GTItems.ELECTRIC_MOTOR_LV.asStack(),
            'D', UnificationEntry(TagPrefix.plate, GTMaterials.Rubber),
            'E', GTMachines.HULL[GTValues.LV].asStack(),
            'F', GTNNItems.INVERTER.asStack()
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "dehydrator_mv", GTNNMachines.DEHYDRATOR[GTValues.MV]!!.asStack(),
            "ABA", "CDC", "EFE",
            'A', UnificationEntry(TagPrefix.wireFine, GTMaterials.RedAlloy),
            'B', CustomTags.MV_CIRCUITS,
            'C', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Copper),
            'D', GTMachines.HULL[GTValues.MV].asStack(),
            'E', UnificationEntry(TagPrefix.gear, GTMaterials.Steel),
            'F', GTItems.ROBOT_ARM_MV
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "dehydrator_hv", GTNNMachines.DEHYDRATOR[GTValues.HV]!!.asStack(),
            "ABA", "CDC", "EFE",
            'A', UnificationEntry(TagPrefix.wireFine, GTMaterials.Electrum),
            'B', CustomTags.HV_CIRCUITS,
            'C', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Silver),
            'D', GTMachines.HULL[GTValues.HV].asStack(),
            'E', UnificationEntry(TagPrefix.gear, GTMaterials.Potin),
            'F', GTItems.ROBOT_ARM_HV
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "dehydrator_ev", GTNNMachines.DEHYDRATOR[GTValues.EV]!!.asStack(),
            "ABA", "CDC", "EFE",
            'A', GTItems.VOLTAGE_COIL_EV,
            'B', CustomTags.EV_CIRCUITS,
            'C', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Aluminium),
            'D', GTMachines.HULL[GTValues.EV].asStack(),
            'E', UnificationEntry(TagPrefix.gear, GTMaterials.TungstenSteel),  //todo
            'F', GTItems.ROBOT_ARM_EV
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "dehydrator_iv", GTNNMachines.DEHYDRATOR[GTValues.IV]!!.asStack(),
            "ABA", "CDC", "EFE",
            'A', GTItems.VOLTAGE_COIL_IV,
            'B', CustomTags.IV_CIRCUITS,
            'C', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Tungsten),
            'D', GTMachines.HULL[GTValues.IV].asStack(),
            'E', UnificationEntry(TagPrefix.gear, GTMaterials.Nichrome),
            'F', GTItems.ROBOT_ARM_IV
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "dehydrator_luv", GTNNMachines.DEHYDRATOR[GTValues.LuV]!!.asStack(),
            "ABA", "CDC", "EFE",
            'A', GTItems.VOLTAGE_COIL_LuV,
            'B', CustomTags.LuV_CIRCUITS,
            'C', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Naquadah),
            'D', GTMachines.HULL[GTValues.LuV].asStack(),
            'E', UnificationEntry(TagPrefix.gear, GTMaterials.Ultimet),  //todo
            'F', GTItems.ROBOT_ARM_LuV
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, "dehydrator_zpm", GTNNMachines.DEHYDRATOR[GTValues.ZPM]!!.asStack(),
            "ABA", "CDC", "EFE",
            'A', GTItems.VOLTAGE_COIL_ZPM,
            'B', CustomTags.ZPM_CIRCUITS,
            'C', UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Osmium),
            'D', GTMachines.HULL[GTValues.ZPM].asStack(),
            'E', UnificationEntry(TagPrefix.gear, GTMaterials.Zeron100),
            'F', GTItems.ROBOT_ARM_ZPM
        )


    }
}
