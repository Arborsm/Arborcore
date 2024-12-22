package dev.arbor.gtnn;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GTNN.MOD_ID)
public class GTNNForge {

    public GTNNForge() {
        var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        var register = Mod.EventBusSubscriber.Bus.MOD.bus().get();
        var events = MinecraftForge.EVENT_BUS;
        GTNN.INSTANCE.init();
        GTNN.genericListener(eventBus);
        GTNN.register(register);
        GTNN.eventRegister(events);
    }
}
