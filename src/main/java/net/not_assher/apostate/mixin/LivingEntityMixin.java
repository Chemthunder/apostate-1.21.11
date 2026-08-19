package net.not_assher.apostate.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.not_assher.apostate.core.cca.entity.LassoComponent;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;
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

/**
 * @author Chemthunder
 */
@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "damage", at = @At(value = "TAIL"))
    private void apostate$freeFromLasso(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        LassoComponent lasso = LassoComponent.KEY.get(self);

        if (lasso.getDuration() > 0) {
            lasso.remove();
        }
    }

    @WrapMethod(method = "travel")
    private void apostate$stopMovementWhenLassoed(Vec3d movementInput, Operation<Void> original) {
        LivingEntity self = (LivingEntity) (Object) this;
        LassoComponent lasso = LassoComponent.KEY.get(self);

        if (lasso.getDuration() > 0) {
            original.call(Vec3d.ZERO);
            return;
        }
        original.call(movementInput);
    }

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

    @Inject(method = "applyMovementInput", at = @At("HEAD"), cancellable = true)
    public void mindsEye$forceZeroMovementInput(Vec3d movementInput, float slipperiness, CallbackInfoReturnable<Vec3d> cir) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (living instanceof PlayerEntity player) {
            if (PlayerComponent.KEY.get(player).isAfk()) {
                cir.setReturnValue(Vec3d.ZERO);
            }
        }
    }

    @WrapMethod(method = "damage")
    private boolean apostate$noDamageWhileAFK(ServerWorld world, DamageSource source, float amount, Operation<Boolean> original) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (living instanceof PlayerEntity player) {
            if (PlayerComponent.KEY.get(player).isAfk()) {
                return false;
            }
        }
        return original.call(world, source, amount);
    }
}
