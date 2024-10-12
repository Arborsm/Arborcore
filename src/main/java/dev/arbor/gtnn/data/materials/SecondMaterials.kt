package dev.arbor.gtnn.data.materials

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet
import com.gregtechceu.gtceu.api.fluids.FluidBuilder
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys
import com.gregtechceu.gtceu.common.data.GTMaterials
import dev.arbor.gtnn.data.GTNNMaterials.*

object SecondMaterials {
    fun init() {
        ArcaneCrystal =
            Builder("arcane_crystal").dust().ore().gem().color(0x93AEFF).iconSet(MaterialIconSet.DIAMOND)
                .buildAndRegister()

        RP1 =
            Builder("rp_1_mixed_fuel").fluid().color(0xC02928).iconSet(MaterialIconSet.DULL)
                .buildAndRegister()

        RP1RocketFuel =
            Builder("rp_1_rocket_fuel").fluid().color(0x9E2A2A).iconSet(MaterialIconSet.DULL)
                .buildAndRegister()

        Kerosene =
            Builder("kerosene").fluid().color(0x752275).iconSet(MaterialIconSet.DULL).buildAndRegister()

        DenseHydrazineMixedFuel =
            Builder("dense_hydrazine_mixed_fuel").fluid().color(0x833D59).iconSet(MaterialIconSet.DULL)
                .buildAndRegister()

        Hydrazine =
            Builder("hydrazine").fluid().color(0xBBBBBB).iconSet(MaterialIconSet.DULL).buildAndRegister()

        HydrogenPeroxide =
            Builder("hydrogen_peroxide").fluid().color(0xC3EDED).iconSet(MaterialIconSet.DULL)
                .buildAndRegister()

        EthylAnthraQuinone =
            Builder("ethyl_anthra_quinone").fluid().color(0xAABE77).iconSet(MaterialIconSet.DULL)
                .buildAndRegister()

        EthylAnthraHydroQuinone =
            Builder("ethyl_anthra_hydro_quinone").fluid().color(0xC9E08D).iconSet(MaterialIconSet.DULL)
                .buildAndRegister()

        Anthracene =
            Builder("anthracene").fluid().color(0xBBBABA).iconSet(MaterialIconSet.DULL).buildAndRegister()

        MethylhydrazineNitrateRocketFuel =
            Builder("methylhydrazine_nitrate_rocket_fuel").fluid().color(0x613B87)
                .iconSet(MaterialIconSet.DULL).buildAndRegister()

        MethylHydrazine =
            Builder("methyl_hydrazine").fluid().color(0x606060).iconSet(MaterialIconSet.DULL)
                .buildAndRegister()

        UDMHRocketFuel =
            Builder("udmh_rocket_fuel").fluid().color(0x2AA327).iconSet(MaterialIconSet.DULL)
                .buildAndRegister()

        UDMH =
            Builder("udmh").fluid().color(0x050543).iconSet(MaterialIconSet.DULL).buildAndRegister()

        OrangeMetal =
            Builder("orange_metal").dust().color(0xfa7e23).iconSet(MaterialIconSet.ROUGH)
                .buildAndRegister()

        PhthalicAnhydride =
            Builder("phthalic_anhydride").dust().color(0x6C863A).iconSet(MaterialIconSet.ROUGH)
                .buildAndRegister()

        VanadiumPentoxide = Builder("vanadium_pentoxide").dust()
            .components(GTMaterials.Vanadium, 2, GTMaterials.Oxygen, 5).color(0xB5730F)
            .iconSet(MaterialIconSet.METALLIC).buildAndRegister()

        BlackMatter = Builder("black_matter").dust().ingot().fluid()
            .components(GTMaterials.Lead, 3, GTMaterials.Manganese, 5, GTMaterials.Carbon, 12).color(0x000000)
            .iconSet(MaterialIconSet.DULL).appendFlags(GTMaterials.EXT_METAL, MaterialFlags.GENERATE_FRAME)
            .buildAndRegister()

        Cerrobase140 = Builder("cerrobase_140").dust().fluid().components(
            GTMaterials.Bismuth, 47, GTMaterials.Lead, 25, GTMaterials.Tin, 13, GTMaterials.Cadmium, 10, GTMaterials.Indium, 5
        ).color(0x9e9e9e).iconSet(MaterialIconSet.METALLIC).blastTemp(1230).buildAndRegister()

        PotassiumPyrosulfate =
            Builder("potassium_pyrosulfate").dust().fluid(FluidStorageKeys.MOLTEN, FluidBuilder())
                .components(GTMaterials.Potassium, 2, GTMaterials.Sulfur, 2, GTMaterials.Oxygen, 7).color(0xff9900)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister()

        SodiumSulfate = Builder("sodium_sulfate").dust()
            .components(GTMaterials.Sodium, 2, GTMaterials.Sulfur, 1, GTMaterials.Oxygen, 4).color(0xF9F6CF)
            .iconSet(MaterialIconSet.SAND).buildAndRegister()

        ZincSulfate = Builder("zinc_sulfate").dust()
            .components(GTMaterials.Zinc, 1, GTMaterials.Sulfur, 1, GTMaterials.Oxygen, 4).color(0x533c1b)
            .iconSet(MaterialIconSet.SAND).buildAndRegister()

        Wollastonite = Builder("wollastonite").dust().ore()
            .components(GTMaterials.Calcium, 1, GTMaterials.Silicon, 1, GTMaterials.Oxygen, 3).color(0xc4cbcf)
            .iconSet(MaterialIconSet.SAND).buildAndRegister()

        Kaolinite =
            Builder("kaolinite").dust().ore().color(0x969090).iconSet(MaterialIconSet.ROUGH)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()

        Dolomite =
            Builder("dolomite").dust().ore().color(0x9F9191).iconSet(MaterialIconSet.ROUGH)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()

        GraphiteUraniumMixture = Builder("graphite_uranium_mixture").dust()
            .components(GTMaterials.Graphite, 3, GTMaterials.Uranium238, 1).color(0x2f734c)
            .iconSet(MaterialIconSet.METALLIC).buildAndRegister()

        PlutoniumOxideUraniumMixture =
            Builder("plutonium_oxide_uranium_mixture").dust().components(
                GTMaterials.Plutonium239, 10, GTMaterials.Oxygen, 12, GTMaterials.Uranium238, 2, GTMaterials.Carbon, 8
            ).color(0xc51d46).iconSet(MaterialIconSet.METALLIC).buildAndRegister()

        UraniumCarbideThoriumMixture =
            Builder("uranium_carbide_thorium_mixture").dust().components(
                GTMaterials.Thorium, 11, Thorium232, 1, GTMaterials.Uranium235, 1, GTMaterials.Carbon, 3
            ).color(0x15231b).iconSet(MaterialIconSet.METALLIC).buildAndRegister()

        ThoriumBasedLiquidFuel =
            Builder("thorium_based_liquid_fuel").fluid().color(0x3b264d).iconSet(MaterialIconSet.METALLIC)
                .buildAndRegister().setFormula("Th432Li4D2Hg")

        ThoriumBasedLiquidFuelExcited =
            Builder("thorium_based_liquid_fuel_excited").fluid().color(0x3f2850)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("*(Th432Li4D2HG)*")

        ThoriumBasedLiquidFuelDepleted =
            Builder("thorium_based_liquid_fuel_depleted").fluid().color(0x5d5166)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("Th?Pr?B?In?")

        UraniumBasedLiquidFuel =
            Builder("uranium_based_liquid_fuel").fluid().color(0x02ba05).iconSet(MaterialIconSet.METALLIC)
                .buildAndRegister().setFormula("U36K8Qt4Rn")

        UraniumBasedLiquidFuelExcited =
            Builder("uranium_based_liquid_fuel_excited").fluid().color(0x04bc04)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("*(U36K8Qt4Rn)*")

        UraniumBasedLiquidFuelDepleted =
            Builder("uranium_based_liquid_fuel_depleted").fluid().color(0x576d31)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("PB?Bi?Ba?Xe?")

        PlutoniumBasedLiquidFuel =
            Builder("plutonium_based_liquid_fuel").fluid().color(0xb71213)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("Pu45Nt8Cs16Nq2")

        PlutoniumBasedLiquidFuelExcited =
            Builder("plutonium_based_liquid_fuel_excited").fluid().color(0xb81312)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("*(Pu45Nt8Cs16Nq2)*")

        PlutoniumBasedLiquidFuelDepleted =
            Builder("plutonium_based_liquid_fuel_depleted").fluid().color(0x4e1414)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("Th?Ce?Au?Kr?")

        RadiationProtection =
            Builder("radiation_protection").dust().flags(MaterialFlags.GENERATE_FRAME).color(0x4C4C4B)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister()

        NaquadahBasedLiquidFuel =
            Builder("naquadah_based_liquid_fuel").fluid().color(0x43b54a)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("Nq42Ce16Nd16")

        NaquadahBasedLiquidFuelExcited =
            Builder("naquadah_based_liquid_fuel_excited").fluid().color(0x41b349)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("*(Nq42Ce16Nd16)*")

        NaquadahBasedLiquidFuelDepleted =
            Builder("naquadah_based_liquid_fuel_depleted").fluid().color(0x215825)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("Nq?Ke?Nd?")

        NeutroniumMixture =
            Builder("neutronium_mixture").dust().color(0xFFFFFF).secondaryColor(0x000000)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister().setFormula("?Nt?")
    }
}
