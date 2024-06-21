package dev.arbor.gtnn.api.recipe

import com.google.gson.JsonObject
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.condition.RecipeCondition
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.arbor.gtnn.api.machine.multiblock.NeutronActivatorMachine.Companion.checkNeutronActivatorCondition
import dev.arbor.gtnn.data.GTNNRecipesConditions
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.util.GsonHelper

class NeutronActivatorCondition(max: Int, min: Int) : RecipeCondition() {
    companion object {
        val INSTANCE: NeutronActivatorCondition = NeutronActivatorCondition()
        val CODEC: MapCodec<NeutronActivatorCondition> = RecordCodecBuilder
            .mapCodec { instance: RecordCodecBuilder.Instance<NeutronActivatorCondition> ->
                isReverse(instance)
                    .and(Codec.INT.fieldOf("ev_range").forGetter { it.evRange })
                    .apply(instance, ::NeutronActivatorCondition)
            }
    }
    var evRange: Int = 0

    constructor() : this(0, 0)
    constructor(evRange: Int) : this() {
        this.evRange = evRange
    }

    constructor(isReverse: Boolean, evRange: Int) : this(evRange) {
        super.isReverse = isReverse
    }

    init {
        this.evRange = max * 10000 + min
    }

    override fun getType(): RecipeConditionType<*> {
        return GTNNRecipesConditions.NA
    }

    override fun getTooltips(): Component {
        val max = evRange % 10000
        val min = evRange / 10000
        return Component.translatable("gtnn.recipe.condition.neutron_activator_condition_tooltip", max, min)
    }

    override fun test(gtRecipe: GTRecipe, recipeLogic: RecipeLogic): Boolean {
        return checkNeutronActivatorCondition((recipeLogic.machine as MetaMachine), gtRecipe)
    }

    override fun createTemplate(): RecipeCondition {
        return NeutronActivatorCondition()
    }

    override fun serialize(): JsonObject {
        val value = super.serialize()
        value.addProperty("evRange", this.evRange)
        return value
    }

    override fun deserialize(config: JsonObject): RecipeCondition {
        super.deserialize(config)
        this.evRange = GsonHelper.getAsInt(config, "evRange", 0)
        return this
    }

    override fun toNetwork(buf: RegistryFriendlyByteBuf) {
        super.toNetwork(buf)
        buf.writeInt(this.evRange)
    }

    override fun fromNetwork(buf: RegistryFriendlyByteBuf): RecipeCondition {
        super.fromNetwork(buf)
        this.evRange = buf.readInt()
        return this
    }
}
