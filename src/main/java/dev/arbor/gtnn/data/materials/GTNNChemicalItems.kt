package dev.arbor.gtnn.data.materials

import com.google.common.collect.ImmutableTable
import com.google.common.collect.Table
import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.item.ComponentItem
import com.gregtechceu.gtceu.api.item.IComponentItem
import com.gregtechceu.gtceu.api.item.component.IItemComponent
import com.gregtechceu.gtceu.common.data.GTItems
import com.gregtechceu.gtceu.utils.FormattingUtil
import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateItemModelProvider
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.nullness.NonNullBiConsumer
import com.tterrag.registrate.util.nullness.NonNullConsumer
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.data.GTNNTagPrefix.catalyst
import dev.arbor.gtnn.api.item.behaviors.CatalystBehavior
import dev.arbor.gtnn.api.item.behaviors.TagPrefixBehavior
import dev.arbor.gtnn.api.tool.StringTools.nn
import dev.arbor.gtnn.api.tool.StringTools.rl
import dev.arbor.gtnn.data.GTNNCreativeModeTabs
import dev.arbor.gtnn.data.GTNNPropertyKeys
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ModelFile
import java.util.function.Supplier

@Suppress("unused")
object GTNNChemicalItems {
    lateinit var CATALYST_ITEMS: Table<TagPrefix, Material, ItemEntry<ComponentItem>>
    @Suppress("UNUSED")
    val oops = REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.MAIN_TAB }

    private fun generateCatalystItems() {
        val builder = ImmutableTable.builder<TagPrefix, Material, ItemEntry<ComponentItem>>()
        for (registry in GTCEuAPI.materialManager.registries) {
            for (material in registry.allMaterials) {
                if (material.hasProperty(GTNNPropertyKeys.CATALYST)) {
                    builder.put(
                        catalyst,
                        material,
                        REGISTRATE
                            .item<ComponentItem>(catalyst.idPattern().format(material.name), ComponentItem::create)
                            .setData(ProviderType.LANG, NonNullBiConsumer.noop())
                            .transform(GTItems.unificationItem(catalyst, material))
                            .properties { p: Item.Properties -> p.stacksTo(catalyst.maxStackSize()) }
                            .model(NonNullBiConsumer.noop())
                            .color { Supplier { TagPrefixBehavior.tintColor() } }
                            .onRegister(
                                GTItems.attach(
                                    CatalystBehavior(
                                        material.getProperty(GTNNPropertyKeys.CATALYST).maxDurability
                                    )
                                )
                            )
                            .onRegister(attach(TagPrefixBehavior(catalyst, material)))
                            .register())
                }
            }
        }
        CATALYST_ITEMS = builder.build()
    }

    private fun registerCatalyst(name: String, color: Int): ItemBuilder<ComponentItem, Registrate> {
        return REGISTRATE
            .item<ComponentItem>(name + "_catalyst", ComponentItem::create)
            .color { CatalystBehavior.getItemStackColor(color) }
            .model(
                simpleCustomModel(
                    "item/generated".rl(),
                    "item/catalyst/base".nn(),
                    "item/catalyst/overlay".nn()
                )
            )
            .onRegister(attach(CatalystBehavior(50)))
    }

    private fun registerCatalyst(
        name: String, color: Int, maxDurability: Int
    ): ItemBuilder<ComponentItem, Registrate> {
        return REGISTRATE
            .item<ComponentItem>(name + "_catalyst", ComponentItem::create)
            .lang(FormattingUtil.toEnglishName(name) + "Catalyst")
            .color { CatalystBehavior.getItemStackColor(color) }
            .model(
                simpleCustomModel(
                    "item/generated".rl(),
                    "item/catalyst/base".nn(),
                    "item/catalyst/overlay".nn()
                )
            )
            .onRegister(attach(CatalystBehavior(maxDurability)))
    }

    private fun <T : Item?>
            simpleCustomModel(
        modelLocation: ResourceLocation?, vararg textureLocations: ResourceLocation?
    ): NonNullBiConsumer<DataGenContext<Item?, T>, RegistrateItemModelProvider> {
        return NonNullBiConsumer { ctx: DataGenContext<Item?, T>, prov: RegistrateItemModelProvider ->
            val builder = prov
                .getBuilder("item/" + prov.name { ctx.entry })
                .parent(ModelFile.UncheckedModelFile(modelLocation))
            for (i in textureLocations.indices) {
                builder.texture("layer%s".format(i), textureLocations[i])
            }
        }
    }

    private fun <T : IComponentItem?> attach(components: IItemComponent?): NonNullConsumer<T> {
        return NonNullConsumer { item: T -> item!!.attachComponents(components) }
    }

    fun init() {
        generateCatalystItems()
    }
}