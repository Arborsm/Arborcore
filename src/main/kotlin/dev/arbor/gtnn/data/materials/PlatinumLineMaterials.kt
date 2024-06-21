package dev.arbor.gtnn.data.materials

import com.gregtechceu.gtceu.api.material.material.info.MaterialFlags.DISABLE_DECOMPOSITION
import com.gregtechceu.gtceu.api.material.material.info.MaterialIconSet.*
import com.gregtechceu.gtceu.data.material.GTMaterials
import dev.arbor.gtnn.data.GTNNMaterials.*

object PlatinumLineMaterials {
    fun init() {
        addFluid(GTMaterials.RutheniumTetroxide)
        addFluid(GTMaterials.OsmiumTetroxide)
        addFluid(GTMaterials.CalciumChloride)

        PlatinumSalt =
            Builder("platinum_salt").dust().color(0xEEF2AE).iconSet(SAND).flags(DISABLE_DECOMPOSITION)
                .buildAndRegister()

        RefinedPlatinumSalt =
            Builder("refined_platinum_salt").dust().color(0xFFFEC2).iconSet(SAND)
                .flags(DISABLE_DECOMPOSITION).buildAndRegister()

        PalladiumSalt =
            Builder("palladium_salt").dust().color(0x33302D).iconSet(SAND).flags(DISABLE_DECOMPOSITION)
                .buildAndRegister()

        RhodiumNitrate =
            Builder("rhodium_nitrate").dust().color(0x8C5A0C).iconSet(SAND).flags(DISABLE_DECOMPOSITION)
                .buildAndRegister()

        RoughlyRhodiumMetal =
            Builder("roughly_rhodium_metal").dust().color(0x594C1A).iconSet(SAND)
                .flags(DISABLE_DECOMPOSITION).buildAndRegister().setFormula("??Rh??")

        PalladiumMetal =
            Builder("palladium_metal").dust().color(0x30302E).iconSet(SAND).flags(DISABLE_DECOMPOSITION)
                .buildAndRegister().setFormula("??Pd??")

        MetalSludge =
            Builder("metal_sludge").dust().color(0x362605).iconSet(SAND).buildAndRegister()
                .setFormula("NiCu")

        PlatinumSlag =
            Builder("platinum_slag").dust().color(0x343318).iconSet(SAND).flags(DISABLE_DECOMPOSITION)
                .buildAndRegister().setFormula("??IrOsRhRb??")

        ReprecipitatedRhodium =
            Builder("reprecipitated_rhodium").dust().color(0xD40849).iconSet(SAND)
                .flags(DISABLE_DECOMPOSITION).buildAndRegister().setFormula("Rh2NH4")

        SodiumNitrate =
            Builder("sodium_nitrate").dust().color(0x4e2a40).iconSet(SAND).flags(DISABLE_DECOMPOSITION)
                .buildAndRegister().setFormula("NaNO3")

        RhodiumSalt = Builder("rhodium_salt").dust().fluid().color(0x61200A).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION).buildAndRegister()

        RhodiumFilterCake =
            Builder("rhodium_filter_cake").dust().fluid().color(0x87350C).iconSet(ROUGH)
                .flags(DISABLE_DECOMPOSITION).buildAndRegister().setFormula("?Ru?")

        PlatinumMetal =
            Builder("platinum_metal").dust().color(0xEBEBB2).iconSet(ROUGH).flags(DISABLE_DECOMPOSITION)
                .buildAndRegister().setFormula("??PtPdIrOsRhRu??")

        SodiumRutheniate =
            Builder("sodium_rutheniate").dust().color(0x282C8C).iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION).buildAndRegister().setFormula("Na2RbO3")

        IridiumDioxide = Builder("iridium_dioxide").dust().color(0xA2BFFF).iconSet(METALLIC)
            .flags(DISABLE_DECOMPOSITION).buildAndRegister().setFormula("IrO2")

        ConcentratedPlatinum =
            Builder("concentrated_platinum").fluid().color(0xC3C39A).iconSet(ROUGH).buildAndRegister()

        PalladiumRichAmmonia =
            Builder("palladium_rich_ammonia").fluid().color(0x676767).iconSet(ROUGH).buildAndRegister()

        RutheniumTetroxideLQ =
            Builder("ruthenium_tetroxide_lq").fluid().color(0xA8A8A8).iconSet(ROUGH).buildAndRegister()

        SodiumFormate =
            Builder("sodium_formate").fluid().color(0xf1939c).iconSet(ROUGH).buildAndRegister()

        FormicAcid =
            Builder("formic_acid").fluid().color(0xf8c387).iconSet(ROUGH).buildAndRegister()

        RhodiumSulfateGas =
            Builder("rhodium_sulfate_gas").gas().color(0xBD8743).iconSet(ROUGH).buildAndRegister()

        AcidicIridium =
            Builder("acidic_iridium").gas().color(0x634E3A).iconSet(ROUGH).buildAndRegister()
                .setFormula("???")

        RutheniumTetroxideHot =
            Builder("ruthenium_tetroxide_hot").gas().color(0x9B9B9B).iconSet(ROUGH).buildAndRegister()
    }
}
