package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.GTValues.*
import com.gregtechceu.gtceu.api.block.ICoilType
import com.gregtechceu.gtceu.api.data.RotationState
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.machine.MachineDefinition
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo
import com.gregtechceu.gtceu.api.pattern.Predicates.*
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic
import com.gregtechceu.gtceu.client.renderer.machine.MinerRenderer
import com.gregtechceu.gtceu.common.data.*
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils
import com.gregtechceu.gtceu.utils.FormattingUtil
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.api.block.BlockMaps
import dev.arbor.gtnn.api.block.ITierType
import dev.arbor.gtnn.api.machine.GTNNGeneratorMachine
import dev.arbor.gtnn.api.machine.MachineReg
import dev.arbor.gtnn.api.machine.ModifyMachines
import dev.arbor.gtnn.api.machine.StoneBedrockOreMinerMachine
import dev.arbor.gtnn.api.machine.multiblock.APartAbility
import dev.arbor.gtnn.api.machine.multiblock.ChemicalPlantMachine
import dev.arbor.gtnn.api.machine.multiblock.LargeNaquadahReactorMachine
import dev.arbor.gtnn.api.machine.multiblock.NeutronActivatorMachine
import dev.arbor.gtnn.api.machine.multiblock.part.CatalystHatchPartMachine
import dev.arbor.gtnn.api.machine.multiblock.part.HighSpeedPipeBlock
import dev.arbor.gtnn.api.machine.multiblock.part.NeutronAcceleratorMachine
import dev.arbor.gtnn.api.machine.multiblock.part.NeutronSensorMachine
import dev.arbor.gtnn.api.pattern.APredicates
import dev.arbor.gtnn.api.tool.StringTools.gt
import dev.arbor.gtnn.api.tool.StringTools.nn
import dev.arbor.gtnn.client.renderer.machine.BlockMachineRenderer
import dev.arbor.gtnn.client.renderer.machine.GTPPMachineRenderer
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Supplier

object GTNNMachines {
    private val ULV2UV: IntArray = tiersBetween(0, 8)
    private val MV2ZPM: IntArray = tiersBetween(2, 7)
    private val EV2UV: IntArray = tiersBetween(4, 8)

    //////////////////////////////////////
    //**********    Block     **********//
    //////////////////////////////////////
    val HIGH_SPEED_PIPE_BLOCK: MachineDefinition = REGISTRATE.machine("high_speed_pipe_block", ::HighSpeedPipeBlock)
        .renderer { BlockMachineRenderer(GTNN.id("block/machine/part/high_speed_pipe_block")) }
        .rotationState(RotationState.Y_AXIS).register()

    //////////////////////////////////////
    //**********     Part     **********//
    //////////////////////////////////////
    val NEUTRON_ACCELERATOR: Array<MachineDefinition?> = MachineReg.registerTieredMachines(
        "neutron_accelerator", ::NeutronAcceleratorMachine, { tier, builder ->
            builder.langValue(VNF[tier] + " Neutron Accelerator").rotationState(RotationState.ALL)
                .abilities(APartAbility.NEUTRON_ACCELERATOR)
                .tooltips(Component.translatable("gtnn.machine.neutron_accelerator.tooltip1"))
                .tooltips(Component.translatable("gtnn.machine.neutron_accelerator.tooltip2", V[tier]))
                .tooltips(Component.translatable("gtnn.machine.neutron_accelerator.tooltip3", V[tier] * 8 / 10))
                .tooltips(Component.translatable("gtnn.machine.neutron_accelerator.tooltip4"))
                .overlayTieredHullRenderer("neutron_accelerator").register()
        }, ULV2UV
    )

    val NEUTRON_SENSOR: MachineDefinition =
        REGISTRATE.machine("neutron_sensor", ::NeutronSensorMachine).langValue("Neutron Sensor").tier(IV)
            .rotationState(RotationState.ALL).abilities(APartAbility.NEUTRON_SENSOR)
            .overlayTieredHullRenderer("neutron_sensor")
            .tooltips(Component.translatable("block.gtnn.neutron_sensor.tooltip1"))
            .tooltips(Component.translatable("block.gtnn.neutron_sensor.tooltip2")).register()

    val CATALYST_HATCH: MachineDefinition = REGISTRATE
        .machine("catalyst_hatch", ::CatalystHatchPartMachine)
        .langValue("Catalyst Hatch")
        .tier(IV)
        .rotationState(RotationState.ALL)
        .abilities(APartAbility.CATALYST)
        .overlayTieredHullRenderer("catalyst_hatch")
        .tooltips()
        .register()
    //////////////////////////////////////
    //**********   Machine    **********//
    //////////////////////////////////////

    val DEHYDRATOR: Array<MachineDefinition?> = MachineReg.registerSimpleMachines(
        "dehydrator", GTNNRecipeTypes.DEHYDRATOR_RECIPES, GTMachineUtils.defaultTankSizeFunction, MV2ZPM
    )


    val NAQUADAH_REACTOR: Array<MachineDefinition?> = MachineReg.registerGTNNGeneratorMachines(
        "naquadah_reactor",
        GTNNRecipeTypes.NAQUADAH_REACTOR_RECIPES,
        GTNNGeneratorMachine::nonParallel,
        GTMachineUtils.genericGeneratorTankSizeFunction,
        EV2UV
    )


    val Rocket_Engine: Array<MachineDefinition?> = MachineReg.registerGTNNGeneratorMachines(
        "rocket_engine",
        GTNNRecipeTypes.ROCKET_ENGINE_RECIPES,
        GTNNGeneratorMachine::parallel,
        GTMachineUtils.genericGeneratorTankSizeFunction,
        intArrayOf(EV, IV, LuV)
    )

    val STONE_BEDROCK_ORE_MACHINE: MachineDefinition =
        REGISTRATE.machine("homemade_bedrock_ore_machine", ::StoneBedrockOreMinerMachine)
            .langValue("Homemade Bedrock Ore Machine").rotationState(RotationState.ALL)
            .recipeType(GTNNRecipeTypes.STONE_BEDROCK_ORE_MACHINE_RECIPES).editableUI(
                SimpleTieredMachine.EDITABLE_UI_CREATOR.apply(
                    "homemade_bedrock_ore_machine".nn(), GTNNRecipeTypes.STONE_BEDROCK_ORE_MACHINE_RECIPES
                )
            ).renderer { MinerRenderer(0, GTCEu.id("block/machines/miner")) }
            .tooltips(Component.translatable("gtceu.machine.bedrock_ore_miner.description")).tooltips(
                Component.translatable(
                    "gtceu.machine.bedrock_ore_miner.depletion", FormattingUtil.formatNumbers(100.0)
                )
            ).register()

    val CHEMICAL_PLANT: MultiblockMachineDefinition = REGISTRATE.multiblock(
        "exxonmobil_chemical_plant"
    ) { ChemicalPlantMachine(it) }.rotationState(RotationState.NON_Y_AXIS)
        .tooltips(Component.translatable("gtnn.multiblock.chemical_plant.tooltip1"))
        .tooltips(Component.translatable("gtnn.multiblock.chemical_plant.tooltip2"))
        .tooltips(Component.translatable("gtnn.multiblock.chemical_plant.tooltip3"))
        .tooltips(Component.translatable("gtnn.multiblock.chemical_plant.tooltip4"))
        .recipeTypes(GTNNRecipeTypes.CHEMICAL_PLANT_RECIPES)
        .recipeModifiers(
            GTNNRecipeModifiers.GTNN_PARALLEL,
            GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK)
        )
        .appearanceBlock(GTBlocks.CASING_BRONZE_BRICKS)
        .pattern { definition ->
            FactoryBlockPattern.start()
                .aisle("VVVVVVV", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                .aisle("VBBBBBV", "#BBBBB#", "#######", "#######", "#######", "#BBBBB#", "AAAAAAA")
                .aisle("VBBBBBV", "#BCCCB#", "##DDD##", "##CCC##", "##DDD##", "#BCCCB#", "AAAAAAA")
                .aisle("VBBBBBV", "#BCCCB#", "##DDD##", "##CCC##", "##DDD##", "#BCCCB#", "AAAAAAA")
                .aisle("VBBBBBV", "#BCCCB#", "##DDD##", "##CCC##", "##DDD##", "#BCCCB#", "AAAAAAA")
                .aisle("VBBBBBV", "#BBBBB#", "#######", "#######", "#######", "#BBBBB#", "AAAAAAA")
                .aisle("AVVSVVA", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                .where("S", controller(blocks(definition.get()))).where(
                    "V",
                    APredicates.plantCasings().or(autoAbilities(*definition.recipeTypes))
                        .or(autoAbilities(true, false, false))
                        .or(abilities(PartAbility.INPUT_ENERGY)).or(abilities(APartAbility.CATALYST))
                        .or(abilities(PartAbility.IMPORT_ITEMS)).or(abilities(PartAbility.EXPORT_ITEMS))
                        .or(abilities(PartAbility.IMPORT_FLUIDS)).or(abilities(PartAbility.EXPORT_FLUIDS))
                ).where("A", APredicates.plantCasings()).where("D", APredicates.pipeBlock()).where("C", heatingCoils())
                .where("B", APredicates.machineCasing()).where("#", air()).build()
        }.shapeInfos { definition ->
            val shapeInfo = mutableListOf<MultiblockShapeInfo>()
            val builder = MultiblockShapeInfo.builder()
                .aisle("AAOSJPA", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                .aisle("MBBBBBN", "#BBBBB#", "#######", "#######", "#######", "#BBBBB#", "AAAAAAA")
                .aisle("KBBBBBL", "#BCCCB#", "##DDD##", "##CCC##", "##DDD##", "#BCCCB#", "AAAAAAA")
                .aisle("ABBBBBA", "#BCCCB#", "##DDD##", "##CCC##", "##DDD##", "#BCCCB#", "AAAAAAA")
                .aisle("ABBBBBA", "#BCCCB#", "##DDD##", "##CCC##", "##DDD##", "#BCCCB#", "AAAAAAA")
                .aisle("ABBBBBA", "#BBBBB#", "#######", "#######", "#######", "#BBBBB#", "AAAAAAA")
                .aisle("AAAAAAA", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                .where('S', definition, Direction.NORTH).where('#', Blocks.AIR.defaultBlockState())
                .where('J', GTMachines.MAINTENANCE_HATCH, Direction.NORTH)
                .where('P', CATALYST_HATCH, Direction.NORTH)
            val shapeBlock = hashMapOf<Int, BlockState>()
            for (casing in BlockMaps.ALL_CP_CASINGS) {
                shapeBlock[casing.key.tier + 9] = casing.value.get().defaultBlockState()
            }
            for (machineCasing in BlockMaps.ALL_MACHINE_CASINGS) {
                shapeBlock[machineCasing.key.tier + 20] = machineCasing.value.get().defaultBlockState()
            }
            for (coil in GTCEuAPI.HEATING_COILS) {
                shapeBlock[coil.key.tier + 30] = coil.value.get().defaultBlockState()
            }
            for (pipe in BlockMaps.ALL_CP_TUBES) {
                shapeBlock[pipe.key.tier + 39] = pipe.value.get().defaultBlockState()
            }
            for (tier in ITierType.TierBlockType.entries.map { it.tier }.filter { it in 0..8 }) {
                builder.where('A', shapeBlock.getOrDefault(tier + 10, BlockMaps.ALL_CP_CASINGS.getMax()))
                builder.where('B', shapeBlock.getOrDefault(tier + 20, BlockMaps.ALL_MACHINE_CASINGS.getMax()))
                builder.where('C', shapeBlock.getOrDefault(tier + 30, GTCEuAPI.HEATING_COILS.getMax()))
                builder.where('D', shapeBlock.getOrDefault(tier + 40, BlockMaps.ALL_CP_TUBES.getMax()))
                builder.where('K', GTMachines.ITEM_IMPORT_BUS[tier], Direction.WEST)
                builder.where('L', GTMachines.ITEM_EXPORT_BUS[tier], Direction.EAST)
                builder.where('M', GTMachines.FLUID_IMPORT_HATCH[tier], Direction.WEST)
                builder.where('N', GTMachines.FLUID_EXPORT_HATCH[tier], Direction.EAST)
                builder.where('O', GTMachines.ENERGY_INPUT_HATCH[tier], Direction.NORTH)
                shapeInfo.add(builder.shallowCopy().build())
            }
            return@shapeInfos shapeInfo
        }.renderer {
            GTPPMachineRenderer(
                GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                GTNN.id("block/multiblock/chemical_plant"), false
            )
        }.register()

    private fun Map<ITierType, Supplier<Block>>.getMax(): BlockState {
        return this.entries.maxBy { it.key.tier }.value.get().defaultBlockState()
    }

    @JvmName("getCoilMax")
    private fun Map<ICoilType, Supplier<out Block>>.getMax(): BlockState {
        return this.entries.maxBy { it.key.tier }.value.get().defaultBlockState()
    }


    val NEUTRON_ACTIVATOR: MultiblockMachineDefinition =
        REGISTRATE.multiblock("neutron_activator") { NeutronActivatorMachine(it) }
            .rotationState(RotationState.NON_Y_AXIS)
            .tooltips(Component.translatable("gtnn.multiblock.neutron_activator.tooltip1"))
            .tooltips(Component.translatable("gtnn.multiblock.neutron_activator.tooltip2"))
            .tooltips(Component.translatable("gtnn.multiblock.neutron_activator.tooltip3"))
            .tooltips(Component.translatable("gtnn.multiblock.neutron_activator.tooltip4"))
            .tooltips(Component.translatable("gtnn.multiblock.neutron_activator.tooltip5"))
            .recipeTypes(GTNNRecipeTypes.NEUTRON_ACTIVATOR_RECIPES).appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .pattern { definition ->
                FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP)
                    .aisle("AASAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                    .aisle("C###C", "#DDD#", "#DED#", "#DDD#", "C###C").setRepeatable(4, 34)
                    .aisle("VVVVV", "VBBBV", "VBBBV", "VBBBV", "VVVVV").where("S", controller(blocks(definition.get())))
                    .where(
                        "V",
                        blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()).or(abilities(PartAbility.IMPORT_FLUIDS))
                            .or(abilities(PartAbility.IMPORT_ITEMS))
                    ).where(
                        "A",
                        blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()).or(abilities(PartAbility.EXPORT_FLUIDS))
                            .or(abilities(PartAbility.EXPORT_ITEMS)).or(abilities(APartAbility.NEUTRON_ACCELERATOR))
                            .or(abilities(APartAbility.NEUTRON_SENSOR)).or(autoAbilities(true, false, false))
                    ).where("B", blocks(GTNNCasingBlocks.PROCESS_MACHINE_CASING.get()))
                    .where("C", blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.Steel)))
                    .where("D", blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                    .where("E", blocks(HIGH_SPEED_PIPE_BLOCK.get())).where("#", air()).build()
            }.workableCasingRenderer(
                GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
                GTNN.id("block/multiblock/neutron_activator"),
                false
            ).register()

    val LARGE_DEHYDRATOR: MultiblockMachineDefinition =
        REGISTRATE.multiblock("large_dehydrator") { WorkableElectricMultiblockMachine(it) }
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTNNRecipeTypes.DEHYDRATOR_RECIPES)
            .recipeModifiers(GTRecipeModifiers.DEFAULT_ENVIRONMENT_REQUIREMENT,
                GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK))
            .appearanceBlock(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING)
            .pattern { definition ->
                FactoryBlockPattern.start()
                    .aisle("XXX", "CCC", "CCC", "CCC", "XXX")
                    .aisle("XXX", "C#C", "C#C", "C#C", "XXX")
                    .aisle("XSX", "CCC", "CCC", "CCC", "XXX")
                    .where('S', controller(blocks(definition.block)))
                    .where(
                        'X', blocks(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.get()).setMinGlobalLimited(9)
                            .or(autoAbilities(*definition.recipeTypes))
                            .or(autoAbilities(true, false, true))
                    )
                    .where('C', blocks(GTBlocks.COIL_NAQUADAH.get()))
                    .where('#', air())
                    .build()
            }.workableCasingRenderer(
                "block/casings/gcym/high_temperature_smelting_casing".gt(),
                "block/multiblock/gcym/large_assembler".gt(), false
            )
            .register()


    val LargeNaquadahReactor: MultiblockMachineDefinition =
        REGISTRATE.multiblock("large_naquadah_reactor") { LargeNaquadahReactorMachine(it) }
            .rotationState(RotationState.NON_Y_AXIS)
            .tooltips(Component.translatable("gtnn.multiblock.large_naquadah_reactor.tooltip1"))
            .tooltips(Component.translatable("gtnn.multiblock.large_naquadah_reactor.tooltip2"))
            .tooltips(Component.translatable("gtnn.multiblock.large_naquadah_reactor.tooltip3"))
            .tooltips(Component.translatable("gtnn.multiblock.large_naquadah_reactor.tooltip4"))
            .tooltips(Component.translatable("gtnn.multiblock.large_naquadah_reactor.tooltip5"))
            .tooltips(Component.translatable("gtnn.multiblock.large_naquadah_reactor.tooltip6"))
            .tooltips(Component.translatable("gtnn.multiblock.large_naquadah_reactor.tooltip7"))
            .tooltips(Component.translatable("gtnn.multiblock.large_naquadah_reactor.tooltip8"))
            .tooltips(Component.translatable("gtnn.multiblock.large_naquadah_reactor.tooltip9"))
            .recipeTypes(GTNNRecipeTypes.LARGE_NAQUADAH_REACTOR_RECIPES)
            .recipeModifier(LargeNaquadahReactorMachine::modifyRecipe)
            .appearanceBlock(GTNNCasingBlocks.RADIATION_PROOF_MACHINE_CASING).pattern { definition ->
                FactoryBlockPattern.start()
                    .aisle("AAAAAAA", "VBBBBBV", "VVVVVVV", "B#####B", "B#####B", "B#####B", "B#####B", "VVVVVVV")
                    .aisle("AAAAAAA", "B#####B", "V#####V", "#######", "#######", "#######", "#######", "VVVVVVV")
                    .aisle("AAAAAAA", "B#CCC#B", "V#CCC#V", "##CCC##", "##CCC##", "##CCC##", "##CCC##", "VVVVVVV")
                    .aisle("AAAAAAA", "B#CCC#B", "V#CDC#V", "##CDC##", "##CDC##", "##CDC##", "##CDC##", "VVVVVVV")
                    .aisle("AAAAAAA", "B#CCC#B", "V#CCC#V", "##CCC##", "##CCC##", "##CCC##", "##CCC##", "VVVVVVV")
                    .aisle("AAAAAAA", "B#####B", "V#####V", "#######", "#######", "#######", "#######", "VVVVVVV")
                    .aisle("AAASAAA", "VBBBBBV", "VVVVVVV", "B#####B", "B#####B", "B#####B", "B#####B", "VVVVVVV")
                    .where("S", controller(blocks(definition.get())))
                    .where("V", blocks(GTNNCasingBlocks.RADIATION_PROOF_MACHINE_CASING.get())).where(
                        "A", blocks(GTNNCasingBlocks.RADIATION_PROOF_MACHINE_CASING.get()).or(
                            autoAbilities(
                                true, false, false
                            )
                        ).or(abilities(PartAbility.OUTPUT_ENERGY).setMinGlobalLimited(1, 1))
                            .or(abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                            .or(abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                    ).where("B", blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTNNMaterials.RadiationProtection)))
                    .where("C", blocks(GTNNCasingBlocks.MAR_CASING.get()))
                    .where("D", blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get())).where("#", air()).build()
            }.workableCasingRenderer(
                GTNN.id("block/casings/solid/radiation_proof_machine_casing"),
                GTNN.id("block/multiblock/large_naquadah_reactor"),
                false
            ).additionalDisplay { controller, components ->
                if (controller is LargeNaquadahReactorMachine && controller.isFormed()) run {
                    components.add(
                        Component.translatable(
                            "gtnn.multiblock.large_naquadah_reactor.power", controller.getFinalPowerRate()
                        )
                    )
                }
            }.register()

    fun init() {
        REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.MAIN_TAB }
        ModifyMachines.init()
    }
}