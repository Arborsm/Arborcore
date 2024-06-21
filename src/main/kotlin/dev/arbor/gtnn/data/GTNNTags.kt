package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.tag.TagUtil
import com.tterrag.registrate.providers.RegistrateTagsProvider
import dev.arbor.gtnn.GTNNIntegration.isAdAstraLoaded
import dev.arbor.gtnn.data.tags.AdAstraTag.init
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

object GTNNTags {
    fun initBlock(provider: RegistrateTagsProvider<Block?>) {
        if (isAdAstraLoaded()) {
            init(provider)
        }
    }

    val AD_ASTRA_STONES: TagKey<Block?> = TagUtil.createBlockTag("ad_astra_stones")
}
