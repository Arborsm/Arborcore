package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.material.Element
import com.gregtechceu.gtceu.data.material.GTElements

object GTNNElement {
    val IF: Element = GTElements.createAndRegister(999, 9999, -1, null, "Infinity Catalyst", "If", false)
    val SpNt: Element = GTElements.createAndRegister(9999, 99999, -1, null, "Space Neutronium", "SpNt", false)
    val IF2: Element = GTElements.createAndRegister(99999, 999999, -1, null, "Infinity", "If*", false)
    val Th232: Element = GTElements.createAndRegister(90, 142, -1, null, "Thorium-232", "Th-232", true)
    val Ds: Element = GTElements.createAndRegister(200, 300, -1, null, "Desh", "Ds", false)
    val Ot: Element = GTElements.createAndRegister(201, 144, -1, null, "Ostrum", "Os", false)
    val Ct: Element = GTElements.createAndRegister(202, 144, -1, null, "Calorite", "Ct", false)

    fun init() {
    }
}
