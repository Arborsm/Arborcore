package dev.arbor.gtnn.block

import dev.arbor.gtnn.api.block.ITier

enum class BlockTier(private var tier: Int) : ITier {
    TIER0(0),
    TIER1(1),
    TIER2(2),
    TIER3(3),
    TIER4(4),
    TIER5(5),
    TIER6(6),
    TIER7(7);

    override fun tier(): Int {
        return tier
    }
}
