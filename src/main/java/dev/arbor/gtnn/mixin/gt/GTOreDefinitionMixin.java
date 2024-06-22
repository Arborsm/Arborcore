package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.worldgen.BiomeWeightModifier;
import com.gregtechceu.gtceu.api.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.worldgen.IWorldGenLayer;
import com.gregtechceu.gtceu.api.worldgen.generator.IndicatorGenerator;
import com.gregtechceu.gtceu.api.worldgen.generator.VeinGenerator;
import dev.arbor.gtnn.GTNN;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Mixin(value = GTOreDefinition.class, remap = false)
public abstract class GTOreDefinitionMixin {

    @Shadow
    private IntProvider clusterSize;

    @Inject(method = "<init>(Lnet/minecraft/util/valueproviders/IntProvider;FILcom/gregtechceu/gtceu/api/worldgen/IWorldGenLayer;Ljava/util/Set;Lnet/minecraft/world/level/levelgen/placement/HeightRangePlacement;FLjava/util/function/Supplier;Lcom/gregtechceu/gtceu/api/worldgen/BiomeWeightModifier;Lcom/gregtechceu/gtceu/api/worldgen/generator/VeinGenerator;Ljava/util/List;)V",
            at = @At("TAIL"))
    private void init(IntProvider clusterSize, float density, int weight, IWorldGenLayer layer,
                      Set<ResourceKey<Level>> dimensionFilter, HeightRangePlacement range,
                      float discardChanceOnAirExposure,
                      Supplier<HolderSet<Biome>> biomes, BiomeWeightModifier biomeWeightModifier,
                      VeinGenerator veinGenerator,
                      List<IndicatorGenerator> indicatorGenerators, CallbackInfo ci) {
        final int min = (int) (clusterSize.getMinValue() * GTNN.INSTANCE.getServerConfig().gtOresMultiplyNum);
        final int max = (int) (clusterSize.getMaxValue() * GTNN.INSTANCE.getServerConfig().gtOresMultiplyNum);
        this.clusterSize = UniformInt.of(min, max);
    }
}
