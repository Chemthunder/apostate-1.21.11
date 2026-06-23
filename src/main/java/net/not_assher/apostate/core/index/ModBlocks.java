package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.not_assher.apostate.core.block.PlacedPaperBlock;

import static net.not_assher.apostate.core.Apostate.LOGGER;
import static net.not_assher.apostate.core.Apostate.MOD_ID;

public interface ModBlocks {
    BlockRegistrant BLOCKS = new BlockRegistrant(MOD_ID);

    Block PLACED_PAPER = BLOCKS.register("placed_paper", PlacedPaperBlock::new, AbstractBlock.Settings.copy(Blocks.LEAF_LITTER));

    static void init() {
        LOGGER.info("Registered Blocks");
    }
}
