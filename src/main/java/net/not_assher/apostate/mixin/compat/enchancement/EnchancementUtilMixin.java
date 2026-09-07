package net.not_assher.apostate.mixin.compat.enchancement;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import moriyashiine.enchancement.common.util.EnchancementUtil;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = EnchancementUtil.class)
public abstract class EnchancementUtilMixin {

    @WrapMethod(method = "getTridentChargeTime")
    private static int apostate$negateTridentRebalance(Operation<Integer> original) {
        return 10;
    }
}
