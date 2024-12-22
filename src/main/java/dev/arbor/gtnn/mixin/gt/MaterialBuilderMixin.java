package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import net.minecraft.resources.ResourceLocation;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Material.Builder.class, remap = false)
public abstract class MaterialBuilderMixin {

    @Unique
    private String gtnn$id;

    @Inject(method = "<init>(Lnet/minecraft/resources/ResourceLocation;)V", at = @At(value = "TAIL"))
    private void injected(ResourceLocation resourceLocation, CallbackInfo ci, @Local String id) {
        this.gtnn$id = id;
    }

    @Inject(method = "buildAndRegister", at = @At("HEAD"), cancellable = true)
    private void buildAndRegister(CallbackInfoReturnable<Material> cir) {
        if (GTCEuAPI.materialManager.getMaterial(gtnn$id) != null) {
            cir.setReturnValue(GTCEuAPI.materialManager.getMaterial(gtnn$id));
        }
    }
}
