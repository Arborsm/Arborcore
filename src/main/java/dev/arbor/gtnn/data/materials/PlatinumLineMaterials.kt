package dev.arbor.gtnn.data.materials

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*
import com.gregtechceu.gtceu.common.data.GTMaterials
import dev.arbor.gtnn.data.GTNNMaterials

object PlatinumLineMaterials {
    fun init() {
        GTNNMaterials.addFluid(GTMaterials.RutheniumTetroxide)
        GTNNMaterials.addFluid(GTMaterials.OsmiumTetroxide)
        GTNNMaterials.addFluid(GTMaterials.CalciumChloride)
        GTNNMaterials.PlatinumSalt = GTNNMaterials.builder("platinum_salt")
            .dust()
            .color(0xEEF2AE).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister()
        GTNNMaterials.RefinedPlatinumSalt = GTNNMaterials.builder("refined_platinum_salt")
            .dust()
            .color(0xFFFEC2).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister()
        GTNNMaterials.PalladiumSalt = GTNNMaterials.builder("palladium_salt")
            .dust()
            .color(0x33302D).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister()
        GTNNMaterials.RhodiumNitrate = GTNNMaterials.builder("rhodium_nitrate")
            .dust()
            .color(0x8C5A0C).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister()
        GTNNMaterials.RoughlyRhodiumMetal = GTNNMaterials.builder("roughly_rhodium_metal")
            .dust()
            .color(0x594C1A).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister().setFormula("??Rh??")
        GTNNMaterials.PalladiumMetal = GTNNMaterials.builder("palladium_metal")
            .dust()
            .color(0x30302E).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister().setFormula("??Pd??")
        GTNNMaterials.MetalSludge = GTNNMaterials.builder("metal_sludge")
            .dust()
            .color(0x362605).iconSet(SAND)
            .buildAndRegister().setFormula("NiCu")
        GTNNMaterials.PlatinumSlag = GTNNMaterials.builder("platinum_slag")
            .dust()
            .color(0x343318).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister().setFormula("??IrOsRhRb??")
        GTNNMaterials.ReprecipitatedRhodium = GTNNMaterials.builder("reprecipitated_rhodium")
            .dust()
            .color(0xD40849).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister().setFormula("Rh2NH4")
        GTNNMaterials.SodiumNitrate = GTNNMaterials.builder("sodium_nitrate")
            .dust()
            .color(0x4e2a40).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister().setFormula("NaNO3")
        GTNNMaterials.RhodiumSalt = GTNNMaterials.builder("rhodium_salt")
            .dust().fluid()
            .color(0x61200A).iconSet(SAND)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister()
        GTNNMaterials.RhodiumFilterCake = GTNNMaterials.builder("rhodium_filter_cake")
            .dust().fluid()
            .color(0x87350C).iconSet(ROUGH)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister().setFormula("?Ru?")
        GTNNMaterials.PlatinumMetal = GTNNMaterials.builder("platinum_metal")
            .dust()
            .color(0xEBEBB2).iconSet(ROUGH)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister().setFormula("??PtPdIrOsRhRu??")
        GTNNMaterials.SodiumRutheniate = GTNNMaterials.builder("sodium_rutheniate")
            .dust()
            .color(0x282C8C).iconSet(METALLIC)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister().setFormula("Na2RbO3")
        GTNNMaterials.IridiumDioxide = GTNNMaterials.builder("iridium_dioxide")
            .dust()
            .color(0xA2BFFF).iconSet(METALLIC)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister().setFormula("IrO2")
        GTNNMaterials.ConcentratedPlatinum = GTNNMaterials.builder("concentrated_platinum")
            .fluid()
            .color(0xC3C39A).iconSet(ROUGH)
            .buildAndRegister()
        GTNNMaterials.PalladiumRichAmmonia = GTNNMaterials.builder("palladium_rich_ammonia")
            .fluid()
            .color(0x676767).iconSet(ROUGH)
            .buildAndRegister()
        GTNNMaterials.RutheniumTetroxideLQ = GTNNMaterials.builder("ruthenium_tetroxide_lq")
            .fluid()
            .color(0xA8A8A8).iconSet(ROUGH)
            .buildAndRegister()
        GTNNMaterials.SodiumFormate = GTNNMaterials.builder("sodium_formate")
            .fluid()
            .color(0xf1939c).iconSet(ROUGH)
            .buildAndRegister()
        GTNNMaterials.FormicAcid = GTNNMaterials.builder("formic_acid")
            .fluid()
            .color(0xf8c387).iconSet(ROUGH)
            .buildAndRegister()
        GTNNMaterials.RhodiumSulfateGas = GTNNMaterials.builder("rhodium_sulfate_gas")
            .gas()
            .color(0xBD8743).iconSet(ROUGH)
            .buildAndRegister()
        GTNNMaterials.AcidicIridium = GTNNMaterials.builder("acidic_iridium")
            .gas()
            .color(0x634E3A).iconSet(ROUGH)
            .buildAndRegister().setFormula("???")
        GTNNMaterials.RutheniumTetroxideHot = GTNNMaterials.builder("ruthenium_tetroxide_hot")
            .gas()
            .color(0x9B9B9B).iconSet(ROUGH)
            .buildAndRegister()
    }
}
