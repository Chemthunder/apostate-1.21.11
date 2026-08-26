package net.not_assher.apostate.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author Chemthunder
 */
@Mixin(value = MobEntity.class)
public abstract class MobEntityMixin {
    @Shadow private @Nullable LivingEntity target;

//    @WrapMethod(method = "getTarget")
//    private LivingEntity apostate$pacifyTarget(Operation<LivingEntity> original) {
//        MobEntity self = (MobEntity) (Object) this;
//
//        if (ModUtils.nearBlock(self.getBlockPos(), 15, self.getEntityWorld(), ModBlocks.CRIMSON_CANDLE.getDefaultState().with(Properties.LIT, true))) {
//            if (target instanceof PlayerEntity) {
//                return null;
//            }
//        }
//        return original.call();
//    }
//
//    @WrapMethod(method = "getTargetInBrain")
//    private LivingEntity apostate$pacifyTargetInBrain(Operation<LivingEntity> original) {
//        MobEntity self = (MobEntity) (Object) this;
//
//        if (ModUtils.nearBlock(self.getBlockPos(), 15, self.getEntityWorld(), ModBlocks.CRIMSON_CANDLE.getDefaultState().with(Properties.LIT, true))) {
//            if (target instanceof PlayerEntity) {
//                return null;
//            }
//        }
//        return original.call();
//    }
}
