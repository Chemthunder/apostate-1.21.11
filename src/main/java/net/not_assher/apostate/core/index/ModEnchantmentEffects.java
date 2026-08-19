package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.EnchantmentEffectRegistrant;
import net.minecraft.component.ComponentType;
import net.minecraft.util.Unit;
import net.not_assher.apostate.core.Apostate;

/**
 * @author Chemthunder
 */
public interface ModEnchantmentEffects {
    EnchantmentEffectRegistrant EE = new EnchantmentEffectRegistrant(Apostate.MOD_ID);

    ComponentType<Unit> LASSO = EE.register("lasso", Unit.CODEC, Unit.PACKET_CODEC);

    static void init() {
        Apostate.LOGGER.info("Registered Enchantment Effects");
    }
}
