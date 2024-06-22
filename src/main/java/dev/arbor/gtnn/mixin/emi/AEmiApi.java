package dev.arbor.gtnn.mixin.emi;

import dev.emi.emi.api.EmiApi;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mixin(value = EmiApi.class, remap = false)
public interface AEmiApi {

    @Invoker
    static void invokeSetPages(Map<EmiRecipeCategory, List<EmiRecipe>> recipes, EmiIngredient stack) {
    }
}
