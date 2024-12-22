package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.data.loader.GTOreLoader;

import dev.arbor.gtnn.GTNNIntegration;
import dev.arbor.gtnn.worldgen.GTOreVein;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GTOreLoader.class)
public class GTOreMixin {

    @Inject(method = "apply*",
            at = @At(value = "INVOKE",
                     target = "Lcom/gregtechceu/gtceu/common/data/GTOres;init()V",
                     shift = At.Shift.AFTER),
            remap = false)
    private void postInit(CallbackInfo ci) {
        if (GTNNIntegration.INSTANCE.isAdAstraLoaded()) {
            GTOreVein.INSTANCE.oreRemove();
        }
    }
}
