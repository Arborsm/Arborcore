package dev.arbor.gtnn.worldgen

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.registry.GTRegistries

object GTOreVein {
    private val oreVeinsRemoved = setOf(
        GTCEu.id("banded_iron_vein"),
        GTCEu.id("manganese_vein"),
        GTCEu.id("monazite_vein"),
        GTCEu.id("redstone_vein"),
        GTCEu.id("topaz_vein"),
        GTCEu.id("bauxite_vein_end"),
        GTCEu.id("magnetite_vein_end"),
        GTCEu.id("naquadah_vein"),
        GTCEu.id("pitchblende_vein_end"),
        GTCEu.id("scheelite_vein"),
        GTCEu.id("sheldonite_vein"),
    )

    fun oreRemove() {
        for (id in oreVeinsRemoved) {
            GTRegistries.ORE_VEINS.remove(id)
        }
    }
}
