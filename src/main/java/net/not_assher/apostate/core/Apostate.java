package net.not_assher.apostate.core;

import eu.midnightdust.lib.config.MidnightConfig;
import net.acoyt.acornlib.api.ALib;
import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.command.FlexCommand;
import net.not_assher.apostate.core.command.NicknameCommand;
import net.not_assher.apostate.core.command.StatusCommand;
import net.not_assher.apostate.core.event.ApplyApostateAdvancementEvent;
import net.not_assher.apostate.core.event.DebugRelogEvent;
import net.not_assher.apostate.core.event.LightCrimsonCandleEvent;
import net.not_assher.apostate.core.index.*;
import net.not_assher.apostate.core.item.BountyBookItem;
import net.not_assher.apostate.core.item.BountyPosterItem;
import net.not_assher.apostate.core.item.FlyerItem;
import net.not_assher.apostate.core.item.PactCrystalItem;
import net.not_assher.apostate.core.networking.ModNetworking;
import net.not_assher.apostate.core.utilities.LootTableModifiers;
import net.not_assher.apostate.ext.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chemthunder
 */
public class Apostate implements ModInitializer {
    public static final String MOD_ID = "apostate";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final int MAIN_COLOR = 0xFF621414;

    public void onInitialize() {
        LOGGER.info("Apostate Init");

        // Registries
        ModItems.init();
        ModDataComponentTypes.init();
        ModItemGroups.init();
        ModCriteria.init();
        ModEnchantmentEffects.init();
        ModBlocks.init();

        ModNetworking.init();
        ModNetworking.c2s();

        LootTableModifiers.init();

        // Events
        BetterItemTooltipEvent.EVENT.register(new BountyPosterItem.Tooltip());
        BetterItemTooltipEvent.EVENT.register(new PactCrystalItem.Tooltip());
        BetterItemTooltipEvent.EVENT.register(new FlyerItem.Tooltip());
        BetterItemTooltipEvent.EVENT.register(new BountyBookItem.Tooltip());

        CommandRegistrationCallback.EVENT.register(new NicknameCommand());
        CommandRegistrationCallback.EVENT.register(new StatusCommand());
        CommandRegistrationCallback.EVENT.register(new FlexCommand());

        ServerPlayerEvents.JOIN.register(new DebugRelogEvent());
        ServerPlayerEvents.JOIN.register(new ApplyApostateAdvancementEvent());

        UseBlockCallback.EVENT.register(new LightCrimsonCandleEvent());

        // External
        ALib.registerModMenu(MOD_ID, MAIN_COLOR);

        MidnightConfig.init(MOD_ID, ModConfig.class);
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
