package dev.arbor.gtnn.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.arbor.gtnn.GTNN;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mixin(ChatComponent.class)
public class ChatMixin {

    @Unique
    private final ArrayList<Long> arborCore$messageTimestamps = new ArrayList<>();
    @Unique
    private final float arborCore$fadeTime = 130;
    @Shadow
    private int chatScrollbarPos;
    @Shadow
    @Final
    private List<GuiMessage.Line> trimmedMessages;

    @Shadow
    private int getLineHeight() {
        return 0;
    }

    @Unique
    private int arborCore$chatLineIndex;
    @Unique
    private int arborCore$chatDisplacementY = 0;

    @Unique
    private void arborCore$calculateYOffset() {
        // Calculate current required to be offset to achieve slide in from bottom effect
        try {
            int lineHeight = this.getLineHeight();
            // scale * lineHeight
            float arborCore$fadeOffsetYScale = 0.8f;
            float maxDisplacement = (float) lineHeight * arborCore$fadeOffsetYScale;
            long timestamp = arborCore$messageTimestamps.get(arborCore$chatLineIndex);
            long timeAlive = System.currentTimeMillis() - timestamp;
            if (arborCore$chatLineIndex == 0 && timeAlive < arborCore$fadeTime && this.chatScrollbarPos == 0) {
                arborCore$chatDisplacementY = (int) (maxDisplacement -
                        ((timeAlive / arborCore$fadeTime) * maxDisplacement));
            }
        } catch (Exception ignored) {}
    }

    @Inject(method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/GuiMessage$Line;addedTime()I"))
    public void getChatLineIndex(CallbackInfo ci, @Local(ordinal = 13) int chatLineIndex) {
        // Capture which chat line is currently being rendered
        this.arborCore$chatLineIndex = chatLineIndex;
    }

    @ModifyArg(method = "render",
            index = 1,
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V",
                    ordinal = 1))
    private float applyYOffset(float y) {
        if (GTNN.INSTANCE.getClientConfig().addChatAnimation) arborCore$calculateYOffset();
        // Apply the offset
        return y + arborCore$chatDisplacementY;
    }

    @ModifyVariable(method = "render",
            ordinal = 3,
            at = @At(
                    value = "STORE"))
    private double modifyOpacity(double originalOpacity) {
        double opacity = originalOpacity;
        // Calculate current required opacity for currently rendered line to achieve fade in effect
        if (GTNN.INSTANCE.getClientConfig().addChatAnimation) {
            try {
                long timestamp = arborCore$messageTimestamps.get(arborCore$chatLineIndex);
                long timeAlive = System.currentTimeMillis() - timestamp;
                if (timeAlive < arborCore$fadeTime && this.chatScrollbarPos == 0) {
                    opacity = opacity * (0.5 + Mth.clamp(timeAlive / arborCore$fadeTime, 0, 1) / 2);
                }
            } catch (Exception ignored) {
            }
        }
        return opacity;
    }

    @ModifyVariable(method = "render",
            at = @At(
                    value = "STORE"),
            argsOnly = true)
    private GuiMessageTag removeMessageIndicator(GuiMessageTag value) {
        if (!GTNN.INSTANCE.getClientConfig().addChatAnimation) return value;
        // Don't allow the chat indicator bar to be rendered
        return null;
    }

    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V",
            at = @At("TAIL"))
    private void addMessage(Component pChatComponent, MessageSignature pHeaderSignature, GuiMessageTag pTag,
                            CallbackInfo ci) {
        if (GTNN.INSTANCE.getClientConfig().addChatAnimation) {
            arborCore$messageTimestamps.addFirst(System.currentTimeMillis());
            while (this.arborCore$messageTimestamps.size() > this.trimmedMessages.size()) {
                this.arborCore$messageTimestamps.removeLast();
            }
        }
    }
}
