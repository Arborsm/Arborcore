package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.data.recipe.generated.OreRecipeHandler;

import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static dev.arbor.gtnn.api.recipe.OresHelper.ORE_REPLACEMENTS;

@Mixin(OreRecipeHandler.class)
public class OreRecipeHandlerMixin {

    @Redirect(
              method = "processDirtyDust",
              remap = false,
              at = @At(value = "INVOKE",
                       target = "Lcom/gregtechceu/gtceu/api/data/chemical/ChemicalHelper;get(Lcom/gregtechceu/gtceu/api/data/tag/TagPrefix;Lcom/gregtechceu/gtceu/api/data/chemical/material/Material;)Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack processDirtyDust(TagPrefix orePrefix, Material material) {
        Material replace = ORE_REPLACEMENTS.getOrDefault(material, material);
        return ChemicalHelper.get(dust, replace);
    }

    @Redirect(
              method = "processPureDust",
              remap = false,
              at = @At(value = "INVOKE",
                       target = "Lcom/gregtechceu/gtceu/api/data/chemical/ChemicalHelper;get(Lcom/gregtechceu/gtceu/api/data/tag/TagPrefix;Lcom/gregtechceu/gtceu/api/data/chemical/material/Material;)Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack processPureDust(TagPrefix orePrefix, Material material) {
        Material replace = ORE_REPLACEMENTS.getOrDefault(material, material);
        return ChemicalHelper.get(dust, replace);
    }

    @Redirect(
              method = "processCrushedCentrifuged",
              remap = false,
              at = @At(value = "INVOKE",
                       target = "Lcom/gregtechceu/gtceu/api/data/chemical/ChemicalHelper;get(Lcom/gregtechceu/gtceu/api/data/tag/TagPrefix;Lcom/gregtechceu/gtceu/api/data/chemical/material/Material;)Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack processCrushedCentrifuged(TagPrefix orePrefix, Material material) {
        Material replace = ORE_REPLACEMENTS.getOrDefault(material, material);
        return ChemicalHelper.get(dust, replace);
    }
}
