package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.data.chemical.Element
import com.gregtechceu.gtceu.common.data.GTElements

object GTNNElement {
    val IF: Element = GTElements.createAndRegister(166, 213, -1, null, "Infinity Catalyst", "If", false)
    val SpNt: Element = GTElements.createAndRegister(167, 233, -1, null, "Space Neutronium", "SpNt", false)
    val IF2: Element = GTElements.createAndRegister(168, 316, -1, null, "Infinity", "∞", false)
    val Th232: Element = GTElements.createAndRegister(90, 142, -1, null, "Thorium-232", "Th-232", true)
    val Ds: Element = GTElements.createAndRegister(150, 144, -1, null, "Desh", "Ds", false)
    val Ot: Element = GTElements.createAndRegister(151, 156, -1, null, "Ostrum", "Os", false)
    val Ct: Element = GTElements.createAndRegister(152, 162, -1, null, "Calorite", "Ct", false)

    fun init() {
    }
}
