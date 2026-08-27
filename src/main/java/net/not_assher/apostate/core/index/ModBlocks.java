package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.block.CovenantBellBlock;
import net.not_assher.apostate.core.block.CrimsonCandleBlock;

/**
 * @author Chemthunder
 */
public interface ModBlocks {
    BlockRegistrant plugin = new BlockRegistrant(Apostate.MOD_ID);

    Block CRIMSON_CANDLE = plugin.registerWithItem("crimson_candle", CrimsonCandleBlock::new, AbstractBlock.Settings.copy(Blocks.CANDLE)
            .sounds(BlockSoundGroup.CANDLE)
            .emissiveLighting(CrimsonCandleBlock::hasEmissiveLighting)
            .luminance(CrimsonCandleBlock::getLuminance)
    );

    Block COVENANT_BELL = plugin.registerWithItem("covenant_bell", CovenantBellBlock::new, AbstractBlock.Settings.copy(Blocks.BELL)
            .sounds(BlockSoundGroup.METAL)
    );

    static void init() {}
}
