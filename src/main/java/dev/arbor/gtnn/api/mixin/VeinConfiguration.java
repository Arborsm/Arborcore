package dev.arbor.gtnn.api.mixin;

import com.gregtechceu.gtceu.api.data.worldgen.ores.GeneratedVeinMetadata;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;

public record VeinConfiguration(GeneratedVeinMetadata data, RandomSource random) {

    public RandomSource newRandom() {
        return new XoroshiroRandomSource(random.nextLong());
    }
}
