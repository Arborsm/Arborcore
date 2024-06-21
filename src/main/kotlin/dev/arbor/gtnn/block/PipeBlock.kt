package dev.arbor.gtnn.block

import com.gregtechceu.gtceu.data.block.GTBlocks.*
import com.tterrag.registrate.util.entry.BlockEntry
import dev.arbor.gtnn.api.block.ITier
import dev.arbor.gtnn.api.block.PipeType
import dev.arbor.gtnn.block.BlockTier.*
import net.minecraft.world.level.block.Block

class PipeBlock(properties: Properties) : Block(properties) {
    enum class Pipe(val tier: ITier, private val casingBlock: BlockEntry<Block>) : PipeType {
        BRONZE(TIER0, CASING_BRONZE_PIPE),
        STEEL(TIER1, CASING_STEEL_PIPE),
        TITANIUM(TIER2, CASING_TITANIUM_PIPE),
        TUNGSTENSTEEL(TIER3, CASING_TUNGSTENSTEEL_PIPE),
        CHROME(TIER4, CASING_TUNGSTENSTEEL_PIPE),
        IRIDIUM(TIER5, CASING_TUNGSTENSTEEL_PIPE),
        OSMIUM(TIER6, CASING_TUNGSTENSTEEL_PIPE),
        NEUTRONIUM(TIER7, CASING_TUNGSTENSTEEL_PIPE);

        fun pipeType(): PipeType {
            return this
        }

        override fun getPipeTier(): Int {
            return tier.tier()
        }

        override fun getPipe(): BlockEntry<Block> {
            return casingBlock
        }

        fun getPipe(tier: Int): BlockEntry<Block> {
            return entries[tier].getPipe()
        }
    }
}
