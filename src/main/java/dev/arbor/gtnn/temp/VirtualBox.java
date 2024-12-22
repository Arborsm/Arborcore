package dev.arbor.gtnn.temp;

import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Getter
@Accessors(chain = true)
public class VirtualBox extends VirtualEntry {

    protected static final String ITEM_KEY = "item";

    @NotNull
    private final CustomItemStackHandler itemHandler;

    public VirtualBox() {
        this.itemHandler = new CustomItemStackHandler();
    }

    @Override
    public EntryTypes<VirtualBox> getType() {
        return EntryTypes.ENDER_ITEM;
    }

    public void setStack(ItemStack stack) {
        itemHandler.setStackInSlot(0, stack);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VirtualBox other)) return false;
        return this.itemHandler == other.itemHandler;
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = super.serializeNBT();

        if (itemHandler.getStackInSlot(0) != ItemStack.EMPTY)
            tag.put(ITEM_KEY, itemHandler.getStackInSlot(0).save(new CompoundTag()));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        if (nbt.contains(ITEM_KEY))
            setStack(ItemStack.of(nbt.getCompound(ITEM_KEY)));
    }

    @Override
    public boolean canRemove() {
        return super.canRemove() && itemHandler.getStackInSlot(0).isEmpty();
    }
}
