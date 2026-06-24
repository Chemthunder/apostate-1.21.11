package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.not_assher.apostate.core.index.ModItems;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        ModItems.ITEMS.registerLang(wrapperLookup, translationBuilder);

        translationBuilder.add("item.apostate.bounty_poster.0", "%s is wanted by %s, ");
    }
}
