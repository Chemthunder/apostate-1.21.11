package net.not_assher.apostate.core.index.data;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Unit;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModEnchantmentEffects;

/**
 * @author Chemthunder
 */
public interface ModEnchantments {
    RegistryKey<Enchantment> LASSO = create("lasso");

    private static RegistryKey<Enchantment> create(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Apostate.id(id));
    }

    static void bootstrap(Registerable<Enchantment> registerable) {
        RegistryEntryLookup<Item> itemLookup = registerable.getRegistryLookup(RegistryKeys.ITEM);
        RegistryEntryLookup<Enchantment> enchantmentLookup = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);

        registerable.register(LASSO, Enchantment.builder(Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.leveledCost(5, 0),
                                Enchantment.leveledCost(17, 0),
                                7,
                                AttributeModifierSlot.MAINHAND
                        ))
                        .addNonListEffect(ModEnchantmentEffects.LASSO, Unit.INSTANCE)
                        .exclusiveSet(enchantmentLookup.getOrThrow(ModEnchantmentTags.LASSO_EXCLUSIVE))
                        .build(LASSO.getValue())
        );
    }
}
