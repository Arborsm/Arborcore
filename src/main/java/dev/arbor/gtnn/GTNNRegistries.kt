package dev.arbor.gtnn

import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry
import com.gregtechceu.gtceu.api.machine.MachineDefinition
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate
import dev.arbor.gtnn.api.block.BlockMaps
import dev.arbor.gtnn.api.lang.LangGenerators
import dev.arbor.gtnn.client.ExtraHeartRenderHandler
import dev.arbor.gtnn.client.GetRecipesCommand.register
import dev.arbor.gtnn.data.GTNNMachines
import dev.arbor.gtnn.data.GTNNMaterials
import dev.arbor.gtnn.data.lang.MachineLang
import dev.arbor.gtnn.temp.VirtualEnderRegistry
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.FilePackResources
import net.minecraft.server.packs.PackResources
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.server.ServerStoppedEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import org.apache.commons.io.FileUtils
import java.io.File

@Suppress("unused")
object GTNNRegistries {
    private lateinit var MATERIAL_REGISTRY: MaterialRegistry

    val REGISTRATE: GTRegistrate by lazy {
        GTRegistrate.create(GTNN.MOD_ID).also(::addAdditionalDataGenerators)
    }

    @JvmStatic
    fun serverStopped(event: ServerStoppedEvent?) {
        VirtualEnderRegistry.release()
    }

    @JvmStatic
    fun fmlCommonSetupEvent(event: FMLCommonSetupEvent) {
        MinecraftForge.EVENT_BUS.register(ExtraHeartRenderHandler())
    }

    @JvmStatic
    fun onServerStarting(event: RegisterCommandsEvent) {
        register(event.dispatcher)
    }

    @JvmStatic
    fun registerMachines(event: GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition>) {
        GTNNMachines.init()
        GTNN.LOGGER.info("register GTNN Machines")
    }

    @JvmStatic
    fun onCommonSetup(modBus: FMLCommonSetupEvent) {
        BlockMaps.initBlocks()
    }

    @JvmStatic
    fun registerMaterialRegistryEvent(event: MaterialRegistryEvent) {
        MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(GTNN.MOD_ID)
        GTNN.LOGGER.info("register GTNN Materials")
    }

    @JvmStatic
    fun registerMaterials(event: MaterialEvent) {
        GTNNMaterials.init()
    }

    @JvmStatic
    fun getAllPackResources(): List<PackResources> {
        val packResources = ArrayList<PackResources>()
        if (GTNNIntegration.isAdAstraLoaded()) {
            this::class.java.getResourceAsStream("/data/gtnn/ad_astra.zip").use {
                val tempFile = File.createTempFile("gtnn_resource_pack", ".tmp")
                FileUtils.copyInputStreamToFile(it, tempFile)
                packResources.add(FilePackResources(tempFile.getName(), tempFile, false))
            }
        }
        return packResources
    }

    private fun addAdditionalDataGenerators(registrate: GTRegistrate) {
        LangGenerators.initDatagen(registrate, MachineLang::class)
    }
}
