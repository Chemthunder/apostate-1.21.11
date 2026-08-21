package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.index.tag.ModEnchantmentTags;
import net.not_assher.apostate.core.index.data.ModEnchantments;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class ModEnchantmentTagProvider extends FabricTagProvider<Enchantment> {
    public ModEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ENCHANTMENT, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries) {
        this.getTagBuilder(EnchantmentTags.NON_TREASURE)
                .add(ModEnchantments.LASSO.getValue());

        this.getTagBuilder(EnchantmentTags.IN_ENCHANTING_TABLE)
                .add(ModEnchantments.LASSO.getValue());

        this.getTagBuilder(EnchantmentTags.CROSSBOW_EXCLUSIVE_SET)
                .add(ModEnchantments.LASSO.getValue());

        this.getTagBuilder(EnchantmentTags.TRADEABLE)
                .add(ModEnchantments.LASSO.getValue());

        this.getTagBuilder(EnchantmentTags.ON_RANDOM_LOOT)
                .add(ModEnchantments.LASSO.getValue());

        this.getTagBuilder(EnchantmentTags.ON_TRADED_EQUIPMENT)
                .add(ModEnchantments.LASSO.getValue());

        this.getTagBuilder(ModEnchantmentTags.LASSO_EXCLUSIVE)
                .add(Enchantments.QUICK_CHARGE.getValue())
                .add(Enchantments.MENDING.getValue())
                .add(Enchantments.MULTISHOT.getValue())
                .add(Enchantments.PIERCING.getValue())

                .addOptional(Identifier.of("enchancement", "brimstone"))
                .addOptional(Identifier.of("enchancement", "torch"))
                .addOptional(Identifier.of("enchancement", "scatter"));
    }
}
