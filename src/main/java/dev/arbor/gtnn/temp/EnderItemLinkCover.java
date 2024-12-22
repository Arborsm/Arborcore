package dev.arbor.gtnn.temp;

import com.gregtechceu.gtceu.api.capability.ICoverable;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.api.cover.filter.FilterHandler;
import com.gregtechceu.gtceu.api.cover.filter.FilterHandlers;
import com.gregtechceu.gtceu.api.cover.filter.ItemFilter;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.utils.GTTransferUtils;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import dev.arbor.gtnn.GTNN;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnderItemLinkCover extends AbstractEnderLinkCover<VirtualBox> {

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(EnderItemLinkCover.class,
            AbstractEnderLinkCover.MANAGED_FIELD_HOLDER);
    public static final int TRANSFER_RATE = 64; // item/s
    @Persisted
    @DescSynced
    protected final FilterHandler<ItemStack, ItemFilter> filterHandler;
    @Persisted
    @DescSynced
    protected VirtualBox virtualBox;
    private int itemsLeftToTransferLastSecond;

    public EnderItemLinkCover(CoverDefinition definition, ICoverable coverHolder, Direction attachedSide) {
        super(definition, coverHolder, attachedSide);
        this.filterHandler = FilterHandlers.item(this);
        if (!isRemote()) this.virtualBox = VirtualEnderRegistry.getInstance()
                .getOrCreateEntry(getOwner(), EntryTypes.ENDER_ITEM, this.getChannelName());
    }

    @Override
    public boolean canAttach() {
        if (!GTNN.INSTANCE.getServerConfig().isTurnOnEnderItemCover) return false;
        return this.coverHolder.getItemHandlerCap(this.attachedSide, false) != null;
    }

    @Override
    protected String identifier() {
        return "EILink#";
    }

    @Override
    protected VirtualEntry getEntry() {
        return virtualBox;
    }

    @Override
    protected void setEntry(VirtualEntry entry) {
        this.virtualBox = (VirtualBox) entry;
    }

    @Override
    protected EntryTypes<VirtualBox> getEntryType() {
        return EntryTypes.ENDER_ITEM;
    }

    @Override
    protected void transfer() {
        long timer = this.coverHolder.getOffsetTimer();
        if (itemsLeftToTransferLastSecond > 0) {
            int platformTransferredItem = doTransferItems(itemsLeftToTransferLastSecond);
            this.itemsLeftToTransferLastSecond -= platformTransferredItem;
        }

        if (timer % 20L == 0L) {
            this.itemsLeftToTransferLastSecond = TRANSFER_RATE;
        }
    }

    private int doTransferItems(int itemsLeftToTransferLastSecond) {
        var ownItemHandler = getOwnItemHandler();

        if (ownItemHandler != null && virtualBox != null) {
            return switch (io) {
                case IN -> GTTransferUtils.transferItemsFiltered(ownItemHandler, virtualBox.getItemHandler(),
                        filterHandler.getFilter(), itemsLeftToTransferLastSecond);
                case OUT -> GTTransferUtils.transferItemsFiltered(virtualBox.getItemHandler(), ownItemHandler,
                        filterHandler.getFilter(), itemsLeftToTransferLastSecond);
                default -> 0;
            };
        }
        return 0;
    }

    protected @Nullable IItemHandlerModifiable getOwnItemHandler() {
        return this.coverHolder.getItemHandlerCap(this.attachedSide, false);
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    //////////////////////////////////////
    // *********** GUI ************ //
    //////////////////////////////////////

    @Override
    protected Widget addVirtualEntryWidget(VirtualEntry entry, int x, int y, int width, int height, boolean canClick) {
        var slotWidget = new SlotWidget(((VirtualBox) entry).getItemHandler(), 0, x, y, false, false);
        slotWidget.setSize(width, height);
        return slotWidget;
    }

    @Override
    protected String getUITitle() {
        return "cover.ender_item_link.title";
    }

    @Override
    protected FilterHandler<ItemStack, ItemFilter> getFilterHandler() {
        return filterHandler;
    }
}
