package dev.arbor.gtnn.data.recipes

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType
import com.gregtechceu.gtceu.api.material.material.stack.UnificationEntry
import com.gregtechceu.gtceu.api.tag.TagPrefix
import com.gregtechceu.gtceu.data.block.GTBlocks
import com.gregtechceu.gtceu.data.item.GTItems
import com.gregtechceu.gtceu.data.machine.GTMachines
import com.gregtechceu.gtceu.data.material.GTMaterials
import com.gregtechceu.gtceu.data.recipe.CustomTags
import com.gregtechceu.gtceu.data.recipe.GTRecipeTypes
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.GTNNIntegration.isCCTweakedLoaded
import dev.arbor.gtnn.GTNNIntegration.isSupplementariesLoaded
import dev.arbor.gtnn.data.GTNNItems
import dev.arbor.gtnn.data.GTNNMachines.STONE_BEDROCK_ORE_MACHINE
import dev.arbor.gtnn.data.GTNNMaterials.Cerrobase140
import dev.arbor.gtnn.data.GTNNRecipes
import net.mehvahdjukaar.supplementaries.Supplementaries
import net.mehvahdjukaar.supplementaries.reg.ModRegistry
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import java.util.function.Consumer
import java.util.function.Supplier

object DefaultRecipes {
    fun init(provider: RecipeOutput) {
        Misc.init(provider)
        SelfRecipes.init(provider)
        addBOOMRecipes(
            "heavy_plate_t1",
            GTNNItems.HEAVY_INGOT_T1,
            GTNNItems.HEAVY_PLATE_T1,
            GTNNRecipes.dur(15.0),
            1,
            provider
        )
        addBOOMRecipes(
            "heavy_plate_t2",
            GTNNItems.HEAVY_INGOT_T2,
            GTNNItems.HEAVY_PLATE_T2,
            GTNNRecipes.dur(15.0),
            2,
            provider
        )
        addBOOMRecipes(
            "heavy_plate_t3",
            GTNNItems.HEAVY_INGOT_T3,
            GTNNItems.HEAVY_PLATE_T3,
            GTNNRecipes.dur(15.0),
            3,
            provider
        )
        addBOOMRecipes(
            "heavy_plate_t4",
            GTNNItems.HEAVY_INGOT_T4,
            GTNNItems.HEAVY_PLATE_T4,
            GTNNRecipes.dur(15.0),
            4,
            provider
        )
    }

    fun addBOOMRecipes(
        name: String,
        input: Supplier<out Item?>,
        output: Supplier<out Item?>,
        time: Int,
        level: Int,
        provider: RecipeOutput
    ) {
        GTRecipeTypes.IMPLOSION_RECIPES.recipeBuilder("implode_" + name + "_tnt")
            .inputItems(input)
            .outputItems(output)
            .chancedOutput(TagPrefix.dust, GTMaterials.DarkAsh, 2500, 0)
            .explosivesType(ItemStack(Items.TNT, level * 4))
            .duration(time)
            .save(provider)

        GTRecipeTypes.IMPLOSION_RECIPES.recipeBuilder("implode_" + name + "_powderbarrel")
            .inputItems(input)
            .outputItems(output)
            .chancedOutput(TagPrefix.dust, GTMaterials.DarkAsh, 2500, 0)
            .explosivesType(ItemStack(GTBlocks.POWDERBARREL, level * 8))
            .duration(time)
            .save(provider)

        GTRecipeTypes.IMPLOSION_RECIPES.recipeBuilder("implode_" + name + "_dynamite")
            .inputItems(input)
            .outputItems(output)
            .chancedOutput(TagPrefix.dust, GTMaterials.DarkAsh, 2500, 0)
            .explosivesType(GTItems.DYNAMITE.asStack(level * 2))
            .duration(time)
            .save(provider)

        GTRecipeTypes.IMPLOSION_RECIPES.recipeBuilder("implode_" + name + "_itnt")
            .inputItems(input)
            .outputItems(output)
            .chancedOutput(TagPrefix.dust, GTMaterials.DarkAsh, 2500, 0)
            .explosivesType(ItemStack(GTBlocks.INDUSTRIAL_TNT, level))
            .duration(time)
            .save(provider)

        if (!isSupplementariesLoaded()) return
        GTRecipeTypes.IMPLOSION_RECIPES.recipeBuilder("implode_" + name + "_bomb")
            .inputItems(input)
            .outputItems(output)
            .chancedOutput(TagPrefix.dust, GTMaterials.DarkAsh, 2500, 0)
            .explosivesType(ItemStack(ModRegistry.BOMB_ITEM.get(), level * 4))
            .duration(time)
            .save(provider)
    }

    object Misc {
        fun init(provider: RecipeOutput) {
            if (GTNN.getServerConfig().skyblock) {
                VanillaRecipeHelper.addShapedRecipe(
                    provider, "homemade_bedrock_ore_machine", STONE_BEDROCK_ORE_MACHINE.asStack(),
                    "ABA", "CDC", "AEA",
                    'A', UnificationEntry(TagPrefix.plate, GTMaterials.WroughtIron),
                    'B', UnificationEntry(TagPrefix.plate, GTMaterials.Steel),
                    'C', Items.AMETHYST_SHARD,
                    'D', GTBlocks.BRONZE_HULL,
                    'E', UnificationEntry(TagPrefix.toolHeadDrill, GTMaterials.Diamond)
                )
                GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("bedrock_ore_machine_i")
                    .inputItems(GTMachines.HULL[GTValues.MV])
                    .inputItems(TagPrefix.frameGt, GTMaterials.Aluminium, 4)
                    .inputItems(CustomTags.MV_CIRCUITS, 4)
                    .inputItems(GTItems.ELECTRIC_MOTOR_MV, 4)
                    .inputItems(GTItems.ELECTRIC_PUMP_MV, 4)
                    .inputItems(TagPrefix.gear, GTMaterials.Steel, 4)
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(72))
                    .outputItems(GTMachines.BEDROCK_ORE_MINER[GTValues.MV])
                    .circuitMeta(2)
                    .duration(GTNNRecipes.dur(20.0)).EUt(GTValues.VA[GTValues.MV].toLong()).save(provider)
                GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("bedrock_ore_machine_ii")
                    .inputItems(GTMachines.LARGE_MINER[GTValues.EV])
                    .inputItems(TagPrefix.frameGt, GTMaterials.Titanium, 4)
                    .inputItems(CustomTags.EV_CIRCUITS, 4)
                    .inputItems(GTItems.ELECTRIC_MOTOR_EV, 4)
                    .inputItems(GTItems.ELECTRIC_PUMP_EV, 4)
                    .inputItems(TagPrefix.gear, GTMaterials.Tungsten, 4)
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(GTMachines.BEDROCK_ORE_MINER[GTValues.HV])
                    .circuitMeta(2)
                    .duration(GTNNRecipes.dur(60.0)).EUt(GTValues.VA[GTValues.EV].toLong()).save(provider)
                GTRecipeTypes.ASSEMBLY_LINE_RECIPES.recipeBuilder("bedrock_ore_machine_iii")
                    .inputItems(GTMachines.LARGE_MINER[GTValues.LuV])
                    .inputItems(TagPrefix.frameGt, GTMaterials.Tritanium, 9)
                    .inputItems(TagPrefix.plate, GTMaterials.Europium, 3)
                    .inputItems(GTItems.ELECTRIC_MOTOR_LuV, 9)
                    .inputItems(GTItems.EMITTER_LuV, 9)
                    .inputItems(GTItems.FIELD_GENERATOR_LuV, 9)
                    .inputItems(TagPrefix.screw, GTMaterials.Europium, 36)
                    .inputFluids(Cerrobase140.getFluid(1440))
                    .inputFluids(GTMaterials.Neon.getFluid(20000))
                    .outputItems(GTMachines.BEDROCK_ORE_MINER[GTValues.EV])
                    .scannerResearch(GTMachines.LARGE_MINER[GTValues.LuV].asStack())
                    .duration(GTNNRecipes.dur(300.0)).EUt(GTValues.VA[GTValues.LuV].toLong()).save(provider)
            }
            if (isSupplementariesLoaded()) {
                GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("bomb")
                    .inputItems(Items.PAPER)
                    .inputItems(Items.STRING)
                    .inputFluids(GTMaterials.GlycerylTrinitrate.getFluid(500))
                    .outputItems(ModRegistry.BOMB_ITEM)
                    .circuitMeta(1)
                    .duration(GTNNRecipes.dur(8.0)).EUt(GTValues.VA[GTValues.ULV].toLong()).save(provider)
            }
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("computer_normal")
                .inputItems(GTBlocks.MACHINE_CASING_MV.asStack(2))
                .inputItems(TagPrefix.plate, GTMaterials.Aluminium, 2)
                .inputItems(TagPrefix.wireFine, GTMaterials.Aluminium, 2)
                .inputItems(TagPrefix.rotor, GTMaterials.Iron)
                .inputItems(GTItems.PLASTIC_CIRCUIT_BOARD)
                .inputItems(GTItems.BASIC_CIRCUIT_BOARD)
                .inputFluids(GTMaterials.Polyethylene.getFluid(72))
                .outputItems(computer)
                .duration(GTNNRecipes.dur(10.0)).EUt(GTValues.VA[GTValues.MV].toLong()).save(provider)
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("computer_advanced")
                .inputItems(GTBlocks.MACHINE_CASING_HV.asStack())
                .inputItems(TagPrefix.plate, GTMaterials.Aluminium, 2)
                .inputItems(TagPrefix.wireFine, GTMaterials.Tantalum, 2)
                .inputItems(TagPrefix.rotor, GTMaterials.Iron)
                .inputItems(GTItems.ADVANCED_CIRCUIT_BOARD)
                .inputItems(GTItems.INTEGRATED_CIRCUIT_HV)
                .inputFluids(GTMaterials.Polyethylene.getFluid(72))
                .outputItems(computerAdvanced)
                .duration(GTNNRecipes.dur(10.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("lightning_rod")
                .inputItems(TagPrefix.rodLong, GTMaterials.Copper, 2)
                .inputItems(TagPrefix.plate, GTMaterials.Copper, 3)
                .outputItems(Items.LIGHTNING_ROD)
                .EUt(GTValues.VA[GTValues.LV].toLong()).duration(50)
                .circuitMeta(9).save(provider)
            VanillaRecipeHelper.addShapedRecipe(
                provider, "lightning_rod", ItemStack(Items.LIGHTNING_ROD),
                "hAf", "ABA", "dBs",
                'A', UnificationEntry(TagPrefix.plate, GTMaterials.Copper),
                'B', UnificationEntry(TagPrefix.rodLong, GTMaterials.Copper)
            )
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("t1_chip")
                .inputItems(computer.get()!!.asItem())
                .inputItems(GTItems.COVER_SCREEN)
                .inputItems(GTItems.SENSOR_HV)
                .inputItems(GTItems.EMITTER_HV)
                .inputItems(GTNNItems.HEAVY_PLATE_T1)
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(576))
                .outputItems(GTNNItems.CHIP_T1)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(GTNNRecipes.dur(30.0)).EUt(GTValues.VA[GTValues.HV].toLong()).save(provider)
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("t2_chip")
                .inputItems(computer.get()!!.asItem())
                .inputItems(GTItems.COVER_SCREEN)
                .inputItems(GTItems.FIELD_GENERATOR_EV.asStack(2))
                .inputItems(GTNNItems.HEAVY_PLATE_T2)
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(864))
                .outputItems(GTNNItems.CHIP_T2)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(GTNNRecipes.dur(30.0)).EUt(GTValues.VA[GTValues.EV].toLong()).save(provider)
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("t3_chip")
                .inputItems(computer.get()!!.asItem())
                .inputItems(GTItems.COVER_SCREEN)
                .inputItems(GTItems.SENSOR_IV)
                .inputItems(GTItems.EMITTER_IV)
                .inputItems(GTNNItems.HEAVY_PLATE_T3)
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(1152))
                .outputItems(GTNNItems.CHIP_T3)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(GTNNRecipes.dur(30.0)).EUt(GTValues.VA[GTValues.IV].toLong()).save(provider)
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("t4_chip")
                .inputItems(computerAdvanced.get()!!.asItem())
                .inputItems(GTItems.COVER_SCREEN)
                .inputItems(GTItems.SENSOR_LuV)
                .inputItems(GTItems.EMITTER_LuV)
                .inputItems(GTNNItems.HEAVY_PLATE_T4)
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(2304))
                .outputItems(GTNNItems.CHIP_T4)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(GTNNRecipes.dur(30.0)).EUt(GTValues.VA[GTValues.LuV].toLong()).save(provider)
        }

        private val computerAdvanced: Supplier<out ItemLike?>
            get() = if (isCCTweakedLoaded()) dan200.computercraft.shared.ModRegistry.Blocks.COMPUTER_ADVANCED else GTNNItems.COMPUTER_ADVANCED

        private val computer: Supplier<out ItemLike?>
            get() = if (isCCTweakedLoaded()) dan200.computercraft.shared.ModRegistry.Blocks.COMPUTER_NORMAL else GTNNItems.COMPUTER

        fun removeRecipes(consumer: Consumer<ResourceLocation>) {
            consumer.accept(ResourceLocation.parse("minecraft:lightning_rod"))
            if (isSupplementariesLoaded()) {
                consumer.accept(Supplementaries.res("bomb"))
            }
            if (isCCTweakedLoaded()) {
                consumer.accept(ResourceLocation.fromNamespaceAndPath("computercraft", "computer_normal"))
                consumer.accept(ResourceLocation.fromNamespaceAndPath("computercraft", "computer_advanced"))
                consumer.accept(ResourceLocation.fromNamespaceAndPath("computercraft", "computer_advanced_upgrade"))
            }
        }
    }
}