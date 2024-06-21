package dev.arbor.gtnn

import com.lowdragmc.lowdraglib.LDLib

object GTNNIntegration {
    fun isAdAstraLoaded(): Boolean {
        return isLoaded("ad_astra")
    }

    fun isTwilightForestLoaded(): Boolean {
        return isLoaded("twilightforest")
    }

    fun isBotaniaLoaded(): Boolean {
        return isLoaded("botania")
    }

    fun isForbiddenArcanusLoaded(): Boolean {
        return isLoaded("forbidden_arcanus")
    }

    fun isCreateLoaded(): Boolean {
        return isLoaded("create")
    }

    fun isGreateLoaded(): Boolean {
        return isLoaded("greate")
    }

    fun isSupplementariesLoaded(): Boolean {
        return isLoaded("supplementaries")
    }

    fun isCCTweakedLoaded(): Boolean {
        return isLoaded("computercraft")
    }

    private fun isLoaded(mod: String): Boolean {
        return LDLib.isModLoaded(mod)
    }

}