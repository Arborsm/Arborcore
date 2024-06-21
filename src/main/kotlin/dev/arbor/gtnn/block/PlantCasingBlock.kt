package dev.arbor.gtnn.block

import com.gregtechceu.gtceu.data.block.GTBlocks
import com.tterrag.registrate.util.entry.BlockEntry
import dev.arbor.gtnn.api.block.ITier
import dev.arbor.gtnn.api.block.PlantCasingType
import dev.arbor.gtnn.api.tool.StringTools.gt
import dev.arbor.gtnn.block.BlockTier.*
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block

enum class PlantCasingBlock(
    private val tier: ITier,
    private val blockName: String,
    private val plantCasing: BlockEntry<Block>,
    private val texture: ResourceLocation
) : PlantCasingType {
    BRONZE(
        TIER0,
        "Bronze",
        GTBlocks.CASING_BRONZE_BRICKS,
        "dev/arbor/gtnn/block/casings/solid/machine_casing_bronze_plated_bricks".gt()
    ),
    STEEL(
        TIER1,
        "Steel",
        GTBlocks.CASING_STEEL_SOLID,
        "dev/arbor/gtnn/block/casings/solid/machine_casing_solid_steel".gt()
    ),
    ALUMINIUM(
        TIER2,
        "Aluminium",
        GTBlocks.CASING_ALUMINIUM_FROSTPROOF,
        "dev/arbor/gtnn/block/casings/solid/machine_casing_frost_proof".gt()
    ),
    STAINLESS(
        TIER3,
        "Stainless",
        GTBlocks.CASING_STAINLESS_CLEAN,
        "dev/arbor/gtnn/block/casings/solid/machine_casing_clean_stainless_steel".gt()
    ),
    TITANIUM(
        TIER4,
        "Titanium",
        GTBlocks.CASING_TITANIUM_STABLE,
        "dev/arbor/gtnn/block/casings/solid/machine_casing_stable_titanium".gt()
    ),
    TUNGSTENSTEEL(
        TIER5,
        "Tungstensteel",
        GTBlocks.CASING_TUNGSTENSTEEL_ROBUST,
        "dev/arbor/gtnn/block/casings/solid/machine_casing_robust_tungstensteel".gt()
    ),
    CHROME(
        TIER6,
        "Chrome",
        GTBlocks.CASING_TUNGSTENSTEEL_ROBUST,
        "dev/arbor/gtnn/block/casings/solid/machine_casing_robust_tungstensteel".gt()
    ),
    IRIDIUM(
        TIER7,
        "Iridium",
        GTBlocks.CASING_TUNGSTENSTEEL_ROBUST,
        "dev/arbor/gtnn/block/casings/solid/machine_casing_robust_tungstensteel".gt()
    );

    internal object PlantCasing {
        val All_PlantCasingBlock: MutableMap<String?, PlantCasingBlock> = Object2ObjectOpenHashMap()
        val All_PlantCasing_Tier: MutableMap<Int, PlantCasingBlock> = Object2ObjectOpenHashMap()
    }

    companion object {
        fun getByTier(tier: Int): PlantCasingBlock {
            return PlantCasing.All_PlantCasing_Tier[tier] ?: BRONZE
        }

        fun getByName(name: String?): PlantCasingBlock {
            return getByNameOrDefault(name)
        }

        fun getByNameOrDefault(name: String?): PlantCasingBlock {
            return PlantCasing.All_PlantCasingBlock[name] ?: return BRONZE
        }
    }

    init {
        PlantCasing.All_PlantCasingBlock[name] = this
        PlantCasing.All_PlantCasing_Tier[tier.tier()] = this
    }

    fun plantCasingType(): PlantCasingType {
        return this
    }

    override fun getName(): String {
        return blockName
    }

    override fun getTier(): Int {
        return tier.tier()
    }

    override fun getPlantCasing(tier: Int): BlockEntry<Block> {
        return getByTier(tier).getPlantCasing()
    }

    override fun getPlantCasing(): BlockEntry<Block> {
        return plantCasing
    }

    override fun getResourceLocation(): ResourceLocation {
        return texture
    }
}
