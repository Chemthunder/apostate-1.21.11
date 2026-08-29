package net.not_assher.apostate.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

/**
 * @author Chemthunder
 */
public interface LightEmitter {
    static boolean hasEmissiveLighting(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    static int getLuminance(BlockState state) {
        return 0;
    }
}
