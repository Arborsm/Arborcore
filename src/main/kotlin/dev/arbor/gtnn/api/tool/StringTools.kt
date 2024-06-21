package dev.arbor.gtnn.api.tool

import dev.arbor.gtnn.GTNN
import net.minecraft.resources.ResourceLocation

object StringTools {
    fun String.rl(): ResourceLocation {
        return ResourceLocation.withDefaultNamespace(this)
    }

    fun String.rl(namespace: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(namespace, this)
    }

    fun String.gt(): ResourceLocation {
        return this.rl("gtceu")
    }

    fun String.nn(): ResourceLocation {
        return this.rl(GTNN.MODID)
    }
}