package org.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import org.arbor.gtnn.data.GTNNRecipeTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GTRecipeTypes.class)
public class GTRecipeTypeMixin {
    @Inject(method = "init", at = @At("HEAD"), remap = false)
    private static void init(CallbackInfo ci){
        GTNNRecipeTypes.init();
    }
}
