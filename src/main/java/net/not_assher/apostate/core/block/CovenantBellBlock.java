package net.not_assher.apostate.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.not_assher.apostate.core.block.entity.CovenantBellBlockEntity;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class CovenantBellBlock extends BlockWithEntity {
    public CovenantBellBlock(Settings settings) {
        super(settings);
    }

    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(CovenantBellBlock::new);
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CovenantBellBlockEntity(pos, state);
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return ((world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof CovenantBellBlockEntity covenantBellBlockEntity) {
                covenantBellBlockEntity.tick(world1, pos, state1, covenantBellBlockEntity);
            }
        });
    }
}
