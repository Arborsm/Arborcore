package dev.arbor.gtnn.data.tags

import com.tterrag.registrate.providers.RegistrateTagsProvider
import dev.arbor.gtnn.data.GTNNTags
import earth.terrarium.adastra.common.registry.ModBlocks
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import java.util.*

object AdAstraTag {
    fun init(provider: RegistrateTagsProvider<Block?>) {
        create(
            provider,
            GTNNTags.AD_ASTRA_STONES,
            ModBlocks.MOON_STONE.get(),
            ModBlocks.MARS_STONE.get(),
            ModBlocks.MERCURY_STONE.get(),
            ModBlocks.VENUS_STONE.get(),
            ModBlocks.GLACIO_STONE.get(),
            Blocks.BLACKSTONE,
            Blocks.BASALT,
            Blocks.DEEPSLATE,
            Blocks.SOUL_SOIL
        )
    }

    fun create(provider: RegistrateTagsProvider<Block?>, tagKey: TagKey<Block?>?, vararg rls: Block?) {
        val builder = tagKey?.let { provider.addTag(it) }
        for (block in rls) {
            if (builder != null) {
                Objects.requireNonNull(block?.let { BuiltInRegistries.BLOCK.getKey(it) })
                    ?.let { builder.addOptional(it) }
            }
        }
    }
}
