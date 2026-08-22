package net.not_assher.apostate.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jspecify.annotations.Nullable;

import static net.minecraft.state.property.Properties.LIT;

/**
 * @author Chemthunder
 */
public class CrimsonCandleBlock extends Block {
    private static final VoxelShape SHAPE = Block.createColumnShape(12.0F, 0.0F, 16.0F);

    public static final BooleanProperty MASKED = BooleanProperty.of("masked");

    public CrimsonCandleBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false).with(MASKED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
        builder.add(MASKED);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            world.addParticleClient(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    pos.getX() + 0.5F,
                    pos.getY() + 1.3F,
                    pos.getZ() + 0.5F,
                    0,
                    0,
                    0
            );
        }
    }

    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.with(LIT, !state.get(LIT)));
        }
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public static boolean hasEmissiveLighting(BlockState state, BlockView world, BlockPos pos) {
        return !state.get(MASKED) && state.get(LIT);
    }

    public static int getLuminance(BlockState state) {
        return state.get(MASKED) ? 0 : state.get(LIT) ? 2 : 0;
    }
}
