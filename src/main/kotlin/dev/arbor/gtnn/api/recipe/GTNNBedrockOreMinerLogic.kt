package dev.arbor.gtnn.api.recipe

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.capability.recipe.IO
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier
import com.gregtechceu.gtceu.api.registry.GTRegistries
import com.gregtechceu.gtceu.common.machine.multiblock.electric.BedrockOreMinerMachine
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder
import dev.arbor.gtnn.api.machine.StoneBedrockOreMinerMachine
import dev.arbor.gtnn.api.tool.StringTools.nn
import dev.arbor.gtnn.data.GTNNRecipeTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.ItemStack
import net.neoforged.fml.loading.FMLLoader

class GTNNBedrockOreMinerLogic<T : IRecipeLogicMachine>(machine: T) : RecipeLogic(machine) {
    private var veinOres: List<Pair<Int, ItemStack>>? = null

    companion object {
        val DURATION: Int = if (FMLLoader.isProduction()) 20 else 1
    }

    override fun findAndHandleRecipe() {
        val level = getMachine().level
        if (level is ServerLevel && GTRegistries.ORE_VEINS.any()) {
            lastRecipe = null
            OresHelper.INSTANCE.oresWeighted.filter { it.first.contains(level.dimension()) }.map { it.second }
                .let { veinOres = it }
            val match: GTRecipe? = getOreMinerRecipe()
            if (match != null) {
                val copied = match.copy(ContentModifier(match.duration.toDouble(), 0.0))
                if (match.matchRecipe(this.machine).isSuccess && copied.matchTickRecipe(this.machine).isSuccess) {
                    setupRecipe(match)
                }
            }
        }
    }

    private fun getOreMinerRecipe(): GTRecipe? {
        val machine = getMachine()
        val level = machine.level
        if (level is ServerLevel && veinOres != null && machine is IRecipeLogicMachine) {
            val stack = veinOres!![OresHelper.INSTANCE.getRandomItem(veinOres!!, veinOres!!.size)].second
            if (stack.isEmpty) return null
            var amount = 1
            val recipe: GTRecipe
            if (machine is BedrockOreMinerMachine) {
                amount = OresHelper.INSTANCE.getRigMultiplier(machine.tier)
                if (machine.overclockTier > machine.tier) {
                    amount *= 3 / 2
                }
                recipe = GTRecipeBuilder.ofRaw().duration(DURATION)
                    .EUt(GTValues.VA[machine.energyTier].toLong()).outputItems(stack.item, amount).build()
            } else {
                recipe =
                    GTRecipeBuilder.of("stone_bedrock_ores".nn(), GTNNRecipeTypes.STONE_BEDROCK_ORE_MACHINE_RECIPES)
                        .duration(DURATION)
                        .outputItems(stack.item, amount).build()
            }

            if (machine is StoneBedrockOreMinerMachine && machine.getFilter() != null) {
                return GTRecipeBuilder.of("stone_bedrock_ores".nn(), GTNNRecipeTypes.STONE_BEDROCK_ORE_MACHINE_RECIPES)
                    .duration(DURATION * 10)
                    .outputItems(machine.getFilter()!!, amount).build()
            }

            if (recipe.matchRecipe(machine).isSuccess && recipe.matchTickRecipe(machine).isSuccess) {
                return recipe
            }
        }
        return null
    }

    override fun onRecipeFinish() {
        machine.afterWorking()
        if (lastRecipe != null) {
            lastRecipe!!.postWorking(this.machine)
            lastRecipe!!.handleRecipeIO(IO.OUT, this.machine)
        }
        // try it again
        val match = getOreMinerRecipe()
        if (match != null) {
            val copied = match.copy(ContentModifier(match.duration.toDouble(), 0.0))
            if (match.matchRecipe(this.machine).isSuccess && copied.matchTickRecipe(this.machine).isSuccess) {
                setupRecipe(match)
                return
            }
        }
        status = Status.IDLE
        progress = 0
        duration = 0
    }
}