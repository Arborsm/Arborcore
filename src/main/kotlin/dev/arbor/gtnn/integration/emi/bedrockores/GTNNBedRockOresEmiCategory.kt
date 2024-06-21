package dev.arbor.gtnn.integration.emi.bedrockores

import com.gregtechceu.gtceu.api.GTValues.*
import com.gregtechceu.gtceu.data.machine.GTMachines
import dev.arbor.gtnn.api.recipe.OresHelper
import dev.arbor.gtnn.api.tool.StringTools.nn
import dev.arbor.gtnn.data.GTNNMachines
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.network.chat.Component

class GTNNBedRockOresEmiCategory :
    EmiRecipeCategory("bedrock_ores".nn(), EmiStack.of(GTNNMachines.STONE_BEDROCK_ORE_MACHINE.asStack())) {
    companion object {
        val CATEGORY = GTNNBedRockOresEmiCategory()

        fun registerDisplays(registry: EmiRegistry) {
            for (ore in OresHelper.INSTANCE.getOreCleanList()) {
                registry.addRecipe(GTNNEmiBedrockOres(ore))
            }
        }

        fun registerWorkStations(registry: EmiRegistry) {
            registry.addWorkstation(CATEGORY, EmiStack.of(GTNNMachines.STONE_BEDROCK_ORE_MACHINE.asStack()))
            registry.addWorkstation(CATEGORY, EmiStack.of(GTMachines.BEDROCK_ORE_MINER[MV].asStack()))
            registry.addWorkstation(CATEGORY, EmiStack.of(GTMachines.BEDROCK_ORE_MINER[HV].asStack()))
            registry.addWorkstation(CATEGORY, EmiStack.of(GTMachines.BEDROCK_ORE_MINER[EV].asStack()))
        }
    }

    override fun getName(): Component {
        return Component.translatable("gtnn.jei.bedrock_ores")
    }
}