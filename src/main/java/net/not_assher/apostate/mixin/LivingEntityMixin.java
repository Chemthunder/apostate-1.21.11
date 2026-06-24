package net.not_assher.apostate.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.item.BountyPosterItem;
import net.not_assher.apostate.core.utilities.ModUtils;
import net.not_assher.apostate.core.utilities.enums.KillContext;
import net.not_assher.apostate.core.utilities.records.Bounty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "tryUseDeathProtector", at = @At(value = "TAIL"))
    private void apostate$redeemBounty(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        Entity src = source.getAttacker();

        if (src instanceof PlayerEntity player) {
            if (self instanceof PlayerEntity target) {
                ItemStack stack = ModUtils.checkIfBounty(player);

                if (stack != null) {
                    if (stack.getItem() instanceof BountyPosterItem posterItem) {
                        if (bountyIsRedeemable(stack, target)) {
                            Bounty bounty = stack.get(ModDataComponentTypes.STORED_BOUNTY);
                            if (bounty != null) {
                                if (!bounty.ctx().equals(KillContext.ALIVE)) {
                                    posterItem.killEntity(
                                            player,
                                            target,
                                            stack,
                                            bounty
                                    );
                                } else {
                                    posterItem.failKillEntity(
                                            player,
                                            stack,
                                            bounty
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Unique
    private static boolean bountyIsRedeemable(ItemStack stack, PlayerEntity target) {
        Bounty bounty = stack.getOrDefault(ModDataComponentTypes.STORED_BOUNTY, Bounty.EMPTY);
        if (!bounty.completed() && bounty.signed()) {
            return target.getNameForScoreboard().equals(bounty.targetName());
        }
        return false;
    }
}
