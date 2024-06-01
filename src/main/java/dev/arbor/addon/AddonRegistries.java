package dev.arbor.addon;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.resources.ResourceLocation;

public class AddonRegistries {
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(Addon.MODID);
    private static MaterialRegistry MATERIAL_REGISTRY;

    public static void registerMachine(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        // register Machines here
    }

    public static void registerMaterialRegistryEvent(MaterialRegistryEvent event) {
        MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(Addon.MODID);
    }

    public static void registerMaterials(MaterialEvent event) {
        // register Materials here
    }
}
