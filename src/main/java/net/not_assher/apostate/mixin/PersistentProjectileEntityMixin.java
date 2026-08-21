package net.not_assher.apostate.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.cca.entity.LassoComponent;
import net.not_assher.apostate.core.cca.entity.LassoProjectileComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(value = PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {
    @Inject(
            method = "onEntityHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/item/ItemStack;)V"
            )
    )
    private void apostate$applyData(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity entity = entityHitResult.getEntity();
        PersistentProjectileEntity self = (PersistentProjectileEntity) (Object) this;

        if (entity instanceof LivingEntity living) {
            LassoProjectileComponent component = LassoProjectileComponent.KEY.get(self);

            if (component.isLasso()) {
                Entity unused = self.getOwner();

                if (unused instanceof LivingEntity player) {
                    LassoComponent lasso = LassoComponent.KEY.get(living);

                    lasso.setDuration(200);
                    lasso.setHandler(player);
                }

                Apostate.LOGGER.info("test");
            }
        }
    }
}
