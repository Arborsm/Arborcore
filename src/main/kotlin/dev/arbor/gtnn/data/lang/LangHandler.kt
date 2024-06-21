package dev.arbor.gtnn.data.lang

import com.gregtechceu.gtceu.api.material.material.Material
import com.gregtechceu.gtceu.utils.FormattingUtil
import com.tterrag.registrate.providers.RegistrateLangProvider
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.api.registry.CNLangProvider

class LangHandler {
    companion object {
        val INSTANCE: LangHandler = LangHandler()
        fun tsl(key: String?, cn: String?, en: String?) {
            INSTANCE.translate(key, cn, en)
        }

        fun tsl(key: String?, cn: String?) {
            INSTANCE.translate(key, cn)
        }

        fun translateOreVein(key: String?, cn: String?) {
            tsl(key, cn, FormattingUtil.toEnglishName(key))
        }

        fun translateMaterial(material: Material, cn: String?) {
            translateMaterial(material, cn, null)
        }

        private fun translateMaterial(material: Material, cn: String?, en: String?) {
            if (en == null) {
                if (INSTANCE.enLangProvider != null && INSTANCE.cnLangProvider == null) translateMaterial(
                    INSTANCE.enLangProvider, material
                )
            } else {
                try {
                    INSTANCE.enLangProvider!!.add("material.gtceu." + material.name, en)
                } catch (e: NullPointerException) {
                    GTNN.LOGGER.error("Failed to translate material(EN)", e)
                }
            }
            if (INSTANCE.cnLangProvider != null) INSTANCE.cnLangProvider!!.translateMaterial(material, cn)
        }

        private fun translateMaterial(provider: RegistrateLangProvider?, material: Material) {
            try {
                provider!!.add("material.gtceu." + material.name, FormattingUtil.toEnglishName(material.name))
            } catch (e: NullPointerException) {
                GTNN.LOGGER.error("Failed to translate material(EN)", e)
            }
        }

        fun cnInitialize(provider: CNLangProvider) {
            INSTANCE.cnLangProvider = provider
            init()
        }

        fun enInitialize(provider: RegistrateLangProvider) {
            INSTANCE.enLangProvider = provider
            init()
        }

        fun init() {
            MachineLang.init()
            MaterialLang.init()
            MiscLang.init()
        }
    }

    private var cnLangProvider: CNLangProvider? = null
    private var enLangProvider: RegistrateLangProvider? = null

    private fun translate(key: String?, cn: String?, en: String?) {
        if (enLangProvider != null && cnLangProvider == null) {
            key?.let { en?.let { it1 -> enLangProvider!!.add(it, it1) } }
        }
        if (cnLangProvider != null) key?.let { cn?.let { it1 -> cnLangProvider!!.add(it, it1) } }
    }

    private fun translate(key: String?, cn: String?) {
        if (cnLangProvider != null) key?.let { cn?.let { it1 -> cnLangProvider!!.add(it, it1) } }
    }
}
