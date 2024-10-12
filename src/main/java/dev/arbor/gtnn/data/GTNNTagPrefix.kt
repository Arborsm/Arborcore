package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconType
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import dev.arbor.gtnn.GTNNIntegration
import dev.arbor.gtnn.api.tool.StringTools.rl
import dev.arbor.gtnn.data.materials.GTNNChemicalItems
import earth.terrarium.adastra.common.registry.ModBlocks
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor

object GTNNTagPrefix {
    private val oreMoonStone: TagPrefix by lazy {
        TagPrefix.oreTagPrefix("moon_stone", BlockTags.MINEABLE_WITH_PICKAXE).langValue("MoonStone %s Ore").registerOre(
                { ModBlocks.MOON_STONE.get().defaultBlockState() },
                null,
                BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f),
                "block/moon_stone".ad()
            )
    }
    private val oreVenusStone: TagPrefix by lazy {
        TagPrefix.oreTagPrefix("venus_stone", BlockTags.MINEABLE_WITH_PICKAXE).langValue("VenusStone %s Ore")
            .registerOre(
                { ModBlocks.VENUS_STONE.get().defaultBlockState() },
                null,
                BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f),
                "block/venus_stone".ad()
            )
    }
    private val oreMarsStone: TagPrefix by lazy {
        TagPrefix.oreTagPrefix("mars_stone", BlockTags.MINEABLE_WITH_PICKAXE).langValue("MarsStone %s Ore").registerOre(
                { ModBlocks.MARS_STONE.get().defaultBlockState() },
                null,
                BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f),
                "block/mars_stone".ad()
            )
    }
    private val oreMercuryStone: TagPrefix by lazy {
        TagPrefix.oreTagPrefix("mercury_stone", BlockTags.MINEABLE_WITH_PICKAXE).langValue("MercuryStone %s Ore")
            .registerOre(
                { ModBlocks.MERCURY_STONE.get().defaultBlockState() },
                null,
                BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f),
                "block/mercury_stone".ad()
            )
    }
    private val oreGlacioStone: TagPrefix by lazy {
        TagPrefix.oreTagPrefix("glacio_stone", BlockTags.MINEABLE_WITH_PICKAXE).langValue("GlacioStone %s Ore")
            .registerOre(
                { ModBlocks.GLACIO_STONE.get().defaultBlockState() },
                null,
                BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f),
                "block/glacio_stone".ad()
            )
    }
    private val oreBlackStone: TagPrefix by lazy {
        TagPrefix.oreTagPrefix("blackstone", BlockTags.MINEABLE_WITH_PICKAXE).langValue("BlackStone %s Ore")
            .registerOre(
                { Blocks.BLACKSTONE.defaultBlockState() },
                null,
                BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f),
                "block/blackstone".rl()
            )
    }
    private val oreSoulSoil: TagPrefix by lazy {
        TagPrefix.oreTagPrefix("soul_soil", BlockTags.MINEABLE_WITH_SHOVEL).langValue("SoulSoil %s Ore").registerOre(
                { Blocks.SOUL_SOIL.defaultBlockState() },
                null,
                BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f),
                "block/soul_soil".rl()
            )
    }

    val catalyst: TagPrefix = TagPrefix("catalyst")
        .langValue("%s Catalyst")
        .defaultTagPath("catalyst/%s")
        .unformattedTagPath("catalyst")
        .itemTable { GTNNChemicalItems.CATALYST_ITEMS }
        .materialAmount(-1)
        .materialIconType(GTNNMaterialIconType.catalyst)
        .unificationEnabled(true)

    fun init() {
        if (GTNNIntegration.isAdAstraLoaded()) {
            oreMoonStone
            oreVenusStone
            oreMarsStone
            oreMercuryStone
            oreGlacioStone
            oreBlackStone
            oreSoulSoil
        }
    }

    private fun String.ad(): ResourceLocation {
        return ResourceLocation("ad_astra", this)
    }
}

object GTNNMaterialIconType{
    val catalyst: MaterialIconType = MaterialIconType("catalyst")

    fun init() {}
}
