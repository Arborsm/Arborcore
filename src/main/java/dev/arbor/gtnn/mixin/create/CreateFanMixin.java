package dev.arbor.gtnn.mixin.create;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import dev.arbor.gtnn.GTNN;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(AllFanProcessingTypes.BlastingType.class)
public class CreateFanMixin {

    @Inject(method = "process(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;)Ljava/util/List;",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;",
                     ordinal = 1),
            cancellable = true)
    public void cancelBlastingRecipe(ItemStack stack, Level level, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (GTNN.INSTANCE.getServerConfig().banCreateFanBlasting)
            cir.setReturnValue(Collections.emptyList()); // cancel recipe
    }
}
