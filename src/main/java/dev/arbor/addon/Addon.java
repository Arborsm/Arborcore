package dev.arbor.addon;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Addon.MODID)
public class Addon {
    public static final String MODID = "addon";

    public Addon() {
        var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        var register = Mod.EventBusSubscriber.Bus.MOD.bus().get();
        eventBus.addGenericListener(MachineDefinition.class, AddonRegistries::registerMachine);
        register.addListener(AddonRegistries::registerMaterials);
        register.addListener(AddonRegistries::registerMaterialRegistryEvent);
    }
}
