package dev.arbor.gtnn.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.DustProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.FluidProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.OreProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;

import net.minecraft.resources.ResourceLocation;

import dev.arbor.gtnn.GTNN;
import dev.arbor.gtnn.GTNNIntegration;
import dev.arbor.gtnn.api.recipe.GTNNBuilder;
import dev.arbor.gtnn.data.materials.*;
import dev.arbor.gtnn.data.recipes.BrineChain;

import java.util.Locale;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.SHINY;

public class GTNNMaterials extends Material {

    public static Material AndesiteAlloy;
    public static Material Desh;
    public static Material Ostrum;
    public static Material Calorite;
    public static Material SpaceNeutronium;
    public static Material Infinity;
    public static Material InfinityCatalyst;
    public static Material RP1;
    public static Material RP1RocketFuel;
    public static Material Kerosene;
    public static Material DenseHydrazineMixedFuel;
    public static Material Hydrazine;
    public static Material HydrogenPeroxide;
    public static Material EthylAnthraQuinone;
    public static Material EthylAnthraHydroQuinone;
    public static Material Anthracene;
    public static Material MethylhydrazineNitrateRocketFuel;
    public static Material MethylHydrazine;
    public static Material UDMHRocketFuel;
    public static Material UDMH;
    public static Material OrangeMetal;
    public static Material PhthalicAnhydride;
    public static Material VanadiumPentoxide;
    public static Material BlackMatter;
    public static Material Cerrobase140;
    public static Material PotassiumPyrosulfate;
    public static Material SodiumSulfate;
    public static Material ZincSulfate;
    public static Material Wollastonite;
    public static Material ArcaneCrystal;
    public static Material ManaSteel;
    public static Material TerraSteel;
    public static Material Elementium;
    public static Material RefinedRadiance;
    public static Material ShadowSteel;
    public static Material PlatinumSalt;
    public static Material RefinedPlatinumSalt;
    public static Material PalladiumSalt;
    public static Material RhodiumNitrate;
    public static Material RoughlyRhodiumMetal;
    public static Material PalladiumMetal;
    public static Material MetalSludge;
    public static Material PlatinumSlag;
    public static Material ReprecipitatedRhodium;
    public static Material SodiumNitrate;
    public static Material RhodiumSalt;
    public static Material RhodiumFilterCake;
    public static Material PlatinumMetal;
    public static Material Kaolinite;
    public static Material Dolomite;
    public static Material SodiumRutheniate;
    public static Material IridiumDioxide;
    public static Material ConcentratedPlatinum;
    public static Material PalladiumRichAmmonia;
    public static Material RutheniumTetroxideLQ;
    public static Material SodiumFormate;
    // public static Material FormicAcid;
    public static Material RhodiumSulfateGas;
    public static Material AcidicIridium;
    public static Material RutheniumTetroxideHot;
    public static Material NaquadahOxideMixture;
    public static Material EnrichedNaquadahOxideMixture;
    public static Material NaquadriaOxideMixture;
    public static Material HexafluorideEnrichedNaquadahSolution;
    public static Material XenonHexafluoroEnrichedNaquadate;
    public static Material PalladiumOnCarbon;
    public static Material GoldTrifluoride;
    public static Material EnrichedNaquadahResidueSolution;
    public static Material XenoauricFluoroantimonicAcid;
    public static Material GoldChloride;
    public static Material BromineTrifluoride;
    public static Material HexafluorideNaquadriaSolution;
    public static Material RadonDifluoride;
    public static Material RadonNaquadriaOctafluoride;
    public static Material NaquadriaResidueSolution;
    public static Material CaesiumFluoride;
    public static Material XenonTrioxide;
    public static Material CaesiumXenontrioxideFluoride;
    public static Material NaquadriaCaesiumXenonnonfluoride;
    public static Material RadonTrioxide;
    public static Material NaquadriaCaesiumfluoride;
    public static Material NitrosoniumOctafluoroxenate;
    public static Material NitrylFluoride;
    public static Material AcidicNaquadriaCaesiumfluoride;
    public static Material GraphiteUraniumMixture;
    public static Material UraniumCarbideThoriumMixture;
    public static Material PlutoniumOxideUraniumMixture;
    public static Material Thorium232;
    public static Material ThoriumBasedLiquidFuelExcited;
    public static Material ThoriumBasedLiquidFuelDepleted;
    public static Material ThoriumBasedLiquidFuel;
    public static Material UraniumBasedLiquidFuelExcited;
    public static Material UraniumBasedLiquidFuelDepleted;
    public static Material UraniumBasedLiquidFuel;
    public static Material PlutoniumBasedLiquidFuelExcited;
    public static Material PlutoniumBasedLiquidFuelDepleted;
    public static Material PlutoniumBasedLiquidFuel;
    public static Material RadiationProtection;
    public static Material NaquadahBasedLiquidFuel;
    public static Material NaquadahBasedLiquidFuelExcited;
    public static Material NaquadahBasedLiquidFuelDepleted;
    public static Material IodizedBrine;
    public static Material IodineBrineMixture;
    public static Material BrominatedBrine;
    public static Material IodineSlurry;
    public static Material AcidicBrominatedBrine;
    public static Material BromineSulfateSolution;
    public static Material OverheatedBromineSulfateSolution;
    public static Material WetBromine;
    public static Material DebrominatedWater;
    public static Material NeutroniumMixture;

    protected GTNNMaterials(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public static void init() {
        FirstMaterials.INSTANCE.init();
        SecondMaterials.INSTANCE.init();
        BrineChain.INSTANCE.init();
        if (GTNN.INSTANCE.getServerConfig().enableHarderPlatinumLine) PlatinumLineMaterials.INSTANCE.init();
        if (GTNN.INSTANCE.getServerConfig().enableHarderNaquadahLine) NaquadahMaterials.INSTANCE.init();
        if (GTNNIntegration.INSTANCE.isAdAstraLoaded()) AdAstraMaterials.INSTANCE.init();
        if (GTNNIntegration.INSTANCE.isBotaniaLoaded()) BotaniaMaterials.INSTANCE.init();
        if (GTNNIntegration.INSTANCE.isCreateLoaded()) CreateMaterials.INSTANCE.init();
        AdjustGTMaterials.INSTANCE.init();
    }

    public static void addDust(Material material) {
        if (!material.hasProperty(PropertyKey.DUST)) {
            material.setProperty(PropertyKey.DUST, new DustProperty());
        }
    }

    public static void addFluid(Material material) {
        if (!material.hasProperty(PropertyKey.FLUID)) {
            material.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        }
    }

    public static void addGas(Material material) {
        if (!material.hasProperty(PropertyKey.FLUID)) {
            material.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.GAS, new FluidBuilder()));
        }
    }

    public static void addOre(Material... materials) {
        materials[0].setProperty(PropertyKey.ORE, new OreProperty());
        if (materials.length == 2 && materials[1] != null) {
            var oreProperty = materials[0].getProperty(PropertyKey.ORE);
            oreProperty.setDirectSmeltResult(materials[1]);
            oreProperty.setOreByProducts(materials[1]);
            oreProperty.setSeparatedInto(materials[1]);
        }
    }

    public static GTNNBuilder Builder(String id) {
        return new GTNNBuilder(GTCEu.id(id));
    }

    public static class MaterialIcons {

        public static MaterialIconSet InfinityIcon = MaterialIconSet.ICON_SETS
                .getOrDefault("infinity".toLowerCase(Locale.ENGLISH), new MaterialIconSet("infinity", SHINY));
    }
}
