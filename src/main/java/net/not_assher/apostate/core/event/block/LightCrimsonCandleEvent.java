package net.not_assher.apostate.core.event.block;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.not_assher.apostate.core.block.CrimsonCandleBlock;
import net.not_assher.apostate.core.index.ModBlocks;
import net.not_assher.apostate.core.index.ModCriteria;
import net.not_assher.apostate.core.utilities.ModUtils;

/**
 * @author Chemthunder
 */
public class LightCrimsonCandleEvent implements UseBlockCallback {
    public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = playerEntity.getStackInHand(hand);

        if (state.isOf(ModBlocks.CRIMSON_CANDLE)) {
            if (ModUtils.stackCreatesFire(stack)) {
                if (!state.get(Properties.LIT)) {
                    world.setBlockState(pos, state.with(Properties.LIT, true));
                    world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1, 1);

                    if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
                        ModCriteria.CRIMSON_CANDLE.trigger(serverPlayer);
                    }
                    return ActionResult.SUCCESS;
                }
            }

            if (stack.isOf(Items.PAPER)) {
                world.setBlockState(pos, state.with(CrimsonCandleBlock.MASKED, !state.get(CrimsonCandleBlock.MASKED)));
                world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_BREAK, SoundCategory.BLOCKS, 1, 1);

                return ActionResult.SUCCESS;
            }

            if (stack.isEmpty()) {
                if (state.get(Properties.LIT)) {
                    world.setBlockState(pos, state.with(Properties.LIT, false));
                    world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }
}
