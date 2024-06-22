package dev.arbor.gtnn.mixin.mc;

import dev.arbor.gtnn.GTNNRegistries;

import net.minecraft.server.WorldLoader;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.List;

@Mixin(WorldLoader.PackConfig.class)
public class WorldLoaderPackConfigMixin {

    @ModifyArg(method = "*",
               at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/server/packs/resources/MultiPackResourceManager;<init>(Lnet/minecraft/server/packs/PackType;Ljava/util/List;)V"),
               index = 1)
    public List<PackResources> gtceu$injectDynamicData(PackType type, List<PackResources> packs) {
        List<PackResources> packResources = new ArrayList<>(packs);
        packResources.addAll(GTNNRegistries.getAllPackResources());
        return packResources;
    }
}
