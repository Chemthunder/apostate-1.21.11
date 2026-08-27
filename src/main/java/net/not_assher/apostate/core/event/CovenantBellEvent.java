package net.not_assher.apostate.core.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.not_assher.apostate.core.block.entity.CovenantBellBlockEntity;
import net.not_assher.apostate.core.index.ModBlocks;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.utilities.records.Pact;

import java.util.Objects;

/**
 * @author Chemthunder
 */
public class CovenantBellEvent implements UseBlockCallback {
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult blockHitResult) {
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (state.isOf(ModBlocks.COVENANT_BELL)) {
            if (world.getBlockEntity(pos) instanceof CovenantBellBlockEntity bell) {
                if (!bell.isActive()) {
                    ItemStack stack = player.getStackInHand(hand);

                    if (!stack.isEmpty()) {
                        if (bell.getPactStack() == null) {
                            if (stack.isOf(ModItems.PACT_CRYSTAL)) {
                                bell.setPactStack(stack.split(1));

                                world.playSound(
                                        null,
                                        pos.getX(),
                                        pos.getY(),
                                        pos.getZ(),
                                        SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN,
                                        SoundCategory.BLOCKS,
                                        1,
                                        1
                                );

                                return ActionResult.SUCCESS;
                            }
                        }
                    } else {
                        if (bell.getPactStack() != null && player.getActiveOrMainHandStack().isEmpty()) {
                            if (player.isSneaking()) {
                                Pact pact = bell.getPactStack().get(ModDataComponentTypes.STORED_PACT);

                                if (pact != null) {
                                    if (Objects.equals(player.getName().getString(), pact.owner())) {
                                        bell.trigger(bell);
                                    }
                                }
                            } else {
                                player.giveItemStack(bell.getPactStack());
                                bell.setPactStack(null);

                                world.playSound(
                                        null,
                                        pos.getX(),
                                        pos.getY(),
                                        pos.getZ(),
                                        SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE,
                                        SoundCategory.BLOCKS,
                                        1,
                                        1
                                );
                            }
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return ActionResult.PASS;
    }
}
