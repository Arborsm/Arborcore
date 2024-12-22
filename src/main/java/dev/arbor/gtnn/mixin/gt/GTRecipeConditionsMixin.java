package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.common.data.GTRecipeConditions;

import dev.arbor.gtnn.data.GTNNRecipeConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GTRecipeConditions.class)
public class GTRecipeConditionsMixin {

    @Inject(method = "init", at = @At("HEAD"), remap = false)
    private static void register(CallbackInfo ci) {
        GTNNRecipeConditions.INSTANCE.init();
    }
}
