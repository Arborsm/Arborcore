package dev.arbor.gtnn.data.materials

import com.gregtechceu.gtceu.api.material.material.info.MaterialFlags.*
import com.gregtechceu.gtceu.api.material.material.info.MaterialIconSet.METALLIC
import com.gregtechceu.gtceu.data.material.GTMaterials.*
import dev.arbor.gtnn.data.GTNNElement
import dev.arbor.gtnn.data.GTNNMaterials.*

object AdAstraMaterials {
    fun init() {
        addOre(Perlite)
        addOre(Uvarovite)
        addOre(Andradite)
        addOre(Arsenic)
        addOre(Bismuth)
        addOre(Antimony)
        addOre(Uranium235)
        addOre(Uranium238)
        addOre(Plutonium241)
        addOre(Gallium)
        addOre(Niobium)
        addOre(Vanadium)
        addOre(Osmium)
        addOre(Iridium)
        addOre(Titanium)
        addOre(Manganese)
        addOre(Rutile)
        addOre(Tungsten)
        addOre(Chromium)
        Desh =
            Builder("desh").ingot().fluid().ore().color(0xF2A057).secondaryColor(0x2E2F04).element(GTNNElement.Ds)
                .iconSet(METALLIC).appendFlags(
                    EXT2_METAL, GENERATE_ROTOR, GENERATE_DENSE, GENERATE_SMALL_GEAR
                ).buildAndRegister()
        Ostrum =
            Builder("ostrum").ingot().fluid().ore().color(0xE5939B).secondaryColor(0x2F0425).element(GTNNElement.Ot)
                .iconSet(METALLIC).appendFlags(
                    EXT2_METAL, GENERATE_ROTOR, GENERATE_DENSE, GENERATE_SMALL_GEAR
                ).buildAndRegister()
        Calorite =
            Builder("calorite").ingot().fluid().ore().color(0xE65757).secondaryColor(0x2F0506).element(GTNNElement.Ct)
                .iconSet(METALLIC).appendFlags(
                    EXT2_METAL, GENERATE_ROTOR, GENERATE_DENSE, GENERATE_SMALL_GEAR
                ).buildAndRegister()
    }
}
