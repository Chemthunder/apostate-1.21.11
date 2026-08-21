package net.not_assher.apostate.core.utilities;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.utilities.records.Bounty;
import org.jetbrains.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class ModUtils {
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

            if (slot.isIn(ItemTags.BUNDLES) && slot.contains(DataComponentTypes.BUNDLE_CONTENTS)) {
                BundleContentsComponent bundle = slot.get(DataComponentTypes.BUNDLE_CONTENTS);

                if (bundle != null) {
                    for (ItemStack bundleSlot : bundle.stream().toList()) {
                        if (bundleSlot.contains(ModDataComponentTypes.STORED_BOUNTY)) {
                            Bounty bounty = slot.get(ModDataComponentTypes.STORED_BOUNTY);

                            if (bounty != null) {
                                if (!bounty.completed() && bounty.signed()) {
                                    return slot;
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
