package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.material.material.Material;
import com.gregtechceu.gtceu.api.material.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.material.material.properties.MaterialProperties;
import com.gregtechceu.gtceu.api.material.material.properties.PropertyKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Material.class, remap = false)
public class MaterialMixin {

    @Final
    @Shadow
    private MaterialProperties properties;

    @Inject(method = "setProperty", at = @At("HEAD"), cancellable = true)
    private <T extends IMaterialProperty<T>> void setProperties(PropertyKey<T> key, IMaterialProperty<T> value,
                                                                CallbackInfo ci) {
        if (properties.hasProperty(key)) ci.cancel();
    }
}
