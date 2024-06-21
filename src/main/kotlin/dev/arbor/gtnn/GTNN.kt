package dev.arbor.gtnn

import com.gregtechceu.gtceu.utils.FormattingUtil
import dev.arbor.gtnn.config.ConfigHandler
import dev.arbor.gtnn.init.CommonProxy
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(GTNN.MODID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object GTNN {
    const val MODID = "gtnn"
    val LOGGER: Logger by lazy { LogManager.getLogger(MODID) }

    init {
        GTNNRegistries.REGISTRATE.registerRegistrate(MOD_BUS)
    }

    fun getClientConfig(): ConfigHandler.ClientConfigs {
        return ConfigHandler.INSTANCE.Client
    }

    fun getServerConfig(): ConfigHandler.ServerConfigs {
        return ConfigHandler.INSTANCE.Server
    }

    fun id(path: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(MODID, FormattingUtil.toLowerCaseUnder(path))
    }

    @SubscribeEvent
    fun modConstruct(event: FMLConstructModEvent) {
        CommonProxy.init()
    }
}

