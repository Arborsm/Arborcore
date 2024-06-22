package dev.arbor.gtnn.mixin.gt;

import dev.arbor.gtnn.api.recipe.OresHelper;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import com.google.gson.JsonElement;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.loader.OreDataLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(OreDataLoader.class)
public class OreInitMixin {

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At(target = "Lcom/gregtechceu/gtceu/data/block/GTOres;updateLargestVeinSize()V",
                     value = "INVOKE",
                     shift = At.Shift.AFTER),
            remap = false)
    private void init(Map<ResourceLocation, JsonElement> resourceList, ResourceManager resourceManager,
                      ProfilerFiller profiler, CallbackInfo ci) {
        OresHelper.Companion.setINSTANCE(new OresHelper(GTRegistries.ORE_VEINS));
    }
}
