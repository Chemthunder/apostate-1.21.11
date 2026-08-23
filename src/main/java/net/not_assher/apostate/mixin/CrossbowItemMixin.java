package net.not_assher.apostate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.not_assher.apostate.core.cca.entity.LassoProjectileComponent;
import net.not_assher.apostate.core.index.ModEnchantmentEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = CrossbowItem.class)
public abstract class CrossbowItemMixin {
    @WrapOperation(
            method = "createArrowEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/RangedWeaponItem;createArrowEntity(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/projectile/ProjectileEntity;"
            )
    )
    private ProjectileEntity apostate$applyComponentIfApplicable(CrossbowItem instance, World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical, Operation<ProjectileEntity> original) {
        ProjectileEntity projectile = original.call(instance, world, shooter, weaponStack, projectileStack, critical);

        if (EnchantmentHelper.hasAnyEnchantmentsWith(weaponStack, ModEnchantmentEffects.LASSO)) {
            LassoProjectileComponent component = LassoProjectileComponent.KEY.get(projectile);

            component.setLasso(true);
        }
        return projectile;
    }
}
