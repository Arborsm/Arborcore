package dev.arbor.gtnn.mixin.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import com.lowdragmc.shimmer.forge.platform.ForgePlatformHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(ForgePlatformHelper.class)
public class ShimmerMixin {

    @Inject(method = "isDevelopmentEnvironment()Z", at = @At("HEAD"), cancellable = true, remap = false)
    private void isDevelopmentEnvironment(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
