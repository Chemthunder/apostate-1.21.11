package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.block.*;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.block.CovenantBellBlock;
import net.not_assher.apostate.core.block.CrimsonCandleBlock;

/**
 * @author Chemthunder
 */
public interface ModBlocks {
    BlockRegistrant plugin = new BlockRegistrant(Apostate.MOD_ID);

    BlockSetType CHTHONIC = (new BlockSetType("chthonic", true, true, false, BlockSetType.ActivationRule.EVERYTHING, BlockSoundGroup.METAL, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON));

    Block COVENANT_BELL = plugin.registerWithItem("covenant_bell", CovenantBellBlock::new, AbstractBlock.Settings.copy(Blocks.BELL)
            .sounds(BlockSoundGroup.METAL)
            .luminance(CovenantBellBlock::getLuminance)
            .emissiveLighting(CovenantBellBlock::hasEmissiveLighting)
            .nonOpaque()
    );

    Block CHTHONIC_GOLD_BLOCK = plugin.registerWithItem("chthonic_gold_block", Block::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)
            .sounds(BlockSoundGroup.METAL)
    );

    Block CHISELED_CHTHONIC_GOLD_BLOCK = plugin.registerWithItem("chiseled_chthonic_gold_block", Block::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)
            .sounds(BlockSoundGroup.METAL)
    );

    Block CHTHONIC_GOLD_TILES = plugin.registerWithItem("chthonic_gold_tiles", Block::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)
            .sounds(BlockSoundGroup.METAL)
    );

    Block CHTHONIC_GOLD_GRATE = plugin.registerWithItem("chthonic_gold_grate", GrateBlock::new, AbstractBlock.Settings.copy(Blocks.COPPER_GRATE)
            .sounds(BlockSoundGroup.COPPER_GRATE)
    );

    Block CHTHONIC_GOLD_DOOR = plugin.registerWithItem("chthonic_gold_door",
            settings -> new DoorBlock(CHTHONIC, settings),
            AbstractBlock.Settings.copy(Blocks.IRON_DOOR)
    );

    Block CHTHONIC_GOLD_TRAPDOOR = plugin.registerWithItem("chthonic_gold_trapdoor",
            settings -> new TrapdoorBlock(CHTHONIC, settings),
            AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR)
    );

    Block CHTHONIC_GOLD_PILE = plugin.registerWithItem("chthonic_gold_pile", LeafLitterBlock::new, AbstractBlock.Settings.copy(Blocks.LEAF_LITTER)
            .sounds(BlockSoundGroup.CHAIN)
            .nonOpaque()
            .noCollision()
            .noCollision()
    );

    Block CHTHONIC_GOLD_CHAIN = plugin.registerWithItem("chthonic_gold_chain", ChainBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_CHAIN)
            .sounds(BlockSoundGroup.CHAIN)
    );

    Block CHTHONIC_GOLD_BARS = plugin.registerWithItem("chthonic_gold_bars", PaneBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS)
            .sounds(BlockSoundGroup.IRON)
    );

    Block CHTHONIC_GOLD_LANTERN = plugin.registerWithItem("chthonic_gold_lantern", LanternBlock::new, AbstractBlock.Settings.copy(Blocks.LANTERN)
            .sounds(BlockSoundGroup.LANTERN)
    );

    Block CRIMSON_CANDLE = plugin.registerWithItem("crimson_candle", CrimsonCandleBlock::new, AbstractBlock.Settings.copy(Blocks.CANDLE)
            .sounds(BlockSoundGroup.CANDLE)
            .emissiveLighting(CrimsonCandleBlock::hasEmissiveLighting)
            .luminance(CrimsonCandleBlock::getLuminance)
    );

    static void init() {}

    static void clinit() {
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                CHTHONIC_GOLD_PILE,
                CHTHONIC_GOLD_CHAIN,
                CHTHONIC_GOLD_DOOR,
                CHTHONIC_GOLD_TRAPDOOR,
                CHTHONIC_GOLD_GRATE,
                CHTHONIC_GOLD_BARS,
                CHTHONIC_GOLD_LANTERN,
                CRIMSON_CANDLE,
                COVENANT_BELL
        );
        BlockRenderLayerMap.putBlock(CHTHONIC_GOLD_PILE, BlockRenderLayer.TRANSLUCENT);
    }
}
