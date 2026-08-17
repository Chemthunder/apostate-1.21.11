package net.not_assher.apostate.core.utilities;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.index.ModItems;

/**
 * @author Chemthunder
 */
public class LootTableModifiers implements LootTableEvents.Modify {
    private static final Identifier VEX_ID = Identifier.ofVanilla("entities/vex");

    public static void init() {
        LootTableEvents.MODIFY.register(new LootTableModifiers());
    }

    public void modifyLootTable(RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
        if (VEX_ID.equals(key.getValue())) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(UniformLootNumberProvider.create(1.0F, 1.0F))
                    .conditionally(RandomChanceLootCondition.builder(0.5F))
                    .with(ItemEntry.builder(ModItems.PACT_CRYSTAL));

            tableBuilder.pool(poolBuilder);
        }
    }
}
