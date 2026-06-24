package net.not_assher.apostate.core;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.index.*;
import net.not_assher.apostate.core.item.BountyPosterItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Apostate implements ModInitializer {
	public static final String MOD_ID = "apostate";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public void onInitialize() {
		LOGGER.info("Apostate Registries ⭐");
        LOGGER.info("~~~~~~~~~~~~~");

        ModItems.init();
        ModDataComponentTypes.init();
        ModItemGroups.init();
        ModBlocks.init();
        ModBlockEntityTypes.init();

        LOGGER.info("Apostate Events ⭐");
        LOGGER.info("~~~~~~~~~~~~~");
        this.createTooltips();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

    public void createTooltips() {
        BetterItemTooltipEvent.EVENT.register(new BountyPosterItem.Tooltip());
    }
}
