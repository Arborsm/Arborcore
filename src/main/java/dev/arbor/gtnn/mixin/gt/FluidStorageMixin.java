package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageImpl;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKey;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = FluidStorageImpl.class, remap = false)
public class FluidStorageMixin {

    @Shadow
    private Map<FluidStorageKey, FluidBuilder> toRegister;

    @Inject(method = "enqueueRegistration", at = @At("HEAD"), cancellable = true)
    private void enqueueRegistration(FluidStorageKey key, FluidBuilder builder, CallbackInfo ci) {
        if (toRegister.containsKey(key)) ci.cancel();
    }
}
