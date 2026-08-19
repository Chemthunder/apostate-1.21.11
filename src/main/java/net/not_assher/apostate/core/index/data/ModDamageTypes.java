package net.not_assher.apostate.core.index.data;

import net.acoyt.acornlib.api.builder.KeyedBuilder;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.not_assher.apostate.core.Apostate;

/**
 * @author Chemthunder
 */
public interface ModDamageTypes {
    KeyedBuilder<DamageType> builder = new KeyedBuilder<>(Apostate.MOD_ID, RegistryKeys.DAMAGE_TYPE);

    RegistryKey<DamageType> PACT = builder.register("pact", new DamageType("pact", 0.0F));
}
