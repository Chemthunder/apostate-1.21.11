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
        translationBuilder.add("item.apostate.bounty_poster.1", "You have failed this Bounty.");

        translationBuilder.add("advancements.apostate.root.title", "Apostate");
        translationBuilder.add("advancements.apostate.root.desc", "Have fun!");

        translationBuilder.add("advancements.apostate.place_bounty.title", "Never Forget a Grudge...");
        translationBuilder.add("advancements.apostate.place_bounty.desc", "Place a Bounty on someone's head.");

        translationBuilder.add("advancements.apostate.collect_bounty.title", "A Pretty Penny");
        translationBuilder.add("advancements.apostate.collect_bounty.desc", "Collect a Bounty.");

        translationBuilder.add("advancements.apostate.sign_contract.title", "Fettered & Chained");
        translationBuilder.add("advancements.apostate.sign_contract.desc", "Sign a Contract, doesn't this feel familiar?");

        translationBuilder.add("itemGroup.apostate", "Apostate");

        translationBuilder.add("bounty.collect", "%s has collected a Bounty on %s's head");

        translationBuilder.add("apostate.midnightconfig.title", "Apostate");
        translationBuilder.add("apostate.midnightconfig.showDisplays", "Show Bounty Poster Displays");
    }
}
