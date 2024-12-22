package dev.arbor.gtnn.temp;

import com.gregtechceu.gtceu.GTCEu;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public final class EntryTypes<T extends VirtualEntry> {

    private static final Map<ResourceLocation, EntryTypes<?>> TYPES_MAP = new Object2ObjectOpenHashMap<>();
    public static final EntryTypes<VirtualTank> ENDER_FLUID = addEntryType(GTCEu.id("ender_fluid"), VirtualTank::new);
    public static final EntryTypes<VirtualBox> ENDER_ITEM = addEntryType(GTCEu.id("ender_item"), VirtualBox::new);
    // ENDER_ENERGY("ender_energy", null),
    // ENDER_REDSTONE("ender_redstone", null);
    private final ResourceLocation location;
    private final Supplier<T> factory;

    private EntryTypes(ResourceLocation location, Supplier<T> supplier) {
        this.location = location;
        this.factory = supplier;
    }

    @Nullable
    public static EntryTypes<? extends VirtualEntry> fromString(String name) {
        return TYPES_MAP.getOrDefault(GTCEu.id(name), null);
    }

    @Nullable
    public static EntryTypes<? extends VirtualEntry> fromLocation(ResourceLocation location) {
        return TYPES_MAP.getOrDefault(location, null);
    }

    public static <E extends VirtualEntry> EntryTypes<E> addEntryType(ResourceLocation location, Supplier<E> supplier) {
        var type = new EntryTypes<>(location, supplier);
        if (!TYPES_MAP.containsKey(location)) {
            TYPES_MAP.put(location, type);
        } else {
            GTCEu.LOGGER.warn("Entry \"{}\" is already registered!", location);
        }
        return type;
    }

    public T createInstance(CompoundTag nbt) {
        var entry = createInstance();
        entry.deserializeNBT(nbt);
        return entry;
    }

    public T createInstance() {
        return factory.get();
    }

    @Override
    public String toString() {
        return this.location.toString();
    }
}
