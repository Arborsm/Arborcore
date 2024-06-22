package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.worldgen.WorldGeneratorUtils;
import com.gregtechceu.gtceu.api.worldgen.ores.GeneratedVeinMetadata;
import com.gregtechceu.gtceu.api.worldgen.ores.OreGenerator;
import com.gregtechceu.gtceu.config.ConfigHolder;
import dev.arbor.gtnn.GTNN;
import dev.arbor.gtnn.mixin.misc.VeinConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.stream.Stream;

@Mixin(value = OreGenerator.class, remap = false)
public abstract class OreGeneratorMixin {

    @Unique
    @NotNull
    private static Optional<BlockPos> gtnn$computeVeinOrigin(WorldGenLevel level, ChunkGenerator generator,
                                                             RandomSource random, BlockPos veinCenter,
                                                             GTOreDefinition entry) {
        int layerSeed = WorldGeneratorUtils.getWorldGenLayerKey(entry.layer()).map(String::hashCode).orElse(0);
        var layeredRandom = new XoroshiroRandomSource(random.nextLong() ^ ((long) layerSeed));

        return entry.range()
                .getPositions(new PlacementContext(level, generator, Optional.empty()), layeredRandom, veinCenter)
                .findFirst();
    }

    @Unique
    private static VeinConfiguration gtnn$logVeinGeneration(VeinConfiguration config) {
        if (ConfigHolder.INSTANCE.dev.debugWorldgen) {
            GTCEu.LOGGER.debug("Generating vein {} at {}", config.data().id(), config.data().center());
        }

        return config;
    }

    @Unique
    private static Optional<BlockPos> gtnn$getVeinCenter(ChunkPos chunkPos) {
        int gridSize = ConfigHolder.INSTANCE.worldgen.oreVeins.oreVeinGridSize;
        if (chunkPos.x % gridSize == 0 && chunkPos.z % gridSize == 0) {
            BlockPos chunkCenter = chunkPos.getMiddleBlockPosition(0);
            return Optional.of(chunkCenter);
        } else {
            return Optional.empty();
        }
    }

    @Inject(method = "generateMetadata", at = @At("HEAD"), cancellable = true)
    private void generateMetadata(WorldGenLevel level, ChunkGenerator chunkGenerator, ChunkPos chunkPos,
                                  CallbackInfoReturnable<List<GeneratedVeinMetadata>> cir) {
        if (GTNN.INSTANCE.getServerConfig().timesOreVeins != 1) {
            cir.setReturnValue(gtnn$createConfigs(level, chunkGenerator, chunkPos).stream()
                    .map(OreGeneratorMixin::gtnn$logVeinGeneration).map(VeinConfiguration::data).toList());
        }
    }

    @Shadow
    protected abstract Stream<GTOreDefinition> getEntries(WorldGenLevel level, BlockPos veinCenter,
                                                          XoroshiroRandomSource random);

    @Unique
    private List<VeinConfiguration> gtnn$createConfigs(WorldGenLevel level, ChunkGenerator generator,
                                                       ChunkPos chunkPos) {
        List<VeinConfiguration> list = new ArrayList<>();
        Set<GTOreDefinition> oreVeins = new HashSet<>();
        for (int i = 0; i < GTNN.INSTANCE.getServerConfig().timesOreVeins; i++) {
            var random = new XoroshiroRandomSource(level.getSeed() ^ chunkPos.toLong() + i);

            list.addAll(gtnn$getVeinCenter(chunkPos).stream().flatMap(veinCenter -> {
                var oreVeinList = getEntries(level, veinCenter, random).toList();
                while (oreVeins.containsAll(oreVeinList)) {
                    oreVeinList = getEntries(level, veinCenter, new XoroshiroRandomSource(random.nextLong())).toList();
                }
                oreVeins.addAll(oreVeinList);
                return oreVeinList.stream().map(entry -> {
                    var id = GTRegistries.ORE_VEINS.getKey(entry);
                    if (entry == null) return null;
                    BlockPos origin = gtnn$computeVeinOrigin(level, generator, random, veinCenter, entry)
                            .orElseThrow(() -> new IllegalStateException(
                                    "Cannot determine y coordinate for the vein at " + veinCenter));
                    return new VeinConfiguration(new GeneratedVeinMetadata(id, chunkPos, origin, entry), random);
                });
            }).toList());
        }
        return list;
    }
}
