package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.data.GTCreativeModeTabs
import com.tterrag.registrate.util.entry.RegistryEntry
import dev.arbor.gtnn.GTNN.id
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import net.minecraft.world.item.CreativeModeTab

object GTNNCreativeModeTabs {
    val MAIN_TAB: RegistryEntry<CreativeModeTab, CreativeModeTab> = REGISTRATE.defaultCreativeTab(
        "main"
    ) { builder: CreativeModeTab.Builder ->
        builder.displayItems(GTCreativeModeTabs.RegistrateDisplayItemsGenerator("main", REGISTRATE))
            .title(REGISTRATE.addLang("itemGroup", id("main"), "GT--"))
            .icon { GTNNMachines.LargeNaquadahReactor.asStack() }.build()
    }.register()
}
