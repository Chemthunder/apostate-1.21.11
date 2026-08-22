package net.not_assher.apostate.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.not_assher.apostate.core.index.ModBlocks;
import net.not_assher.apostate.core.utilities.ModUtils;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MobEntity.class)
public abstract class MobEntityMixin {
    @Shadow private @Nullable LivingEntity target;

    @WrapMethod(method = "getTarget")
    private LivingEntity apostate$pacifyTarget(Operation<LivingEntity> original) {
        MobEntity self = (MobEntity) (Object) this;

        if (ModUtils.nearBlock(self.getBlockPos(), 15, self.getEntityWorld(), ModBlocks.CRIMSON_CANDLE.getDefaultState().with(Properties.LIT, true))) {
            if (target instanceof PlayerEntity) {
                return null;
            }
        }
        return original.call();
    }

    @WrapMethod(method = "getTargetInBrain")
    private LivingEntity apostate$pacifyTargetInBrain(Operation<LivingEntity> original) {
        MobEntity self = (MobEntity) (Object) this;

        if (ModUtils.nearBlock(self.getBlockPos(), 15, self.getEntityWorld(), ModBlocks.CRIMSON_CANDLE.getDefaultState().with(Properties.LIT, true))) {
            if (target instanceof PlayerEntity) {
                return null;
            }
        }
        return original.call();
    }
}
