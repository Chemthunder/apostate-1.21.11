package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.loottable.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryWrapper;
import net.not_assher.apostate.core.index.ModBlocks;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generate() {
        addDrop(ModBlocks.CRIMSON_CANDLE);
        addDrop(ModBlocks.COVENANT_BELL);

        addDrop(ModBlocks.CHTHONIC_GOLD_BLOCK);
        addDrop(ModBlocks.CHISELED_CHTHONIC_GOLD_BLOCK);
        addDrop(ModBlocks.CHTHONIC_GOLD_TILES);
        addDrop(ModBlocks.CHTHONIC_GOLD_BARS);
        addDrop(ModBlocks.CHTHONIC_GOLD_CHAIN);
        addDrop(ModBlocks.CHTHONIC_GOLD_LANTERN);
        addDrop(ModBlocks.CHTHONIC_GOLD_TRAPDOOR);
        addDrop(ModBlocks.CHTHONIC_GOLD_GRATE);
        addDrop(ModBlocks.CHTHONIC_GOLD_DOOR);

        addDrop(ModBlocks.CHTHONIC_GOLD_PILE, segmentedDrops(ModBlocks.CHTHONIC_GOLD_PILE));
    }
}
