package dev.arbor.gtnn.api.machine.feature

import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine

interface IGTPPMachine {
    fun getTier(): Int

    fun scheduleRenderUpdate(machine: MultiblockControllerMachine) {
        for (part in machine.getParts()) {
            part.self().scheduleRenderUpdate()
        }
    }
}
