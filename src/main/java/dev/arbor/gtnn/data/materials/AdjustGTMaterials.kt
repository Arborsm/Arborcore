package dev.arbor.gtnn.data.materials

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty
import com.gregtechceu.gtceu.api.data.chemical.material.properties.OreProperty
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey
import com.gregtechceu.gtceu.common.data.GTMaterials.*
import dev.arbor.gtnn.GTNN.getServerConfig
import dev.arbor.gtnn.api.item.properties.CatalystProperty
import dev.arbor.gtnn.data.GTNNMaterials.*
import dev.arbor.gtnn.data.GTNNPropertyKeys.CATALYST

object AdjustGTMaterials {
    fun init() {
        adjustOres()
        Neutronium.setProperty(
            PropertyKey.BLAST,
            BlastProperty(9000, BlastProperty.GasTier.HIGHEST, 491250, 144 * 20, -1, -1)
        )
        NaquadahEnriched.addFlags(GENERATE_BOLT_SCREW)
        Europium.addFlags(GENERATE_BOLT_SCREW)
        Brass.addFlags(GENERATE_DENSE)
        Aluminium.addFlags(GENERATE_DENSE)
        Steel.addFlags(GENERATE_DENSE)
        Lanthanum.addFlags(GENERATE_DENSE)
        Iridium.addFlags(GENERATE_DENSE)
        Lead.addFlags(GENERATE_DENSE)
        // IronMagnetic.addFlags(GENERATE_PLATE)
        SteelMagnetic.addFlags(GENERATE_PLATE)
        NeodymiumMagnetic.addFlags(GENERATE_PLATE)
        SamariumMagnetic.addFlags(GENERATE_PLATE)
        NaquadahEnriched.addFlags(GENERATE_LONG_ROD)
        NickelZincFerrite.addFlags(GENERATE_LONG_ROD)
        BlueAlloy.addFlags(GENERATE_FRAME)
        Nichrome.addFlags(GENERATE_GEAR)
        Zeron100.addFlags(GENERATE_GEAR)
        Aluminium.addFlags(GENERATE_ROTOR)
        // EnrichedNaquadahTriniumEuropiumDuranide.setProperty(PropertyKey.WIRE, WireProperties(524288, 3, 2, false))
        addGas(Oganesson)
        addGas(Calcium)
        addFluid(Californium)
        addFluid(Caesium)
        addFluid(AmmoniumChloride)
        addDust(Praseodymium)

        OrangeMetal.setProperty(CATALYST, CatalystProperty(100))
    }

    private fun adjustOres() {
        if (getServerConfig().enableHarderPlatinumLine) {
            var oreProp: OreProperty = Nickel.getProperty(PropertyKey.ORE)
            oreProp.oreByProducts.clear()
            oreProp.setOreByProducts(Cobalt, Iron, PlatinumMetal)

            oreProp = Cooperite.getProperty(PropertyKey.ORE)
            oreProp.oreByProducts.clear()
            oreProp.setOreByProducts(Nickel, Nickel, Cobalt, PalladiumMetal)

            oreProp = Platinum.getProperty(PropertyKey.ORE)
            oreProp.oreByProducts.clear()
            oreProp.setOreByProducts(Nickel, Nickel, Cobalt, PalladiumMetal)
            oreProp.directSmeltResult = PlatinumMetal
        }
        if (getServerConfig().enableHarderNaquadahLine) {
            val oreProp = Naquadah.getProperty(PropertyKey.ORE)
            oreProp.oreByProducts.clear()
            oreProp.setOreByProducts(Sulfur, Barite, EnrichedNaquadahOxideMixture)
            oreProp.separatedInto.clear()
            oreProp.setSeparatedInto(EnrichedNaquadahOxideMixture)
        }
    }
}
