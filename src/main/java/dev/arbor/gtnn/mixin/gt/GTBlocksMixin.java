package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.common.data.GTBlocks;

import net.minecraft.world.level.block.Block;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.arbor.gtnn.api.block.WrappedTierType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.arbor.gtnn.api.block.BlockMaps.ALL_MACHINE_CASINGS;

@Mixin(value = GTBlocks.class, remap = false)
public abstract class GTBlocksMixin {

    @Inject(
            method = "createMachineCasingBlock(I)Lcom/tterrag/registrate/util/entry/BlockEntry;",
            at = @At(value = "TAIL"))
    private static void createMachineCasingBlock(
                                                 int tier, CallbackInfoReturnable<BlockEntry<Block>> cir) {
        var block = cir.getReturnValue();
        ALL_MACHINE_CASINGS.put(new WrappedTierType<>(block, tier), block);
    }
}
