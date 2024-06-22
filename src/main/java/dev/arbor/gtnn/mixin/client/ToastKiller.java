package dev.arbor.gtnn.mixin.client;

import dev.arbor.gtnn.GTNN;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
@Mixin(ToastComponent.class)
public class ToastKiller {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void killToasts(GuiGraphics p_283249_, CallbackInfo ci) {
        // Debug.Log("Killed a toast that tried to draw");
        if (GTNN.INSTANCE.getClientConfig().killToast) ci.cancel();
    }

    @Inject(method = "addToast", at = @At("HEAD"), cancellable = true)
    public void addToast(Toast pToast, CallbackInfo ci) {
        if (GTNN.INSTANCE.getClientConfig().killToast) ci.cancel();
    }

    @OnlyIn(Dist.CLIENT)
    @Mixin(targets = "net.minecraft.client.gui.components.toasts.ToastComponent$ToastInstance")
    static class ToastInstance<T extends Toast> { // inner class of ToastManager

        @Inject(method = "render", at = @At("HEAD"), cancellable = true)
        public void render(int pScreenWidth, GuiGraphics pGuiGraphics, CallbackInfoReturnable<Boolean> cir) { // lie
            // about
            // drawing
            if (GTNN.INSTANCE.getClientConfig().killToast) cir.setReturnValue(true);
        }
    }
}
