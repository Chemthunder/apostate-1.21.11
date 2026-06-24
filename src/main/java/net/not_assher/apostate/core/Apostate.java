package net.not_assher.apostate.core;

import eu.midnightdust.lib.config.MidnightConfig;
import net.acoyt.acornlib.api.ALib;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.cmnd.NicknameCommand;
import net.not_assher.apostate.core.event.ApplyApostateAdvancementEvent;
import net.not_assher.apostate.core.index.ModCriteria;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItemGroups;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.item.BountyPosterItem;
import net.not_assher.apostate.core.item.ContractItem;
import net.not_assher.apostate.ext.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Apostate implements ModInitializer {
	public static final String MOD_ID = "apostate";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final int MAIN_COLOR = 0xFF621414;

	public void onInitialize() {
		LOGGER.info("Apostate Init ⭐");
        LOGGER.info("~~~~~~~~~~~~~");

        ModItems.init();
        ModDataComponentTypes.init();
        ModItemGroups.init();
        ModCriteria.init();

        this.createTooltips();
        this.createEvents();

        this.bootstrapEvents();
        this.bootstrapExternal();

        LOGGER.info("~~~~~~~~~~~~~");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

    private void createTooltips() {
        BountyPosterItem.Tooltip.create();
        ContractItem.Tooltip.create();
    }

    private void createEvents() {
        NicknameCommand.create();
    }

    private void bootstrapEvents() {
        ServerPlayerEvents.JOIN.register(new ApplyApostateAdvancementEvent());
    }

    private void bootstrapExternal() {
        ALib.registerModMenu(MOD_ID, MAIN_COLOR);

        MidnightConfig.init(MOD_ID, ModConfig.class);
    }
}
