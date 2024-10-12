package dev.arbor.gtnn.api.item.properties

import com.google.common.base.Preconditions
import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties

class CatalystProperty(maxDurability: Int) : IMaterialProperty<CatalystProperty?> {
    var maxDurability: Int = maxDurability
        set(value) {
            Preconditions.checkArgument(value > 0, "Invalid Max Durability")
            field = value
        }

    override fun verifyProperty(materialProperties: MaterialProperties) {}
}