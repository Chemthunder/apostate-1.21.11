package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.not_assher.apostate.core.index.ModBlocks;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries) {
        this.valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.COVENANT_BELL, ModBlocks.CHTHONIC_GOLD_BLOCK)
                .setReplace(false);
    }
}
