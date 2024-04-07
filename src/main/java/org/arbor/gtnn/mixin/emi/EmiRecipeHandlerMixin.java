package org.arbor.gtnn.mixin.emi;

import com.lowdragmc.lowdraglib.gui.modular.ModularUIContainer;
import dev.emi.emi.api.recipe.handler.EmiRecipeHandler;
import dev.emi.emi.registry.EmiRecipeFiller;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import org.arbor.gtnn.GTNN;
import org.arbor.gtnn.emi.recipe.NGTEmiRecipeHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Deprecated(forRemoval = true, since = "1.1.0")
@Mixin(EmiRecipeFiller.class)
public class EmiRecipeHandlerMixin {
    @SuppressWarnings("unchecked")
    @Inject(method = "getAllHandlers", at = @At("HEAD"), cancellable = true, remap = false)
    private static <T extends AbstractContainerMenu> void AddGTEmiRecipeHandler(AbstractContainerScreen<T> screen, CallbackInfoReturnable<List<EmiRecipeHandler<T>>> cir) {
        if (GTNN.getClientConfig().enableRemakeGTMEMI && screen != null && !(screen.getMenu() instanceof InventoryMenu)) {
            if (screen.getMenu() instanceof ModularUIContainer) {
                cir.setReturnValue((List<EmiRecipeHandler<T>>) (List<?>) List.of(new NGTEmiRecipeHandler()));
            }
        }
    }
}
