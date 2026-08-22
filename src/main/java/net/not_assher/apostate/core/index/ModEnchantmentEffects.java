package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.EnchantmentEffectRegistrant;
import net.minecraft.component.ComponentType;
import net.minecraft.util.Unit;
import net.not_assher.apostate.core.Apostate;

/**
 * @author Chemthunder
 */
public interface ModEnchantmentEffects {
    EnchantmentEffectRegistrant plugin = new EnchantmentEffectRegistrant(Apostate.MOD_ID);

    ComponentType<Unit> LASSO = plugin.register("lasso", Unit.CODEC, Unit.PACKET_CODEC);

    static void init() {}
}
