package net.not_assher.apostate.core.event.block;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.not_assher.apostate.core.block.entity.CovenantBellBlockEntity;
import net.not_assher.apostate.core.index.ModBlocks;
import net.not_assher.apostate.core.index.ModCriteria;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.index.data.ModDamageTypes;
import net.not_assher.apostate.core.item.component.PactComponent;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * @author Chemthunder
 */
public class CovenantBellEvents {
    public static class AfterBroken implements PlayerBlockBreakEvents.After {
        public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
            if (state.isOf(ModBlocks.COVENANT_BELL)) {
                if (blockEntity instanceof CovenantBellBlockEntity bell) {
                    if (bell.getPactStack() != null) {
                        ItemScatterer.spawn(world, pos.toCenterPos().x, pos.toCenterPos().y, pos.toCenterPos().z, bell.getPactStack());
                    }
                }
            }
        }
    }

    public static class UseBlock implements UseBlockCallback {
        public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            ItemStack stack = player.getStackInHand(hand);

            if (state.isOf(ModBlocks.COVENANT_BELL)) {
                if (world.getBlockEntity(pos) instanceof CovenantBellBlockEntity bell) {
                    if (!bell.isActive()) {
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
                                    PactComponent pact = bell.getPactStack().get(ModDataComponentTypes.STORED_PACT);

                                    if (pact != null) {
                                        if (Objects.equals(player.getName().getString(), pact.owner())) {
                                            bell.trigger(bell);

                                            if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                                                ModCriteria.COVENANT_BELL.trigger(serverPlayerEntity);

                                                serverPlayerEntity.damage(serverPlayerEntity.getEntityWorld(), serverPlayerEntity.getDamageSources().create(ModDamageTypes.BELL), player.getHealth() / 2);
                                            }
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
                    } else {
                        if (stack.isEmpty() && bell.getPactStack() == null) {
                            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BELL_USE, SoundCategory.BLOCKS);
                        }
                    }
                }
            }
            return ActionResult.PASS;
        }
    }
}
