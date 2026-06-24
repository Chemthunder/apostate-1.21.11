package net.not_assher.apostate.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.not_assher.apostate.datagen.providers.ModAdvancementProvider;
import net.not_assher.apostate.datagen.providers.ModLanguageProvider;
import net.not_assher.apostate.datagen.providers.ModModelProvider;
import net.not_assher.apostate.datagen.providers.ModRecipeProvider;

public class ApostateDataGenerator implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fdg) {
        var pack = fdg.createPack();

        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModLanguageProvider::new);

        pack.addProvider(ModAdvancementProvider::new);
        pack.addProvider(ModRecipeProvider::new);
	}
}
