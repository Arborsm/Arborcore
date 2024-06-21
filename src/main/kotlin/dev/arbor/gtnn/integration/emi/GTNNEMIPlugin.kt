package dev.arbor.gtnn.integration.emi

import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.integration.emi.bedrockores.GTNNBedRockOresEmiCategory
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry

@EmiEntrypoint
class GTNNEMIPlugin : EmiPlugin {
    override fun register(registry: EmiRegistry) {
        if (GTNN.getServerConfig().skyblock) {
            registry.addCategory(GTNNBedRockOresEmiCategory.CATEGORY)
            GTNNBedRockOresEmiCategory.registerDisplays(registry)
            GTNNBedRockOresEmiCategory.registerWorkStations(registry)
        }
    }
}