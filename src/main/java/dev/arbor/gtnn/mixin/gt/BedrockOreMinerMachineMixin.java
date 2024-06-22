package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.BedrockOreMinerMachine;
import com.gregtechceu.gtceu.common.machine.trait.BedrockOreMinerLogic;
import dev.arbor.gtnn.GTNN;
import dev.arbor.gtnn.api.recipe.GTNNBedrockOreMinerLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedrockOreMinerMachine.class)
public class BedrockOreMinerMachineMixin {

    @Unique
    private BedrockOreMinerLogic gtnn$LOGIC;

    @Inject(method = "createRecipeLogic", at = @At("HEAD"), cancellable = true, remap = false)
    private void createRecipeLogic(Object[] args, CallbackInfoReturnable<RecipeLogic> cir) {
        if (GTNN.INSTANCE.getServerConfig().skyblock) {
            cir.setReturnValue(new GTNNBedrockOreMinerLogic<>((BedrockOreMinerMachine) (Object) this));
        }
    }

    @Inject(method = "getRecipeLogic*", at = @At("HEAD"), cancellable = true, remap = false)
    private void getRecipeLogic(CallbackInfoReturnable<RecipeLogic> cir) {
        if (GTNN.INSTANCE.getServerConfig().skyblock) {
            if (gtnn$LOGIC == null) {
                gtnn$LOGIC = new BedrockOreMinerLogic((BedrockOreMinerMachine) (Object) this);
            }
            cir.setReturnValue(gtnn$LOGIC);
        }
    }
}
