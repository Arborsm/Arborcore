package dev.arbor.gtnn.api.recipe

import com.google.gson.JsonObject
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.condition.RecipeCondition
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.arbor.gtnn.block.PlantCasingBlock
import dev.arbor.gtnn.data.GTNNRecipesConditions
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.util.GsonHelper

class PlantCasingCondition(var plantCasing: PlantCasingBlock?) : RecipeCondition() {
    companion object{
        val INSTANCE: PlantCasingCondition = PlantCasingCondition()
        val CODEC: MapCodec<PlantCasingCondition> = RecordCodecBuilder
            .mapCodec { instance: RecordCodecBuilder.Instance<PlantCasingCondition> ->
                isReverse(instance)
                    .and(Codec.STRING.fieldOf("plantCasing").forGetter { it.plantCasing?.getName() ?: "" })
                    .apply(instance, ::PlantCasingCondition)
            }
    }

    constructor(): this(null)
    constructor(plantCasing: String) : this(PlantCasingBlock.getByName(plantCasing))
    constructor(isReverse: Boolean, plantCasing: String) : this(plantCasing) {
        super.isReverse = isReverse
    }

    override fun getType(): RecipeConditionType<*> {
        return GTNNRecipesConditions.PLANT_CASING
    }

    override fun getTooltips(): Component {
        return Component.translatable(
            "gtnn.recipe.condition.plant_casing.tooltip",
            plantCasing!!.getTier() + 1,
            plantCasing!!.getName()
        )
    }

    override fun test(recipe: GTRecipe, recipeLogic: RecipeLogic): Boolean {
        val machine = recipeLogic.getMachine()
        return machine is IMultiController && this.plantCasing != null
    }

    override fun createTemplate(): RecipeCondition {
        return PlantCasingCondition()
    }

    override fun serialize(): JsonObject {
        val value = super.serialize()
        value.addProperty("plantCasing", plantCasing!!.getName())
        return value
    }

    override fun deserialize(config: JsonObject): RecipeCondition {
        super.deserialize(config)
        this.plantCasing = PlantCasingBlock.getByName(
            GsonHelper.getAsString(config, "plantCasing", "plantCasing")
        )
        return this
    }

    override fun toNetwork(buf: RegistryFriendlyByteBuf) {
        super.toNetwork(buf)
        buf.writeUtf(plantCasing!!.getName())
    }

    override fun fromNetwork(buf: RegistryFriendlyByteBuf): RecipeCondition {
        super.fromNetwork(buf)
        this.plantCasing = PlantCasingBlock.getByNameOrDefault(buf.readUtf())
        return this
    }
}
