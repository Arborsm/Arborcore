package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.item.ComponentItem
import com.gregtechceu.gtceu.common.item.behavior.TooltipBehavior
import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.nullness.NonNullConsumer
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity

object GTNNItems {
    @Suppress("UNUSED")
    val oops = REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.MAIN_TAB }

    val RADIOACTIVE_WASTE: ItemEntry<ComponentItem> = createItem(
        "radioactive_waste"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Radioactive Waste").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.register()

    val HEAVY_INGOT_T1: ItemEntry<ComponentItem> = createItem(
        "heavy_ingot_t1"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Heavy Alloy Ingot T1").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.heavy_ingot_t1.tooltip")
        )
    })).register()
    val HEAVY_INGOT_T2: ItemEntry<ComponentItem> = createItem(
        "heavy_ingot_t2"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Heavy Alloy Ingot T2").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.heavy_ingot_t2.tooltip")
        )
    })).register()
    val HEAVY_INGOT_T3: ItemEntry<ComponentItem> = createItem(
        "heavy_ingot_t3"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Heavy Alloy Ingot T3").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.heavy_ingot_t3.tooltip")
        )
    })).register()
    val HEAVY_INGOT_T4: ItemEntry<ComponentItem> = createItem(
        "heavy_ingot_t4"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Heavy Alloy Ingot T4").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.heavy_ingot_t4.tooltip")
        )
    })).register()
    val HEAVY_PLATE_T1: ItemEntry<ComponentItem> = createItem(
        "heavy_plate_t1"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Heavy Alloy Plate T1").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.heavy_plate_t1.tooltip")
        )
    })).register()
    val HEAVY_PLATE_T2: ItemEntry<ComponentItem> = createItem(
        "heavy_plate_t2"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Heavy Alloy Plate T2").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.heavy_plate_t2.tooltip")
        )
    })).register()
    val HEAVY_PLATE_T3: ItemEntry<ComponentItem> = createItem(
        "heavy_plate_t3"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Heavy Alloy Plate T3").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.heavy_plate_t3.tooltip")
        )
    })).register()
    val HEAVY_PLATE_T4: ItemEntry<ComponentItem> = createItem(
        "heavy_plate_t4"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Heavy Alloy Plate T4").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.heavy_plate_t4.tooltip")
        )
    })).register()
    val CHIP_T1: ItemEntry<ComponentItem> = createItem(
        "t1_chip"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Chip T1").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.chip_t1.tooltip")
        )
    })).register()
    val CHIP_T2: ItemEntry<ComponentItem> = createItem(
        "t2_chip"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Chip T2").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.chip_t2.tooltip")
        )
    })).register()
    val CHIP_T3: ItemEntry<ComponentItem> = createItem(
        "t3_chip"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Chip T3").properties { p: Item.Properties ->
        p.rarity(
            Rarity.EPIC
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.chip_t3.tooltip")
        )
    })).register()
    val CHIP_T4: ItemEntry<ComponentItem> = createItem(
        "t4_chip"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Chip T4").properties { p: Item.Properties ->
        p.rarity(
            Rarity.EPIC
        )
    }.onRegister(attach(TooltipBehavior { text: MutableList<Component?> ->
        text.add(
            Component.translatable("item.gtnn.chip_t4.tooltip")
        )
    })).register()
    val INVERTER: ItemEntry<ComponentItem> = createItem(
        "inverter"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Inverter").register()
    val EncapsulatedUranium: ItemEntry<ComponentItem> = createItem(
        "encapsulated_uranium"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Encapsulated Uranium").register()
    val EnrichedUraniumNugget: ItemEntry<ComponentItem> = createItem(
        "enriched_uranium_nugget"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Enriched Uranium Nugget").register()
    val EnrichedUranium: ItemEntry<ComponentItem> = createItem(
        "enriched_uranium"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Enriched Uranium").register()
    val EncapsulatedThorium: ItemEntry<ComponentItem> = createItem(
        "encapsulated_thorium"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Encapsulated Thorium").register()
    val EnrichedThoriumNugget: ItemEntry<ComponentItem> = createItem(
        "enriched_thorium_nugget"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Enriched Thorium Nugget").register()
    val EnrichedThorium: ItemEntry<ComponentItem> = createItem(
        "enriched_thorium"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Enriched Thorium").register()
    val EncapsulatedPlutonium: ItemEntry<ComponentItem> = createItem(
        "encapsulated_plutonium"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Encapsulated Plutonium").register()
    val EnrichedPlutoniumNugget: ItemEntry<ComponentItem> = createItem(
        "enriched_plutonium_nugget"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Enriched Plutonium Nugget").register()
    val EnrichedPlutonium: ItemEntry<ComponentItem> = createItem(
        "enriched_plutonium"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Enriched Plutonium").register()
    val NeutronSource: ItemEntry<ComponentItem> = createItem(
        "neutron_source"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Neutron Source").register()
    val QuarkCore: ItemEntry<ComponentItem> = createItem(
        "quark_core"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Quark Core").register()
    val PlateRadiationProtection: ItemEntry<ComponentItem> = createItem(
        "plate_radiation_protection"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Radiation Protection Plate").register()

    val COMPUTER: ItemEntry<ComponentItem> = createItem(
        "computer_circuit"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Computer Chip").properties { p: Item.Properties ->
        p.rarity(
            Rarity.UNCOMMON
        )
    }.register()

    val COMPUTER_ADVANCED: ItemEntry<ComponentItem> = createItem(
        "computer_advanced_circuit"
    ) { properties: Item.Properties ->
        ComponentItem.create(
            properties
        )
    }.lang("Advanced Computer Chip").properties { p: Item.Properties ->
        p.rarity(
            Rarity.RARE
        )
    }.register()

    private fun attach(components: TooltipBehavior): NonNullConsumer<in ComponentItem> {
        return NonNullConsumer { item: ComponentItem ->
            item.attachComponents(
                components
            )
        }
    }

    fun init() {
    }

    private fun <T : Item?> createItem(
        name: String, factory: NonNullFunction<Item.Properties, T>
    ): ItemBuilder<T, Registrate> {
        return REGISTRATE.item(name, factory)
    }
}
