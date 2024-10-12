package dev.arbor.gtnn.init

import dev.arbor.gtnn.GTNNRegistries
import dev.arbor.gtnn.data.GTNNDataGen

object CommonProxy {
    fun init() {
        GTNNDataGen.init()
        GTNNRegistries.REGISTRATE.registerRegistrate()
    }
}
