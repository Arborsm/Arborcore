package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.common.data.GTSyncedFieldAccessors;
import com.gregtechceu.gtceu.common.machine.owner.IMachineOwner;

import com.lowdragmc.lowdraglib.syncdata.TypedPayloadRegistries;
import com.lowdragmc.lowdraglib.syncdata.payload.NbtTagPayload;

import dev.arbor.gtnn.temp.MachineOwnerPayload;
import dev.arbor.gtnn.temp.VirtualBoxAccessor;
import dev.arbor.gtnn.temp.VirtualTankAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GTSyncedFieldAccessors.class)
public class GTSyncedFieldAccessorsMixin {

    @Inject(method = "init", at = @At("HEAD"), remap = false)
    private static void init(CallbackInfo ci) {
        TypedPayloadRegistries.registerSimple(MachineOwnerPayload.class, MachineOwnerPayload::new, IMachineOwner.class,
                1);
        TypedPayloadRegistries.register(NbtTagPayload.class, NbtTagPayload::new, VirtualTankAccessor.INSTANCE, 2);
        TypedPayloadRegistries.register(NbtTagPayload.class, NbtTagPayload::new, VirtualBoxAccessor.INSTANCE, 2);
    }
}
