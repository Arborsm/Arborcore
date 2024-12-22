package dev.arbor.gtnn.mixin.mc;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;

import com.google.gson.JsonElement;
import dev.arbor.gtnn.data.recipes.OreReplace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @Shadow
    private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;

    @Inject(method = "apply*", at = @At(value = "TAIL"))
    private void adjustRecipe(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager,
                              ProfilerFiller profiler, CallbackInfo ci) {
        for (RecipeType<?> recipeType : ForgeRegistries.RECIPE_TYPES) {
            // GT recipes
            if (recipeType instanceof GTRecipeType) {
                var recipes = this.recipes.get(recipeType);
                if (recipes != null) {
                    // replace gt recipes
                    recipes.values().forEach(OreReplace::init);
                }
            }
        }
    }
}
