package dev.arbor.gtnn.integration.emi.bedrockores

import com.lowdragmc.lowdraglib.emi.ModularEmiRecipe
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup
import dev.arbor.gtnn.api.tool.StringTools.nn
import dev.arbor.gtnn.integration.GTNNBedrockOreVeinWidget
import dev.emi.emi.api.recipe.EmiRecipeCategory
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class GTNNEmiBedrockOres(oreVeins: Pair<ResourceKey<Level>, HashMap<ItemStack, Int>>) : ModularEmiRecipe<WidgetGroup>(
    { GTNNBedrockOreVeinWidget(oreVeins) }
) {
    private val dimensions: String = oreVeins.first.location().path

    override fun getCategory(): EmiRecipeCategory {
        return GTNNBedRockOresEmiCategory.CATEGORY
    }

    override fun getId(): ResourceLocation {
        return "gtnn_bedrock_$dimensions".nn()
    }

}