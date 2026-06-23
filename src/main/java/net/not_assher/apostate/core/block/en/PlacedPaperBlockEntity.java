package net.not_assher.apostate.core.block.en;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.not_assher.apostate.core.index.ModBlockEntityTypes;

public class PlacedPaperBlockEntity extends BlockEntity {
    public PlacedPaperBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.PLACED_PAPER, pos, state);
    }
}
