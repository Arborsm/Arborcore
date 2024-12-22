package dev.arbor.gtnn.data;

import com.gregtechceu.gtceu.api.cover.CoverDefinition
import com.gregtechceu.gtceu.client.renderer.cover.SimpleCoverRenderer
import com.gregtechceu.gtceu.common.data.GTCovers
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.temp.EnderFluidLinkCover
import dev.arbor.gtnn.temp.EnderItemLinkCover


object GTNNCovers {
    val ENDER_FLUID_LINK: CoverDefinition = GTCovers.register(
        "ender_fluid_link",
        ::EnderFluidLinkCover,
        SimpleCoverRenderer(GTNN.id("block/cover/overlay_ender_fluid_link"))
    )

    val ENDER_ITEM_LINK: CoverDefinition = GTCovers.register(
        "ender_item_link",
        ::EnderItemLinkCover,
        SimpleCoverRenderer(GTNN.id("block/cover/overlay_ender_item_link"))
    )

    fun init() {
    }
}
