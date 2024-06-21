package dev.arbor.gtnn.mixin.misc;

import com.gregtechceu.gtceu.api.worldgen.ores.GeneratedVeinMetadata;
import net.minecraft.util.RandomSource;

public record VeinConfiguration(GeneratedVeinMetadata data, RandomSource random) {
}
