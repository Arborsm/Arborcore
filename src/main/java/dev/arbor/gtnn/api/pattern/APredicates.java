package dev.arbor.gtnn.api.pattern;

import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.error.PatternStringError;
import com.lowdragmc.lowdraglib.utils.BlockInfo;
import dev.arbor.gtnn.block.MachineCasingBlock;
import dev.arbor.gtnn.block.PipeBlock;
import dev.arbor.gtnn.block.PlantCasingBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.*;
@SuppressWarnings("unused")
public class APredicates {
    public static TraceabilityPredicate plantCasings() {
        return new TraceabilityPredicate(blockWorldState -> {
            var blockState = blockWorldState.getBlockState();
            for (var entry : PlantCasingBlock.values()) {
                if (blockState.is(entry.getPlantCasing(entry.getTier()).get())) {
                    var stats = entry.plantCasingType();
                    Object currentPlantCasing = blockWorldState.getMatchContext().getOrPut("PlantCasing", stats);
                    if (!currentPlantCasing.equals(stats)) {
                        blockWorldState.setError(new PatternStringError("gtnn.multiblock.pattern.error.plant_casings"));
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }, () -> Arrays.stream(PlantCasingBlock.values()).map(plantCasing -> BlockInfo.fromBlockState(plantCasing.getPlantCasing(plantCasing.getTier()).get().defaultBlockState())).toArray(BlockInfo[]::new))
                .addTooltips(Component.translatable("gtnn.multiblock.pattern.error.plant_casings"));
    }

    public static TraceabilityPredicate pipeBlock() {
        return new TraceabilityPredicate(blockWorldState -> {
            var blockState = blockWorldState.getBlockState();
            for (var entry : PipeBlock.Pipe.values()) {
                if (blockState.is(entry.getPipe().get())) {
                    var stats = entry.pipeType();
                    Object currentPipeBlock = blockWorldState.getMatchContext().getOrPut("Pipe", stats);
                    if (!currentPipeBlock.equals(stats)) {
                        blockWorldState.setError(new PatternStringError("gtnn.multiblock.pattern.error.pipe"));
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }, () -> Arrays.stream(PipeBlock.Pipe.values()).map(pipe -> BlockInfo.fromBlockState(pipe.getPipe().get().defaultBlockState())).toArray(BlockInfo[]::new))
                .addTooltips(Component.translatable("gtnn.multiblock.pattern.error.pipe"));
    }

    public static TraceabilityPredicate machineCasing() {
        return new TraceabilityPredicate(blockWorldState -> {
            var blockState = blockWorldState.getBlockState();
            for (var entry : MachineCasingBlock.MachineCasing.values()) {
                if (blockState.is(entry.getMachineCasing().get())) {
                    var stats = entry.machineCasingType();
                    Object currentMachineCasing = blockWorldState.getMatchContext().getOrPut("MachineCasing", stats);
                    if (!currentMachineCasing.equals(stats)) {
                        blockWorldState.setError(new PatternStringError("gtnn.multiblock.pattern.error.machine_casing"));
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }, () -> Arrays.stream(MachineCasingBlock.MachineCasing.values()).map(machineCasing -> BlockInfo.fromBlockState(machineCasing.getMachineCasing().get().defaultBlockState())).toArray(BlockInfo[]::new))
                .addTooltips(Component.translatable("gtnn.multiblock.pattern.error.machine_casing"));
    }

    public static TraceabilityPredicate ability(PartAbility ability) {
        int[] tiers = Stream.of(ULV, LV, MV, HV, EV, IV, LuV, ZPM, UV).filter(t -> t <= 1).mapToInt(Integer::intValue).toArray();
        return Predicates.blocks((tiers.length == 0 ? ability.getAllBlocks() : ability.getBlocks(tiers)).toArray(Block[]::new));
    }
}
