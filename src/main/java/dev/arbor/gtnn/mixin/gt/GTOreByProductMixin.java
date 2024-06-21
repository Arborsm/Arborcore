package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.material.ChemicalHelper;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.gregtechceu.gtceu.api.tag.TagPrefix;
import com.gregtechceu.gtceu.integration.GTOreByProduct;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.arbor.gtnn.api.recipe.OresHelper.ORE_REPLACEMENTS;

@Mixin(GTOreByProduct.class)
public abstract class GTOreByProductMixin {
    @Shadow(remap = false)
    protected abstract void addToOutputs(ItemStack stack);

    @Inject(method = "addToOutputs(Lcom/gregtechceu/gtceu/api/material/material/Material;Lcom/gregtechceu/gtceu/api/tag/TagPrefix;I)V", at = @At("HEAD"), remap = false, cancellable = true)
    private void processDirtyDust(Material material, TagPrefix prefix, int size, CallbackInfo ci) {
        if (prefix == TagPrefix.dust) {
            var replace = ORE_REPLACEMENTS.getOrDefault(material, material);
            addToOutputs(ChemicalHelper.get(prefix, replace, size));
            ci.cancel();
        }
    }
}
