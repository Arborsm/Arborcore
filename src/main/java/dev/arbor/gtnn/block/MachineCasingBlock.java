package dev.arbor.gtnn.block;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.arbor.gtnn.api.block.ITier;
import dev.arbor.gtnn.api.block.MachineCasingType;
import lombok.Getter;
import net.minecraft.world.level.block.Block;

import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static dev.arbor.gtnn.block.BlockTier.*;

public class MachineCasingBlock extends Block {
    public MachineCasingBlock(Properties properties) {
        super(properties);
    }

    public enum MachineCasing implements MachineCasingType {
        LV(TIER0, MACHINE_CASING_LV, "§7LV§r"),
        MV(TIER1, MACHINE_CASING_MV, "§bMV§r"),
        HV(TIER2, MACHINE_CASING_HV, "§6HV§r"),
        EV(TIER3, MACHINE_CASING_EV, "§5EV§r"),
        IV(TIER4, MACHINE_CASING_IV, "§1IV§r"),
        LuV(TIER5, MACHINE_CASING_LuV, "§dLuV§r"),
        ZPM(TIER6, MACHINE_CASING_ZPM, "§cZPM§r"),
        UV(TIER7, MACHINE_CASING_UV, "§3UV§r");

        private final ITier tier;
        private final BlockEntry<Block> machineCasing;
        @Getter
        private final String energyHatchLevel;

        MachineCasing(ITier tier, BlockEntry<Block> machineCasing, String energyHatchLevel) {
            this.tier = tier;
            this.machineCasing = machineCasing;
            this.energyHatchLevel = energyHatchLevel;
        }

        public MachineCasingType machineCasingType() {
            return this;
        }

        @Override
        public int getTier() {
            return tier.tier();
        }

        @Override
        public BlockEntry<Block> getMachineCasing() {
            return machineCasing;
        }

        public BlockEntry<Block> getMachineCasing(int tier) {
            return MachineCasing.values()[tier].getMachineCasing();
        }
    }
}
