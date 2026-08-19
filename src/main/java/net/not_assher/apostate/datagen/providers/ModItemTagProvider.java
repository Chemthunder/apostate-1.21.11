package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.index.data.ModItemTags;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries) {
        this.valueLookupBuilder(ModItemTags.ACCEPTABLE)
                .add(Items.AMETHYST_SHARD)
                .add(Items.EMERALD)
                .add(Items.ECHO_SHARD)
                .setReplace(false);

        this.valueLookupBuilder(ModItemTags.HIDES_FRAME)
                .add(ModItems.BOUNTY_POSTER)
                .add(ModItems.FLYER)
                .setReplace(false);
    }
}
