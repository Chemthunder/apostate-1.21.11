package net.not_assher.apostate.core.utilities;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.not_assher.apostate.core.index.ModCriteria;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.item.BountyPosterItem;
import net.not_assher.apostate.core.utilities.enums.KillContext;
import net.not_assher.apostate.core.utilities.records.Bounty;
import org.jetbrains.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class ModUtils {
    public static void redeemBounty(PlayerEntity player, PlayerEntity target) {
        ItemStack stack = ModUtils.checkIfBounty(player);
        World world = player.getEntityWorld();

        if (stack != null) {
            if (stack.getItem() instanceof BountyPosterItem) {
                if (bountyIsRedeemable(stack, target)) {
                    Bounty bounty = stack.get(ModDataComponentTypes.STORED_BOUNTY);

                    if (bounty != null) {
                        if (!bounty.ctx().equals(KillContext.ALIVE)) {
                            stack.set(ModDataComponentTypes.STORED_BOUNTY, new Bounty(
                                    bounty.targetName(),
                                    bounty.ownerName(),
                                    bounty.ctx(),
                                    true,
                                    false,
                                    bounty.signed()
                            ));

                            player.sendMessage(Text.literal("You have redeemed a bounty!").formatted(bounty.ctx().formatting), true);

                            if (player instanceof ServerPlayerEntity serverPlayer) {
                                ModCriteria.COLLECT_BOUNTY.trigger(serverPlayer);
                            }

                            world.playSound(
                                    null,
                                    player.getBlockPos(),
                                    SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK,
                                    SoundCategory.PLAYERS,
                                    1,
                                    1
                            );

                            world.getServer().getPlayerManager().broadcast(
                                    Text.translatable("bounty.collect", player.getNameForScoreboard(), target.getNameForScoreboard())
                                            .formatted(Formatting.YELLOW),
                                    false
                            );
                        } else {
                            stack.set(ModDataComponentTypes.STORED_BOUNTY, new Bounty(
                                    bounty.targetName(),
                                    bounty.ownerName(),
                                    bounty.ctx(),
                                    false,
                                    true,
                                    bounty.signed()
                            ));

                            player.sendMessage(Text.literal("You have failed a bounty!").formatted(Formatting.BOLD, Formatting.DARK_RED), true);

                            world.playSound(
                                    null,
                                    player.getBlockPos(),
                                    SoundEvents.ENTITY_WITHER_HURT,
                                    SoundCategory.PLAYERS,
                                    1,
                                    1
                            );
                        }
                    }
                }
            }
        }
    }

    public static boolean bountyIsRedeemable(ItemStack stack, PlayerEntity target) {
        Bounty bounty = stack.getOrDefault(ModDataComponentTypes.STORED_BOUNTY, Bounty.EMPTY);
        if (!bounty.completed() && bounty.signed()) {
            return target.getNameForScoreboard().equals(bounty.targetName());
        }
        return false;
    }

    @Nullable
    public static ItemStack checkIfBounty(PlayerEntity player) {
        for (ItemStack slot : player.getInventory()) {
            if (slot.isOf(ModItems.BOUNTY_POSTER)) {
                Bounty bounty = slot.get(ModDataComponentTypes.STORED_BOUNTY);

                if (bounty != null) {
                    if (!bounty.completed() && bounty.signed()) {
                        return slot;
                    }
                }
            }

            if (slot.contains(DataComponentTypes.BUNDLE_CONTENTS)) {
                BundleContentsComponent bundle = slot.get(DataComponentTypes.BUNDLE_CONTENTS);

                if (bundle != null) {
                    for (int i = 0; i < bundle.size(); i++) {
                        ItemStack bundleSlot = bundle.get(i);

                        if (bundleSlot.contains(ModDataComponentTypes.STORED_BOUNTY)) {
                            Bounty bounty = bundleSlot.get(ModDataComponentTypes.STORED_BOUNTY);

                            if (bounty != null) {
                                if (!bounty.completed() && bounty.signed()) {
                                    return bundleSlot;
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public static void spawnRotatedParticles(PlayerEntity player, ParticleEffect effect, int count) {
        Random random = Random.create();

        for(int i = 0; i < count; i++) {
            Vec3d vec3d = new Vec3d(((double)random.nextFloat() - (double)0.5F) * 0.1, (double)random.nextFloat() * 0.1 + 0.1, 0.0F);
            vec3d = vec3d.rotateX(-player.getPitch() * ((float)Math.PI / 180F));
            vec3d = vec3d.rotateY(-player.getYaw() * ((float)Math.PI / 180F));
            double d = (double)(-random.nextFloat()) * 0.6 - 0.3;
            Vec3d vec3d2 = new Vec3d(((double)random.nextFloat() - (double)0.5F) * 0.3, d, 0.6);
            vec3d2 = vec3d2.rotateX(-player.getPitch() * ((float)Math.PI / 180F));
            vec3d2 = vec3d2.rotateY(-player.getYaw() * ((float)Math.PI / 180F));
            vec3d2 = vec3d2.add(player.getX(), player.getEyeY(), player.getZ());

            player.getEntityWorld().addParticleClient(effect, vec3d2.x, vec3d2.y, vec3d2.z, vec3d.x, vec3d.y + 0.05, vec3d.z);
        }
    }
}
