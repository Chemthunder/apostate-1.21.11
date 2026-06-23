package net.not_assher.apostate.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.not_assher.apostate.core.block.en.PlacedPaperBlockEntity;
import org.jspecify.annotations.Nullable;

public class PlacedPaperBlock extends BlockWithEntity {
    public IntProperty COUNT = IntProperty.of("count", 1, 4);

    public PlacedPaperBlock(Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    }

    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PlacedPaperBlock::new);
    }

    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlacedPaperBlockEntity(pos, state);
    }
}
