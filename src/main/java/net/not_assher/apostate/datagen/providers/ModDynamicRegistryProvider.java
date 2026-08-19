package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.not_assher.apostate.core.index.data.ModDamageTypes;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class ModDynamicRegistryProvider extends FabricDynamicRegistryProvider {
    public ModDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        ModDamageTypes.builder.addEntries(wrapperLookup, entries);

        entries.addAll(wrapperLookup.getOrThrow(RegistryKeys.ENCHANTMENT));
    }

    public String getName() {
        return "Apostate Registries";
    }
}
