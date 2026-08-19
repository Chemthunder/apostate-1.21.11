package net.not_assher.apostate.datagen.providers;

import net.acoyt.acornlib.api.util.DataUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.not_assher.apostate.core.index.data.ModDamageTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.index.data.ModEnchantments;

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
        translationBuilder.add("advancements.apostate.sign_contract.desc", "Complete a Pact Crystal, doesn't this feel familiar?");

        translationBuilder.add("advancements.apostate.use_tablet.title", "The Hunt Begins.");
        translationBuilder.add("advancements.apostate.use_tablet.desc", "Use a Divining Tablet to locate your target through several means.");

        translationBuilder.add("itemGroup.apostate", "Apostate");

        translationBuilder.add("bounty.collect", "%s has collected a Bounty on %s's head");

        translationBuilder.add("apostate.midnightconfig.title", "Apostate");
        translationBuilder.add("apostate.midnightconfig.showDisplays", "Show Bounty Poster Displays");

        DataUtils.registerDamageType(
                translationBuilder,
                ModDamageTypes.PACT,
                "%1$s signed away their soul",
                "%1$s signed away their soul whilst fighting %2$s, wielding %3$s",
                "%1$s signed away their soul whilst fighting %2$s"
        );

        DataUtils.registerEnchantment(
                translationBuilder,
                ModEnchantments.LASSO,
                "Lasso",
                "Will ensnare enemies hit by the projectile, pulling them towards the source at great speeds."
        );
    }
}
