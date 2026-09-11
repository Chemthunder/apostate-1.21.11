package net.not_assher.apostate.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageRecord;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.not_assher.apostate.core.cca.entity.VowbreakComponent;
import net.not_assher.apostate.core.index.data.ModDamageTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

/**
 * @author Chemthunder
 */
@Mixin(value = DamageTracker.class)
public abstract class DamageTrackerMixin {
    @Shadow @Final private LivingEntity entity;
    @Shadow @Final private List<DamageRecord> recentDamage;

    @WrapMethod(method = "getDeathMessage")
    private Text apostate$pickleStuff(Operation<Text> original) {
        LivingEntity livingEntity = this.entity;

        if (livingEntity instanceof PlayerEntity player) {
            if (VowbreakComponent.KEY.get(player).isActive()) {
                DamageRecord damageRecord = this.recentDamage.getLast();
                DamageSource damageSource = damageRecord.damageSource();

                if (damageSource.isOf(ModDamageTypes.VOWBREAK)) {
                    Text b = livingEntity.getDisplayName();

                    if (b == null) {
                        b = livingEntity.getName();
                    }
                    return b.copy().formatted(Formatting.RED).append(Text.literal(" could not keep their vows").formatted(Formatting.RED));
                }
            }
        }
        return original.call();
    }
}
