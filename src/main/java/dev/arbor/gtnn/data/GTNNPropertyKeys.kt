package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey
import dev.arbor.gtnn.api.item.properties.CatalystProperty

object GTNNPropertyKeys {
    val CATALYST = PropertyKey("catalyst", CatalystProperty::class.java)
}