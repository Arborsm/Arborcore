package dev.arbor.gtnn.api.pattern

import com.gregtechceu.gtceu.api.GTValues.*
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility
import com.gregtechceu.gtceu.api.multiblock.Predicates
import com.gregtechceu.gtceu.api.multiblock.TraceabilityPredicate
import com.gregtechceu.gtceu.api.multiblock.error.PatternStringError
import com.lowdragmc.lowdraglib.utils.BlockInfo
import dev.arbor.gtnn.block.MachineCasingBlock
import dev.arbor.gtnn.block.PipeBlock
import dev.arbor.gtnn.block.PlantCasingBlock
import net.minecraft.network.chat.Component


@SuppressWarnings("unused")
object APredicates {
    fun plantCasings(): TraceabilityPredicate {
        return TraceabilityPredicate({
            val blockState = it.getBlockState()
            for (entry in PlantCasingBlock.entries) {
                if (blockState.`is`(entry.getPlantCasing(entry.getTier()).get())) {
                    val stats = entry.plantCasingType()
                    val currentPlantCasing = it.matchContext.getOrPut("PlantCasing", stats)
                    if (!currentPlantCasing.equals(stats)) {
                        it.setError(PatternStringError("gtnn.multiblock.pattern.error.plant_casings"))
                        return@TraceabilityPredicate false
                    }
                    return@TraceabilityPredicate true
                }
            }
            return@TraceabilityPredicate false
        }, {
            PlantCasingBlock.entries
                .map { BlockInfo.fromBlockState(it.getPlantCasing(it.getTier()).get().defaultBlockState()) }
                .toTypedArray()
        }).addTooltips(Component.translatable("gtnn.multiblock.pattern.error.plant_casings"))
    }

    fun pipeBlock(): TraceabilityPredicate {
        return TraceabilityPredicate({
            val blockState = it.getBlockState()
            for (entry in PipeBlock.Pipe.entries) {
                if (blockState.`is`(entry.getPipe().get())) {
                    val stats = entry.pipeType()
                    val currentPipeBlock = it.matchContext.getOrPut("Pipe", stats)
                    if (!currentPipeBlock.equals(stats)) {
                        it.setError(PatternStringError("gtnn.multiblock.pattern.error.pipe"))
                        return@TraceabilityPredicate false
                    }
                    return@TraceabilityPredicate true
                }
            }
            return@TraceabilityPredicate false
        }, {
            PipeBlock.Pipe.entries.map { BlockInfo.fromBlockState(it.getPipe().get().defaultBlockState()) }
                .toTypedArray()
        }).addTooltips(Component.translatable("gtnn.multiblock.pattern.error.pipe"))
    }

    fun machineCasing(): TraceabilityPredicate {
        return TraceabilityPredicate({
            val blockState = it.getBlockState()
            for (entry in MachineCasingBlock.MachineCasing.entries) {
                if (blockState.`is`(entry.getMachineCasing().get())) {
                    val stats = entry.machineCasingType()
                    val currentMachineCasing = it.matchContext.getOrPut("MachineCasing", stats)
                    if (!currentMachineCasing.equals(stats)) {
                        it.setError(PatternStringError("gtnn.multiblock.pattern.error.machine_casing"))
                        return@TraceabilityPredicate false
                    }
                    return@TraceabilityPredicate true
                }
            }
            return@TraceabilityPredicate false
        }, {
            MachineCasingBlock.MachineCasing.entries
                .map { BlockInfo.fromBlockState(it.getMachineCasing().get().defaultBlockState()) }.toTypedArray()
        }).addTooltips(Component.translatable("gtnn.multiblock.pattern.error.machine_casing"))
    }

    fun ability(ability: PartAbility): TraceabilityPredicate {
        val tiers = listOf(ULV, LV, MV, HV, EV, IV, LuV, ZPM, UV).filter { it <= 1 }.toIntArray()
        return Predicates.blocks(*(if (tiers.isEmpty()) ability.allBlocks else ability.getBlocks(*tiers)).toTypedArray())
    }
}
