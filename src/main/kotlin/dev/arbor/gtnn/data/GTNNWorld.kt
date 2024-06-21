package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.worldgen.IWorldGenLayer
import com.gregtechceu.gtceu.api.worldgen.WorldGeneratorUtils
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.util.StringRepresentable
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest

object GTNNWorld {
    val TWILIGHT_FOREST: ResourceLocation = ResourceLocation.fromNamespaceAndPath("twilightforest", "twilight_forest")
    val MOON: ResourceLocation = ResourceLocation.fromNamespaceAndPath("ad_astra", "moon")
    val VENUS: ResourceLocation = ResourceLocation.fromNamespaceAndPath("ad_astra", "venus")
    val MARS: ResourceLocation = ResourceLocation.fromNamespaceAndPath("ad_astra", "mars")
    val MERCURY: ResourceLocation = ResourceLocation.fromNamespaceAndPath("ad_astra", "mercury")
    val GLACIO: ResourceLocation = ResourceLocation.fromNamespaceAndPath("ad_astra", "glacio")

    enum class GTNNWorldGenLayers(
        private val id: String, private val target: RuleTest, private val levels: Set<ResourceLocation>
    ) : IWorldGenLayer, StringRepresentable {
        AD("ad", TagMatchTest(GTNNTags.AD_ASTRA_STONES), setOf(MOON, MARS, MERCURY, VENUS, GLACIO)),
        TF("tf", TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), setOf(TWILIGHT_FOREST));

        init {
            WorldGeneratorUtils.WORLD_GEN_LAYERS[id] = this
        }

        override fun getLevels(): Set<ResourceLocation> {
            return levels
        }

        override fun getTarget(): RuleTest {
            return target
        }

        override fun isApplicableForLevel(level: ResourceLocation): Boolean {
            return levels.contains(level)
        }

        override fun getSerializedName(): String {
            return id
        }

        companion object {
            fun init() {
            }
        }
    }
}
