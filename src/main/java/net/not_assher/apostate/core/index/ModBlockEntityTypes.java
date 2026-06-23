package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.BlockEntityTypeRegistrant;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.not_assher.apostate.core.block.en.PlacedPaperBlockEntity;

import static net.not_assher.apostate.core.Apostate.LOGGER;
import static net.not_assher.apostate.core.Apostate.MOD_ID;

public interface ModBlockEntityTypes {
    BlockEntityTypeRegistrant BET = new BlockEntityTypeRegistrant(MOD_ID);

    BlockEntityType<PlacedPaperBlockEntity> PLACED_PAPER = BET.register(
            "placed_paper",
            FabricBlockEntityTypeBuilder.create(
                    PlacedPaperBlockEntity::new,
                    Blocks.BARREL
            )
    );

    static void init() {
        LOGGER.info("Registered Block Entity Types");
    }
}
