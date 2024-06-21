package dev.arbor.gtnn.api.recipe

import com.gregtechceu.gtceu.api.fluid.FluidBuilder
import com.gregtechceu.gtceu.api.fluid.attribute.FluidAttributes
import com.gregtechceu.gtceu.api.fluid.store.FluidStorageKeys
import com.gregtechceu.gtceu.api.material.material.Material
import net.minecraft.resources.ResourceLocation

class GTNNBuilder(resourceLocation: ResourceLocation?) : Material.Builder(resourceLocation) {

    fun acid(): Material.Builder {
        fluid(FluidStorageKeys.LIQUID, FluidBuilder().attribute(FluidAttributes.ACID))
        return this
    }
}
