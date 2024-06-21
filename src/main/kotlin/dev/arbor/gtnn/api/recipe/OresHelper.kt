package dev.arbor.gtnn.api.recipe

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.material.ChemicalHelper
import com.gregtechceu.gtceu.api.material.material.Material
import com.gregtechceu.gtceu.api.registry.GTRegistry
import com.gregtechceu.gtceu.api.tag.TagPrefix
import com.gregtechceu.gtceu.api.worldgen.GTOreDefinition
import com.gregtechceu.gtceu.data.material.GTMaterials.*
import com.mojang.datafixers.util.Either
import dev.arbor.gtnn.GTNN.getServerConfig
import dev.arbor.gtnn.data.GTNNMaterials.*
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class OresHelper(oreVeins: GTRegistry.RL<GTOreDefinition>) {
    companion object {
        @JvmField
        val ORE_REPLACEMENTS: MutableMap<Material, Material> = mutableMapOf(Neutronium to NeutroniumMixture)
        lateinit var INSTANCE: OresHelper

        init {
            if (getServerConfig().enableHarderNaquadahLine) {
                ORE_REPLACEMENTS.putAll(
                    listOf(
                        Naquadah to NaquadahOxideMixture,
                        NaquadahEnriched to EnrichedNaquadahOxideMixture,
                        Naquadria to NaquadriaOxideMixture
                    )
                )
            }
            if (getServerConfig().enableHarderPlatinumLine) {
                ORE_REPLACEMENTS.putAll(
                    listOf(
                        Platinum to PlatinumMetal,
                        Palladium to PalladiumMetal
                    )
                )
            }
        }
    }

    private val ores: List<Pair<Set<ResourceKey<Level>>, Pair<Int, Pair<List<Int>, List<ItemStack>>>>>
    private val oresClean: Map<ResourceKey<Level>, List<Pair<Int, ItemStack>>>
    val allowItem: List<Item>
    val oresWeighted: List<Pair<Set<ResourceKey<Level>>, Pair<Int, ItemStack>>>

    init {
        ores = oreVeins.map {
            it.dimensionFilter() to (it.weight() to (getChance(it) to getContainedOresAndBlocks(it)))
        }
        oresWeighted = ores.flatMap {
            val (dimensions, pair) = it
            val (weight, pair2) = pair
            val (chances, ores) = pair2
            chances.zip(ores) { chance, ore ->
                Pair(dimensions, weight * chance to ore)
            }
        }
        oresClean = oresWeighted.flatMap { weightedPair ->
            weightedPair.first.map { resourceKey ->
                resourceKey to weightedPair.second
            }
        }.groupBy(keySelector = { it.first }, valueTransform = { it.second }).mapValues { (_, value) ->
            value.toList()
        }
        allowItem = oresWeighted.map { it.second.second.item }
    }

    private fun getChance(oreDefinition: GTOreDefinition): List<Int> {
        return oreDefinition.veinGenerator().allChances
    }

    private fun getContainedOresAndBlocks(oreDefinition: GTOreDefinition): List<ItemStack> {
        return oreDefinition.veinGenerator().allEntries.map { entry: Map.Entry<Either<BlockState, Material>, Int> ->
            entry.key.map({
                ItemStack.EMPTY
            }, { material: Material ->
                ChemicalHelper.get(TagPrefix.rawOre, material)
            })
        }.toList()
    }

    fun <T> getRandomItem(randomList: List<Pair<Int, T>>, size: Int): Int {
        if (randomList.isEmpty()) return -1
        val baseOffsets = IntArray(size)
        var currentIndex = 0
        for (i in 0 until size) {
            val entry = randomList[i]
            require(entry.first > 0) { "Invalid weight: " + entry.first }
            currentIndex += entry.first
            baseOffsets[i] = currentIndex
        }
        val randomValue: Int = GTValues.RNG.nextInt(currentIndex)
        for (i in 0 until size) {
            if (randomValue < baseOffsets[i]) return i
        }
        throw IllegalArgumentException("Invalid weight")
    }

    fun getRigMultiplier(tier: Int): Int {
        if (tier == GTValues.MV) return 8
        if (tier == GTValues.HV) return 32
        if (tier == GTValues.EV) return 64
        return 1
    }

    fun getOreCleanList(): MutableList<Pair<ResourceKey<Level>, HashMap<ItemStack, Int>>> {
        val levels = oresClean.keys
        val all: MutableList<Pair<ResourceKey<Level>, HashMap<ItemStack, Int>>> = mutableListOf()
        for (level in levels) {
            val things: HashMap<ItemStack, Int> = HashMap()
            val sum = oresClean[level]!!.sumOf { it.first }
            for (ore in oresClean[level]!!) {
                val chance = ore.first * 10000 / sum
                val stack = ItemStack(ore.second.item, 1)
                if (stack.isEmpty) continue
                things[stack] = chance
            }
            all.add(level to things)
        }
        return all
    }
}