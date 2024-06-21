package dev.arbor.gtnn.block

import com.gregtechceu.gtceu.data.block.GTBlocks.*
import com.tterrag.registrate.util.entry.BlockEntry
import dev.arbor.gtnn.api.block.ITier
import dev.arbor.gtnn.api.block.MachineCasingType
import dev.arbor.gtnn.block.BlockTier.*
import net.minecraft.world.level.block.Block


class MachineCasingBlock(properties: Properties) : Block(properties) {

    enum class MachineCasing(
        val tier: ITier,
        private val machineCasingBlock: BlockEntry<Block>
    ) : MachineCasingType {
        LV(TIER0, MACHINE_CASING_LV),
        MV(TIER1, MACHINE_CASING_MV),
        HV(TIER2, MACHINE_CASING_HV),
        EV(TIER3, MACHINE_CASING_EV),
        IV(TIER4, MACHINE_CASING_IV),
        LuV(TIER5, MACHINE_CASING_LuV),
        ZPM(TIER6, MACHINE_CASING_ZPM),
        UV(TIER7, MACHINE_CASING_UV);

        fun machineCasingType(): MachineCasingType {
            return this
        }

        override fun getMachineCasingTier(): Int {
            return tier.tier()
        }

        override fun getMachineCasing(): BlockEntry<Block> {
            return machineCasingBlock
        }

        fun getMachineCasing(tier: Int): BlockEntry<Block> {
            return MachineCasing.values()[tier].machineCasingBlock
        }
    }
}
