package dev.arbor.gtnn.data.recipes

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.material.ChemicalHelper
import com.gregtechceu.gtceu.api.material.material.stack.UnificationEntry
import com.gregtechceu.gtceu.api.tag.TagPrefix.*
import com.gregtechceu.gtceu.data.block.GTBlocks
import com.gregtechceu.gtceu.data.item.GTItems
import com.gregtechceu.gtceu.data.material.GTMaterials.*
import com.gregtechceu.gtceu.data.recipe.GTRecipeTypes.ASSEMBLER_RECIPES
import com.gregtechceu.gtceu.data.recipe.GTRecipeTypes.ASSEMBLY_LINE_RECIPES
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper
import dev.arbor.gtnn.data.GTNNItems
import dev.arbor.gtnn.data.GTNNMaterials
import dev.arbor.gtnn.data.GTNNRecipes
import earth.terrarium.adastra.common.registry.ModItems
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import java.util.function.Consumer

object AdAstraRecipes {
    fun init(provider: RecipeOutput) {
        ASSEMBLER_RECIPES.recipeBuilder("heavy_ingot_t1")
            .inputItems(plateDense, Brass)
            .inputItems(plateDense, Aluminium)
            .inputItems(plateDense, Steel)
            .outputItems(GTNNItems.HEAVY_INGOT_T1)
            .inputFluids(StainlessSteel.getFluid(72))
            .circuitMeta(1)
            .duration(GTNNRecipes.dur(15.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("heavy_ingot_t2")
            .inputItems(GTNNItems.HEAVY_INGOT_T1)
            .inputItems(plateDense, GTNNMaterials.Desh, 2)
            .outputItems(GTNNItems.HEAVY_INGOT_T2)
            .inputFluids(TungstenSteel.getFluid(72))
            .circuitMeta(1)
            .duration(GTNNRecipes.dur(15.0)).EUt(GTValues.VA[GTValues.EV].toLong()).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("heavy_ingot_t3")
            .inputItems(GTNNItems.HEAVY_INGOT_T2)
            .inputItems(plateDense, GTNNMaterials.Ostrum, 4)
            .outputItems(GTNNItems.HEAVY_INGOT_T3)
            .inputFluids(Platinum.getFluid(72))
            .circuitMeta(1)
            .duration(GTNNRecipes.dur(15.0)).EUt(GTValues.VA[GTValues.IV].toLong()).save(provider)
        ASSEMBLY_LINE_RECIPES.recipeBuilder("heavy_ingot_t4")
            .inputItems(GTNNItems.HEAVY_INGOT_T3)
            .inputItems(plateDense, GTNNMaterials.Calorite, 3)
            .inputItems(plateDense, GTNNMaterials.Calorite, 3)
            .inputItems(bolt, Ruridit, 4)
            .outputItems(GTNNItems.HEAVY_INGOT_T4)
            .inputFluids(GTNNMaterials.Cerrobase140.getFluid(36))
            .duration(GTNNRecipes.dur(15.0)).EUt(GTValues.VA[GTValues.LuV].toLong()).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("space_helmet")
            .inputItems(Items.CHAINMAIL_HELMET.defaultInstance, ChemicalHelper.get(plate, Glass))
            .outputItems(ModItems.SPACE_HELMET)
            .inputFluids(Glue.getFluid(72))
            .EUt(GTValues.VA[GTValues.MV].toLong()).duration(GTNNRecipes.dur(20.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("netherite_space_helmet")
            .inputItems(ModItems.SPACE_HELMET.get().defaultInstance, GTNNItems.HEAVY_PLATE_T3.asStack(5))
            .outputItems(ModItems.NETHERITE_SPACE_HELMET)
            .inputFluids(StainlessSteel.getFluid(72))
            .EUt(GTValues.VA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("netherite_space_suit")
            .inputItems(
                ModItems.SPACE_SUIT.get().defaultInstance,
                ItemStack(ModItems.STEEL_TANK.get(), 2),
                GTNNItems.HEAVY_PLATE_T3.asStack(8)
            )
            .outputItems(ModItems.NETHERITE_SPACE_SUIT)
            .inputFluids(StainlessSteel.getFluid(72))
            .EUt(GTValues.VA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("netherite_space_pants")
            .inputItems(ModItems.SPACE_PANTS.get().defaultInstance, GTNNItems.HEAVY_PLATE_T3.asStack(7))
            .outputItems(ModItems.NETHERITE_SPACE_PANTS)
            .inputFluids(StainlessSteel.getFluid(72))
            .EUt(GTValues.VA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("netherite_space_boots")
            .inputItems(ModItems.SPACE_BOOTS.get().defaultInstance, GTNNItems.HEAVY_PLATE_T3.asStack(4))
            .outputItems(ModItems.NETHERITE_SPACE_BOOTS)
            .inputFluids(StainlessSteel.getFluid(72))
            .EUt(GTValues.VA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("jet_suit_helmet")
            .inputItems(ModItems.NETHERITE_SPACE_HELMET.get().defaultInstance, GTNNItems.HEAVY_PLATE_T4.asStack(5))
            .outputItems(ModItems.JET_SUIT_HELMET)
            .inputFluids(Titanium.getFluid(144))
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("jet_suit")
            .inputItems(
                ModItems.NETHERITE_SPACE_SUIT.get().defaultInstance, ItemStack(ModItems.OSTRUM_TANK.get(), 2),
                GTItems.POWER_THRUSTER_ADVANCED.asStack(2), GTNNItems.HEAVY_PLATE_T4.asStack(8)
            )
            .outputItems(ModItems.JET_SUIT)
            .inputFluids(Titanium.getFluid(144))
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("jet_suit_pants")
            .inputItems(ModItems.NETHERITE_SPACE_PANTS.get().defaultInstance, GTNNItems.HEAVY_PLATE_T4.asStack(7))
            .outputItems(ModItems.JET_SUIT_PANTS)
            .inputFluids(Titanium.getFluid(144))
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("jet_suit_boots")
            .inputItems(ModItems.NETHERITE_SPACE_BOOTS.get().defaultInstance, GTNNItems.HEAVY_PLATE_T4.asStack(4))
            .outputItems(ModItems.JET_SUIT_BOOTS)
            .inputFluids(Titanium.getFluid(144))
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("desh_engine")
            .inputItems(
                ItemStack(ModItems.STEEL_TANK.get(), 3), GTNNItems.HEAVY_PLATE_T2.asStack(2),
                ItemStack(ModItems.STEEL_ENGINE.get(), 2), GTNNItems.CHIP_T2.asStack()
            )
            .outputItems(ModItems.DESH_ENGINE)
            .inputFluids(Polyethylene.getFluid(144))
            .EUt(GTValues.VA[GTValues.EV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("ostrum_engine")
            .inputItems(
                ItemStack(ModItems.OSTRUM_TANK.get(), 4),
                GTNNItems.HEAVY_PLATE_T3.asStack(4),
                GTNNItems.HEAVY_PLATE_T2.asStack(2),
                ItemStack(ModItems.DESH_ENGINE.get(), 2),
                GTNNItems.CHIP_T3.asStack()
            )
            .outputItems(ModItems.OSTRUM_ENGINE)
            .inputFluids(Polytetrafluoroethylene.getFluid(144))
            .EUt(GTValues.VA[GTValues.IV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("ostrum_tank")
            .inputItems(
                ModItems.STEEL_TANK.get().defaultInstance,
                ChemicalHelper.get(plateDouble, Titanium, 5),
                GTNNItems.CHIP_T3.asStack(4),
                GTNNItems.HEAVY_PLATE_T2.asStack(2)
            )
            .outputItems(ModItems.OSTRUM_TANK)
            .inputFluids(StainlessSteel.getFluid(144))
            .EUt(GTValues.VA[GTValues.HV].toLong()).duration(GTNNRecipes.dur(10.0)).save(provider)
        ASSEMBLER_RECIPES.recipeBuilder("steel_tank")
            .inputItems(GTItems.FLUID_CELL_LARGE_STEEL.asStack(), GTNNItems.CHIP_T1.asStack(2))
            .inputFluids(StainlessSteel.getFluid(72))
            .outputItems(ModItems.STEEL_TANK)
            .circuitMeta(1)
            .EUt(GTValues.VA[GTValues.LV].toLong()).duration(GTNNRecipes.dur(1.0)).save(provider)
        VanillaRecipeHelper.addShapedRecipe(
            provider, "steel_tank", ItemStack(ModItems.STEEL_TANK.get()),
            "DhD", "ABA", "DdD",
            'A', GTNNItems.CHIP_T1.asStack(),
            'B', GTItems.FLUID_CELL_LARGE_STEEL.asStack(),
            'D', UnificationEntry(screw, StainlessSteel)
        )
        ASSEMBLER_RECIPES.recipeBuilder("rocket_nose_cone")
            .inputItems(GTNNItems.HEAVY_PLATE_T1.asStack(4), ItemStack(Items.LIGHTNING_ROD))
            .inputFluids(StainlessSteel.getFluid(36))
            .outputItems(ModItems.ROCKET_NOSE_CONE)
            .circuitMeta(4)
            .EUt(GTValues.VA[GTValues.LV].toLong()).duration(GTNNRecipes.dur(2.5)).save(provider)
        VanillaRecipeHelper.addShapedRecipe(
            provider, "rocket_nose_cone", ItemStack(ModItems.ROCKET_NOSE_CONE.get()),
            "dBh", "ADA", "DDD",
            'A', UnificationEntry(screw, StainlessSteel),
            'B', ItemStack(Items.LIGHTNING_ROD),
            'D', GTNNItems.HEAVY_PLATE_T1.asStack()
        )
        ASSEMBLER_RECIPES.recipeBuilder("rocket_fin")
            .inputItems(plateDouble, Steel, 2)
            .inputItems(GTNNItems.HEAVY_PLATE_T1.asStack(4))
            .inputItems(ModItems.ROCKET_NOSE_CONE)
            .outputItems(ModItems.ROCKET_FIN)
            .circuitMeta(5)
            .EUt(GTValues.VA[GTValues.LV].toLong()).duration(GTNNRecipes.dur(2.5)).save(provider)
        VanillaRecipeHelper.addShapedRecipe(
            provider, "rocket_fin", ItemStack(ModItems.ROCKET_FIN.get()),
            "hAf", "BAB", "BsB",
            'A', UnificationEntry(plateDouble, StainlessSteel),
            'B', GTNNItems.HEAVY_PLATE_T1.asStack()
        )
        ASSEMBLER_RECIPES.recipeBuilder("steel_engine")
            .inputItems(GTNNItems.HEAVY_PLATE_T1.asStack(4), ItemStack(ModItems.STEEL_TANK.get(), 2))
            .inputItems(
                GTBlocks.FIREBOX_STEEL.asStack(),
                GTItems.COVER_ACTIVITY_DETECTOR.asStack(),
                GTNNItems.CHIP_T1.asStack()
            )
            .outputItems(ModItems.STEEL_ENGINE)
            .EUt(GTValues.VA[GTValues.LV].toLong()).duration(GTNNRecipes.dur(5.0)).save(provider)
        VanillaRecipeHelper.addShapedRecipe(
            provider, "steel_engine", ItemStack(ModItems.STEEL_ENGINE.get()),
            "DED", "CBC", "DAD",
            'A', GTItems.COVER_ACTIVITY_DETECTOR.asStack(),
            'B', GTBlocks.FIREBOX_STEEL.asStack(),
            'C', ItemStack(ModItems.STEEL_TANK.get()),
            'D', GTNNItems.HEAVY_PLATE_T1.asStack(),
            'E', GTNNItems.CHIP_T1.asStack()
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, "space_suit", ItemStack(ModItems.SPACE_SUIT.get()),
            "ADA", "CBC", "ADA",
            'A', GTNNItems.HEAVY_PLATE_T1.asStack(),
            'B', ItemStack(ModItems.OXYGEN_GEAR.get()),
            'C', ItemStack(ModItems.GAS_TANK.get()),
            'D', UnificationEntry(screw, StainlessSteel)
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, "oxygen_gear", ItemStack(ModItems.OXYGEN_GEAR.get()),
            "AEA", "CBC", "ADA",
            'A', UnificationEntry(plateDouble, Steel),
            'B', GTItems.ELECTRIC_PUMP_HV.asStack(),
            'C', GTItems.FLUID_CELL.asStack(),
            'D', UnificationEntry(rotor, Steel),
            'E', GTItems.SENSOR_HV
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, "space_pants", ItemStack(ModItems.SPACE_PANTS.get()),
            "AAA", "AhA", "A A",
            'A', GTNNItems.HEAVY_PLATE_T1.asStack()
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, "space_boots", ItemStack(ModItems.SPACE_BOOTS.get()),
            "AhA", "A A",
            'A', GTNNItems.HEAVY_PLATE_T1.asStack()
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, "oxygen_tank", ItemStack(ModItems.GAS_TANK.get()),
            "AhA", "ACA", "AAA",
            'A', UnificationEntry(plateDouble, Aluminium),
            'C', GTItems.FLUID_CELL.asStack()
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, "fan", ItemStack(ModItems.FAN.get()),
            "AwA", "ECE", "AdA",
            'A', GTNNItems.HEAVY_PLATE_T1.asStack(),
            'C', UnificationEntry(rodLong, StainlessSteel),
            'E', UnificationEntry(rotor, Steel)
        )
        VanillaRecipeHelper.addShapedRecipe(
            provider, "oxygen_loader", ItemStack(ModItems.OXYGEN_LOADER.get()),
            "ABA", "CDE", "FGF",
            'A', UnificationEntry(plateDouble, Steel),
            'B', ItemStack(ModItems.OXYGEN_GEAR.get()),
            'C', ItemStack(Items.IRON_BARS),
            'D', ItemStack(ModItems.FAN.get()),
            'E', GTItems.ELECTRIC_MOTOR_HV.asStack(),
            'F', UnificationEntry(plateDouble, Aluminium),
            'G', UnificationEntry(cableGtDouble, Aluminium)
        )
        ASSEMBLY_LINE_RECIPES.recipeBuilder("calorite_engine")
            .inputItems(GTNNItems.HEAVY_PLATE_T4.asStack(32), GTNNItems.HEAVY_PLATE_T3.asStack(16))
            .inputItems(ItemStack(ModItems.OSTRUM_ENGINE.get(), 8), GTNNItems.CHIP_T4.asStack(2))
            .inputItems(ItemStack(ModItems.OSTRUM_TANK.get(), 8))
            .outputItems(ModItems.CALORITE_ENGINE)
            .inputFluids(Platinum.getFluid(4032))
            .inputFluids(Iridium.getFluid(2016))
            .inputFluids(Palladium.getFluid(1008))
            .inputFluids(Osmium.getFluid(504))
            .EUt(GTValues.VA[GTValues.LuV].toLong()).duration(GTNNRecipes.dur(30.0)).save(provider)
    }

    fun remove(consumer: Consumer<ResourceLocation>) {
        consumer.accept(ResourceLocation.parse("ad_astra:space_helmet"))
        consumer.accept(ResourceLocation.parse("ad_astra:space_suit"))
        consumer.accept(ResourceLocation.parse("ad_astra:space_pants"))
        consumer.accept(ResourceLocation.parse("ad_astra:space_boots"))
        consumer.accept(ResourceLocation.parse("ad_astra:netherite_space_helmet"))
        consumer.accept(ResourceLocation.parse("ad_astra:netherite_space_suit"))
        consumer.accept(ResourceLocation.parse("ad_astra:netherite_space_pants"))
        consumer.accept(ResourceLocation.parse("ad_astra:netherite_space_boots"))
        consumer.accept(ResourceLocation.parse("ad_astra:jet_suit_helmet"))
        consumer.accept(ResourceLocation.parse("ad_astra:jet_suit"))
        consumer.accept(ResourceLocation.parse("ad_astra:jet_suit_pants"))
        consumer.accept(ResourceLocation.parse("ad_astra:jet_suit_boots"))
        consumer.accept(ResourceLocation.parse("ad_astra:steel_engine"))
        consumer.accept(ResourceLocation.parse("ad_astra:rocket_nose_cone"))
        consumer.accept(ResourceLocation.parse("ad_astra:rocket_fin"))
        consumer.accept(ResourceLocation.parse("ad_astra:steel_tank"))
        consumer.accept(ResourceLocation.parse("ad_astra:nasa_workbench/tier_1_rocket_from_nasa_workbench"))
        consumer.accept(ResourceLocation.parse("ad_astra:nasa_workbench/tier_2_rocket_from_nasa_workbench"))
        consumer.accept(ResourceLocation.parse("ad_astra:nasa_workbench/tier_3_rocket_from_nasa_workbench"))
        consumer.accept(ResourceLocation.parse("ad_astra:nasa_workbench/tier_4_rocket_from_nasa_workbench"))
        consumer.accept(ResourceLocation.parse("ad_astra:desh_engine"))
        consumer.accept(ResourceLocation.parse("ad_astra:desh_tank"))
        consumer.accept(ResourceLocation.parse("ad_astra:ostrum_engine"))
        consumer.accept(ResourceLocation.parse("ad_astra:ostrum_tank"))
        consumer.accept(ResourceLocation.parse("ad_astra:calorite_engine"))
        consumer.accept(ResourceLocation.parse("ad_astra:calorite_tank"))
        consumer.accept(ResourceLocation.parse("ad_astra:oxygen_gear"))
        consumer.accept(ResourceLocation.parse("ad_astra:oxygen_tank"))
        consumer.accept(ResourceLocation.parse("ad_astra:fan"))
        consumer.accept(ResourceLocation.parse("ad_astra:gas_tank"))
        consumer.accept(ResourceLocation.parse("ad_astra:oxygen_loader"))
    }
}
