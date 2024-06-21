package dev.arbor.gtnn.api.block

import com.tterrag.registrate.util.entry.BlockEntry
import net.minecraft.world.level.block.Block

interface PipeType {
    /**
     * @return the Tier of the Pipe
     */
    fun getPipeTier(): Int

    fun getPipe(): BlockEntry<Block>
}
