package dev.arbor.gtnn.data.materials

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet
import com.gregtechceu.gtceu.api.fluids.FluidBuilder
import com.gregtechceu.gtceu.api.fluids.attribute.FluidAttributes
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys
import com.gregtechceu.gtceu.common.data.GTMaterials
import dev.arbor.gtnn.api.item.properties.CatalystProperty
import dev.arbor.gtnn.data.GTNNMaterials.*
import dev.arbor.gtnn.data.GTNNPropertyKeys.CATALYST

object NaquadahMaterials {
    fun init() {
        NaquadahOxideMixture =
            Builder("naquadah_oxide_mixture").dust().color(0x4c4c4c).iconSet(MaterialIconSet.ROUGH)
                .buildAndRegister().setFormula("??NqTiGa??")

        EnrichedNaquadahOxideMixture =
            Builder("enriched_naquadah_oxide_mixture").dust().color(0x826868)
                .iconSet(MaterialIconSet.ROUGH).buildAndRegister().setFormula("??Nq+??")

        NaquadriaOxideMixture =
            Builder("naquadria_oxide_mixture").dust().color(0x4d4d55).secondaryColor(0xe7e7ff)
                .iconSet(MaterialIconSet.RADIOACTIVE).buildAndRegister().setFormula("??*Nq*In??")

        HexafluorideEnrichedNaquadahSolution =
            Builder("hexafluoride_enriched_naquadah_solution").fluid().color(0x868D7F)
                .components(GTMaterials.NaquadahEnriched, 1, GTMaterials.Fluorine, 6)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()

        XenonHexafluoroEnrichedNaquadate =
            Builder("xenon_hexafluoro_enriched_naquadate").fluid().color(0x255A55)
                .components(GTMaterials.Xenon, 1, GTMaterials.NaquadahEnriched, 1, GTMaterials.Fluorine, 6)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()

        PalladiumOnCarbon =
            Builder("palladium_on_carbon").dust().color(0x480104).iconSet(MaterialIconSet.DULL)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).components(GTMaterials.Palladium, 1, GTMaterials.Carbon, 1)
                .buildAndRegister()

        GoldTrifluoride =
            Builder("gold_trifluoride").dust().color(0xE8C478).iconSet(MaterialIconSet.BRIGHT)
                .components(GTMaterials.Gold, 1, GTMaterials.Fluorine, 3).buildAndRegister()

        EnrichedNaquadahResidueSolution =
            Builder("enriched_naquadah_residue_solution").fluid().color(0x868D7F)
                .iconSet(MaterialIconSet.DULL).flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()
                .setFormula("XeAuSbKeF6S2?")

        XenoauricFluoroantimonicAcid = Builder("xenoauric_fluoroantimonic_acid")
            .fluid(FluidStorageKeys.LIQUID, FluidBuilder().attribute(FluidAttributes.ACID)).color(0xE0BD74)
            .components(GTMaterials.Xenon, 1, GTMaterials.Gold, 1, GTMaterials.Antimony, 1, GTMaterials.Fluorine, 6)
            .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()

        GoldChloride = Builder("gold_chloride").fluid().color(0xCCCC66)
            .components(GTMaterials.Gold, 2, GTMaterials.Chlorine, 6).buildAndRegister()

        BromineTrifluoride = Builder("bromine_trifluoride").fluid().color(0xA88E57)
            .components(GTMaterials.Bromine, 1, GTMaterials.Fluorine, 3).buildAndRegister()

        HexafluorideNaquadriaSolution =
            Builder("hexafluoride_naquadria_solution").fluid().color(0x25C213)
                .components(GTMaterials.Naquadria, 1, GTMaterials.Fluorine, 6)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()

        RadonDifluoride = Builder("radon_difluoride").fluid().color(0x8B7EFF)
            .components(GTMaterials.Radon, 1, GTMaterials.Fluorine, 2).buildAndRegister()

        RadonNaquadriaOctafluoride =
            Builder("radon_naquadria_octafluoride").fluid().color(0x85F378)
                .components(GTMaterials.Radon, 1, GTMaterials.Naquadria, 1, GTMaterials.Fluorine, 8)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()

        NaquadriaResidueSolution =
            Builder("naquadria_residue_solution").fluid().color(0x25C213).iconSet(MaterialIconSet.DULL)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister().setFormula("InPS6?", true)

        CaesiumFluoride = Builder("caesium_fluoride").fluid().color(0xFF7A5F)
            .components(GTMaterials.Caesium, 1, GTMaterials.Fluorine, 1).buildAndRegister()

        XenonTrioxide = Builder("xenon_trioxide").fluid().color(0x359FC3)
            .components(GTMaterials.Xenon, 1, GTMaterials.Oxygen, 3).buildAndRegister()

        CaesiumXenontrioxideFluoride =
            Builder("caesium_xenontrioxide_fluoride").fluid().color(0x5067D7)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).components(
                    GTMaterials.Caesium, 1, GTMaterials.Xenon, 1, GTMaterials.Oxygen, 3, GTMaterials.Fluorine, 1
                ).flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()

        NaquadriaCaesiumXenonnonfluoride =
            Builder("naquadria_caesium_xenonnonfluoride").fluid().color(0x54C248).components(
                GTMaterials.Naquadria, 1, GTMaterials.Caesium, 1, GTMaterials.Xenon, 1, GTMaterials.Fluorine, 9
            ).flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()

        RadonTrioxide = Builder("radon_trioxide").fluid().color(0x9A6DD7)
            .components(GTMaterials.Radon, 1, GTMaterials.Oxygen, 3).buildAndRegister()

        NaquadriaCaesiumfluoride =
            Builder("naquadria_caesiumfluoride").fluid().color(0xAAEB69)
                .components(GTMaterials.Naquadria, 1, GTMaterials.Fluorine, 3, GTMaterials.Caesium, 1)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister().setFormula("*Nq*F2CsF", true)

        NitrosoniumOctafluoroxenate =
            Builder("nitrosonium_octafluoroxenate").fluid().color(0x953D9F)
                .components(GTMaterials.NitrogenDioxide, 2, GTMaterials.Xenon, 1, GTMaterials.Fluorine, 8)
                .buildAndRegister().setFormula("(NO2)2XeF8", true)

        NitrylFluoride = Builder("nitryl_fluoride").fluid().color(0x8B7EFF)
            .components(GTMaterials.Nitrogen, 1, GTMaterials.Oxygen, 2, GTMaterials.Fluorine, 1)
            .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister()

        AcidicNaquadriaCaesiumfluoride =
            Builder("acidic_naquadria_caesiumfluoride").fluid().color(0x75EB00).components(
                GTMaterials.Naquadria, 1, GTMaterials.Fluorine, 3, GTMaterials.Caesium, 1, GTMaterials.Sulfur, 2, GTMaterials.Oxygen, 8
            ).flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister().setFormula("*Nq*F2CsF(SO4)2", true)

        PalladiumOnCarbon.setProperty(CATALYST, CatalystProperty(50))
    }
}
