package dev.arbor.gtnn

import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.material.material.event.MaterialEvent
import com.gregtechceu.gtceu.api.material.material.event.MaterialRegistryEvent
import com.gregtechceu.gtceu.api.material.material.registry.MaterialRegistry
import com.gregtechceu.gtceu.api.registry.GTRegistries
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate
import dev.arbor.gtnn.api.lang.LangGenerators
import dev.arbor.gtnn.data.GTNNMachines
import dev.arbor.gtnn.data.GTNNMaterials
import dev.arbor.gtnn.data.GTNNRecipesConditions
import dev.arbor.gtnn.data.lang.MachineLang
import net.minecraft.network.chat.Component
import net.minecraft.server.packs.FilePackResources
import net.minecraft.server.packs.PackLocationInfo
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.repository.PackSource
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.util.*

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object GTNNRegistries {
    private lateinit var MATERIAL_REGISTRY: MaterialRegistry
    val REGISTRATE: GTRegistrate by lazy {
        GTRegistrate.create(GTNN.MODID).also(::addAdditionalDataGenerators)
    }

    @SubscribeEvent
    fun registerMaterialRegistryEvent(@Suppress("UNUSED_PARAMETER") event: MaterialRegistryEvent) {
        MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(GTNN.MODID)
        GTNN.LOGGER.info("register GTNN Materials")
    }

    @SubscribeEvent
    fun registerMaterials(@Suppress("UNUSED_PARAMETER") event: MaterialEvent) {
        GTNNMaterials.init()
    }

    @SubscribeEvent
    fun registerRecipeCondition(event: GTCEuAPI.RegisterEvent<*, *>) {
        if (event.registry == GTRegistries.MACHINES) {
            GTNNMachines.init()
        } else if (event.registry == GTRegistries.RECIPE_CONDITIONS) {
            GTNNRecipesConditions.init()
        }
    }

    @JvmStatic
    fun getAllPackResources(): List<PackResources> {
        val packResources = ArrayList<PackResources>()
        if (GTNNIntegration.isAdAstraLoaded()) {
            val inputStream = GTNNRegistries::class.java.getResourceAsStream("/data/gtnn/ad_astra.zip")!!
            try {
                val tempFile = File.createTempFile("temp", ".tmp")
                FileUtils.copyInputStreamToFile(inputStream, tempFile)
                inputStream.close()
                val temp = FilePackResources.SharedZipFileAccess(tempFile)
                val pLocation =
                    PackLocationInfo(tempFile.getName(), Component.empty(), PackSource.BUILT_IN, Optional.empty())
                packResources.add(FilePackResources(pLocation, temp, ""))
            } catch (e: IOException) {
                GTNN.LOGGER.error("ad_astra.zip wrong!", e)
            }
        }
        return packResources
    }

    private fun addAdditionalDataGenerators(registrate: GTRegistrate) {
        LangGenerators.initDatagen(registrate, MachineLang::class)
    }
}
