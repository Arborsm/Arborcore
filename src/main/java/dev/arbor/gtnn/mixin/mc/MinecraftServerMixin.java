package dev.arbor.gtnn.mixin.mc;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;

import dev.arbor.gtnn.GTNNRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.List;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

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
