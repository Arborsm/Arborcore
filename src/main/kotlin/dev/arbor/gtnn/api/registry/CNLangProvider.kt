package dev.arbor.gtnn.api.registry

import com.gregtechceu.gtceu.api.material.material.Material
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.RegistrateProvider
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.data.GTNNDataGen
import net.minecraft.data.CachedOutput
import net.minecraft.data.PackOutput
import net.neoforged.fml.LogicalSide
import net.neoforged.neoforge.common.data.LanguageProvider
import java.util.concurrent.CompletableFuture

class CNLangProvider(owner: AbstractRegistrate<*>, packOutput: PackOutput?) :
    LanguageProvider(packOutput!!, owner.modid, "zh_cn"), RegistrateProvider {
    private var owner: AbstractRegistrate<*>? = owner

    override fun getSide(): LogicalSide {
        return LogicalSide.CLIENT
    }

    override fun getName(): String {
        return "Lang (zh_cn)"
    }

    override fun addTranslations() {
        owner!!.genData(GTNNDataGen.CN_LANG, this)
    }

    override fun run(cache: CachedOutput): CompletableFuture<*> {
        return CompletableFuture.allOf(super.run(cache))
    }

    fun translateMaterial(material: Material, name: String?) {
        try {
            name?.let { add("material.gtceu.${material.name}", it) }
        } catch (e: NullPointerException) {
            GTNN.LOGGER.error("Failed to translate material(CN)", e)
        }
    }
}
