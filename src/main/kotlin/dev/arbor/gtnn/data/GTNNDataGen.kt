package dev.arbor.gtnn.data

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateLangProvider
import com.tterrag.registrate.providers.RegistrateTagsProvider.IntrinsicImpl
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.api.registry.CNLangProvider
import dev.arbor.gtnn.data.lang.LangHandler
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.data.event.GatherDataEvent

object GTNNDataGen {
    val CN_LANG: ProviderType<CNLangProvider> = ProviderType.register(
        "cn_lang"
    ) { p: AbstractRegistrate<*>?, e: GatherDataEvent ->
        CNLangProvider(
            p!!, e.generator.packOutput
        )
    }

    fun init() {
        REGISTRATE.addDataGenerator(
            ProviderType.LANG
        ) { provider: RegistrateLangProvider ->
            LangHandler.enInitialize(
                provider
            )
        }
        REGISTRATE.addDataGenerator(
            CN_LANG
        ) { provider: CNLangProvider ->
            LangHandler.cnInitialize(
                provider
            )
        }
        REGISTRATE.addDataGenerator<IntrinsicImpl<Block>>(
            ProviderType.BLOCK_TAGS
        ) { provider: IntrinsicImpl<Block> ->
            GTNNTags.initBlock(
                provider
            )
        }
    }

}
