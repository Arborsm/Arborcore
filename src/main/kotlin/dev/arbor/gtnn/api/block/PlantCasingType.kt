package dev.arbor.gtnn.api.block

import com.tterrag.registrate.util.entry.BlockEntry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block

interface PlantCasingType {
    /**
     * @return the Tier of the Plant Casing
     */
    fun getTier(): Int

    /**
     * @param tier
     * @return Block of the Plant Casing
     */
    fun getPlantCasing(tier: Int): BlockEntry<Block>

    /**
     * @return the Name of the Plant Casing
     */
    fun getName(): String

    /**
     * @return Block of the Plant Casing
     */
    fun getPlantCasing(): BlockEntry<Block>

    /**
     * @return the ResourceLocation of the Plant Casing
     */
    fun getResourceLocation(): ResourceLocation
}
