package net.not_assher.apostate.core.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.not_assher.apostate.core.Apostate;

/**
 * @author Chemthunder
 */
public interface ModEnchantmentTags {
    TagBuilder<Enchantment> builder = new TagBuilder<>(Apostate.MOD_ID, RegistryKeys.ENCHANTMENT);

    TagKey<Enchantment> LASSO_EXCLUSIVE = builder.register("lasso_exclusive");
}
