package dev.arbor.gtnn.init

import dev.arbor.gtnn.GTNNRegistries
import dev.arbor.gtnn.data.GTNNBlocks
import dev.arbor.gtnn.data.GTNNDataGen
import dev.arbor.gtnn.data.GTNNItems

object CommonProxy {
    fun init() {
        GTNNItems.init()
        GTNNBlocks.init()
        GTNNDataGen.init()
        GTNNRegistries.REGISTRATE.registerRegistrate()
    }
}
