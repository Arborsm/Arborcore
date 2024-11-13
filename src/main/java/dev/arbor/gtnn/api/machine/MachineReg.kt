package dev.arbor.gtnn.api.machine

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability
import com.gregtechceu.gtceu.api.data.RotationState
import com.gregtechceu.gtceu.api.machine.*
import com.gregtechceu.gtceu.api.recipe.GTRecipeType
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder
import com.gregtechceu.gtceu.client.renderer.machine.SimpleGeneratorMachineRenderer
import com.gregtechceu.gtceu.common.data.GTMachines
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers
import com.gregtechceu.gtceu.utils.FormattingUtil
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.GTNNRegistries
import it.unimi.dsi.fastutil.ints.Int2IntFunction
import net.minecraft.network.chat.Component
import java.util.*
import java.util.function.BiFunction

object MachineReg {
    fun registerTieredMachines(
        name: String,
        factory: BiFunction<IMachineBlockEntity, Int, MetaMachine>,
        builder: BiFunction<Int, MachineBuilder<MachineDefinition>, MachineDefinition>,
        tiers: IntArray
    ): Array<MachineDefinition?> {
        return Array(size = GTValues.TIER_COUNT) {
            if (tiers.contains(it)) {
                return@Array builder.apply(
                    it,
                    GTNNRegistries.REGISTRATE.machine(GTValues.VN[it].lowercase(Locale.ROOT) + "_" + name) { holder ->
                        factory.apply(
                            holder, it
                        )
                    }.tier(it)
                )
            } else return@Array null
        }
    }

    fun registerSimpleMachines(
        name: String, recipeType: GTRecipeType, tankScalingFunction: Int2IntFunction, tiers: IntArray
    ): Array<MachineDefinition?> {
        return registerTieredMachines(name, { holder, tier ->
            SimpleTieredMachine(holder, tier, tankScalingFunction)
        }, { tier, builder ->
            builder.langValue("${GTValues.VLVH[tier]} ${FormattingUtil.toEnglishName(name)} ${GTValues.VLVT[tier]}")
                .editableUI(SimpleTieredMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id(name), recipeType))
                .rotationState(RotationState.NON_Y_AXIS).recipeType(recipeType)
                .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK))
                .workableTieredHullRenderer(GTNN.id("block/machines/$name")).tooltips(GTMachines.explosion()).tooltips(
                    *GTMachines.workableTiered(
                        tier, GTValues.V[tier], GTValues.V[tier] * 64, recipeType,
                        tankScalingFunction.apply(tier).toLong(), true
                    )
                ).compassNode(name).register()
        }, tiers)
    }

    fun registerGTNNGeneratorMachines(
        name: String,
        recipeType: GTRecipeType,
        recipeModifier: RecipeModifier,
        tankScalingFunction: Int2IntFunction,
        tiers: IntArray
    ): Array<MachineDefinition?> {
        return registerTieredMachines(name,
            { holder, tier -> GTNNGeneratorMachine(holder, tier, name, tankScalingFunction) },
            { tier, builder ->
                builder.langValue("${GTValues.VLVH[tier]} ${FormattingUtil.toEnglishName(name)} Generator ${GTValues.LVT[tier - 3]}")
                    .editableUI(SimpleGeneratorMachine.EDITABLE_UI_CREATOR.apply(GTNN.id(name), recipeType))
                    .rotationState(RotationState.ALL).recipeType(recipeType).recipeModifier(recipeModifier, true)
                    .addOutputLimit(ItemRecipeCapability.CAP, 0).addOutputLimit(FluidRecipeCapability.CAP, 0)
                    .renderer { SimpleGeneratorMachineRenderer(tier, GTNN.id("block/generators/$name")) }.tooltips(
                        Component.translatable(
                            "gtnn.machine.$name.tooltip", GTNNGeneratorMachine.getEfficiency(tier, name)
                        )
                    ).tooltips(GTMachines.explosion()).tooltips(
                        *GTMachines.workableTiered(
                            tier,
                            GTValues.V[tier],
                            GTValues.V[tier] * 64,
                            recipeType,
                            tankScalingFunction.apply(tier).toLong(),
                            false
                        )
                    ).compassNode(name).register()
            },
            tiers
        )
    }
}
