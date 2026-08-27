package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.BlockEntityTypeRegistrant;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.block.entity.CovenantBellBlockEntity;

/**
 * @author Chemthunder
 */
public interface ModBlockEntityTypes {
    BlockEntityTypeRegistrant plugin = new BlockEntityTypeRegistrant(Apostate.MOD_ID);

    BlockEntityType<CovenantBellBlockEntity> COVENANT_BELL = plugin.register(
            "covenant_bell",
            FabricBlockEntityTypeBuilder.create(
                    CovenantBellBlockEntity::new,
                    ModBlocks.COVENANT_BELL
            )
    );

    static void init() {}

    static void clinit() {}
}
