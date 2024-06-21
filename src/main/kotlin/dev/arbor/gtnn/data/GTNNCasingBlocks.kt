package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.block.RendererBlock
import com.gregtechceu.gtceu.api.item.RendererBlockItem
import com.gregtechceu.gtceu.client.renderer.block.TextureOverrideRenderer
import com.gregtechceu.gtceu.data.recipe.CustomTags
import com.lowdragmc.lowdraglib.Platform
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.nullness.NonNullBiConsumer
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.api.tool.StringTools.nn
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour

object GTNNCasingBlocks {

    val PROCESS_MACHINE_CASING: BlockEntry<Block> = createCasingBlock(
        "clean_machine_casing",
        "Clean Machine Casing", "block/casings/solid/process_machine_casing".nn()
    )

    val RADIATION_PROOF_MACHINE_CASING: BlockEntry<Block> = createCasingBlock(
        "radiation_proof_machine_casing",
        "Radiation Proof Machine Casing", "block/casings/solid/radiation_proof_machine_casing".nn()
    )

    val MAR_CASING: BlockEntry<Block> = createCasingBlock(
        "mar_casing",
        "Field Restriction Casing", "block/casings/solid/mar_casing".nn()
    )

    private fun createCasingBlock(
        name: String,
        displayName: String,
        texture: ResourceLocation,
    ): BlockEntry<Block> {
        return REGISTRATE.block<Block>(name) { p: BlockBehaviour.Properties ->
            RendererBlock(
                p,
                if (Platform.isClient())
                    TextureOverrideRenderer(
                        ResourceLocation.withDefaultNamespace("block/cube_all"),
                        mapOf("all" to texture)
                    )
                else null
            )
        }
            .initialProperties { Blocks.IRON_BLOCK }
            .lang(displayName)
            .blockstate(NonNullBiConsumer.noop<DataGenContext<Block, Block>, RegistrateBlockstateProvider>())
            .tag(CustomTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
            .item { block: Block?, prop: Item.Properties? -> RendererBlockItem(block, prop) }
            .model(NonNullBiConsumer.noop())
            .build()
            .register()
    }

    fun init() {
        REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.MAIN_TAB }
    }
}
