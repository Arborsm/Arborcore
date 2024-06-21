package dev.arbor.gtnn.api.block

import com.tterrag.registrate.util.entry.BlockEntry
import net.minecraft.world.level.block.Block

interface MachineCasingType {
    /**
     * @return the Tier of the Machine Casing
     */
    fun getMachineCasingTier(): Int

    /**
     * @return the Machine Casing Block
     */
    fun getMachineCasing(): BlockEntry<Block>
}
