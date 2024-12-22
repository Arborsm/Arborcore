package dev.arbor.gtnn.temp;

import com.lowdragmc.lowdraglib.syncdata.AccessorOp;
import com.lowdragmc.lowdraglib.syncdata.accessor.CustomObjectAccessor;
import com.lowdragmc.lowdraglib.syncdata.payload.ITypedPayload;
import com.lowdragmc.lowdraglib.syncdata.payload.NbtTagPayload;

import net.minecraft.nbt.CompoundTag;

public class VirtualBoxAccessor extends CustomObjectAccessor<VirtualBox> {

    public static final VirtualBoxAccessor INSTANCE = new VirtualBoxAccessor();

    public VirtualBoxAccessor() {
        super(VirtualBox.class, true);
    }

    @Override
    public ITypedPayload<?> serialize(AccessorOp op, VirtualBox value) {
        return NbtTagPayload.of(value.serializeNBT());
    }

    @Override
    public VirtualBox deserialize(AccessorOp op, ITypedPayload<?> payload) {
        var box = new VirtualBox();
        box.deserializeNBT((CompoundTag) payload.getPayload());
        return box;
    }
}
