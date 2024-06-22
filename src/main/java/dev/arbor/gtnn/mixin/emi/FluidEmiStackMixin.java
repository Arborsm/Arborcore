package dev.arbor.gtnn.mixin.emi;

import dev.arbor.gtnn.GTNN;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.FluidEmiStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.*;

@SuppressWarnings("all")
@Mixin(FluidEmiStack.class)
public abstract class FluidEmiStackMixin extends EmiStack {

    @Unique
    private ItemStack arborCore$stack;
    @Mutable
    @Shadow(remap = false)
    private @Final Fluid fluid;

    protected FluidEmiStackMixin(Fluid fluid) {
        this.fluid = fluid;
    }

    @Override
    public ItemStack getItemStack() {
        if (GTNN.INSTANCE.getServerConfig().makesEMIBetter) {
            arborCore$stack = fluid.getBucket().getDefaultInstance();
            arborCore$stack.setCount(1);
            return arborCore$stack;
        }
        return ItemStack.EMPTY;
    }
}
