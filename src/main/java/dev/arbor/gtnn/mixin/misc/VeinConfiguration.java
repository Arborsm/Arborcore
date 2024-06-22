package dev.arbor.gtnn.mixin.misc;

import net.minecraft.util.RandomSource;

import com.gregtechceu.gtceu.api.worldgen.ores.GeneratedVeinMetadata;

public record VeinConfiguration(GeneratedVeinMetadata data, RandomSource random) {}
