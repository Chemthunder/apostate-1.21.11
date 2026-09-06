package net.not_assher.apostate.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;
import net.not_assher.apostate.core.client.event.BountyDisplayEvent;
import net.not_assher.apostate.core.client.event.EmeraldTabletEvent;
import net.not_assher.apostate.core.client.event.TextOverlayEvents;
import net.not_assher.apostate.core.client.event.VowbreakHudEvent;
import net.not_assher.apostate.core.client.item.CordialVowProperty;
import net.not_assher.apostate.core.client.item.DiviningTabletProperty;
import net.not_assher.apostate.core.client.item.KillContextProperty;
import net.not_assher.apostate.core.client.item.PactCrystalProperty;
import net.not_assher.apostate.core.client.tooltip.TabletTooltipComponent;
import net.not_assher.apostate.core.client.tooltip.TabletTooltipData;
import net.not_assher.apostate.core.index.ModBlockEntityTypes;
import net.not_assher.apostate.core.index.ModBlocks;
import net.not_assher.apostate.core.item.component.DiviningTabletComponent;
import net.not_assher.apostate.core.networking.ModNetworking;

import static net.not_assher.apostate.core.Apostate.id;

/**
 * @author Chemthunder
 */
public class ApostateClient implements ClientModInitializer {
    public void onInitializeClient() {
        ModBlockEntityTypes.clinit();
        ModBlocks.clinit();

        ModNetworking.s2c();

        // Models
        SelectProperties.ID_MAPPER.put(KillContextProperty.ID, KillContextProperty.TYPE);
        SelectProperties.ID_MAPPER.put(PactCrystalProperty.ID, PactCrystalProperty.TYPE);
        SelectProperties.ID_MAPPER.put(DiviningTabletProperty.ID, DiviningTabletProperty.TYPE);
        SelectProperties.ID_MAPPER.put(CordialVowProperty.ID, CordialVowProperty.TYPE);

        // Hud
        HudElementRegistry.addFirst(
                id("compass"),
                new EmeraldTabletEvent()
        );

        HudElementRegistry.addFirst(
                id("bounty_display"),
                new BountyDisplayEvent()
        );

        HudElementRegistry.addFirst(
                id("flyer"),
                new TextOverlayEvents.Render()
        );

        HudElementRegistry.addFirst(
                id("meemoo"),
                new VowbreakHudEvent()
        );

        // Events
        ClientTickEvents.START_CLIENT_TICK.register(new TextOverlayEvents.Ticker());

        TooltipComponentCallback.EVENT.register(data -> {
            if (data instanceof TabletTooltipData(ItemStack self, DiviningTabletComponent component)) {
                return new TabletTooltipComponent(self, component);
            }
            return null;
        });
    }

    public static void drawListEntry(DrawContext context, int width, int x, int y, PlayerListEntry entry, MinecraftClient client) {
        PlayerEntity playerEntity = client.world.getPlayerByUuid(entry.getProfile().id());

        if (playerEntity != null) {
            if (PlayerComponent.KEY.get(playerEntity).isLore()) {
                context.getMatrices().pushMatrix();

                context.getMatrices().translate(x + 4, y - 2);
                context.getMatrices().scale(1.2F, 1.2F);

                context.drawTexture(
                        RenderPipelines.GUI_TEXTURED,
                        Identifier.ofVanilla("textures/particle/glitter_5.png"),
                        0, 0,
                        0,
                        0,
                        5,
                        5,
                        5,
                        5,
                        0xFFfff500
                );
                context.getMatrices().popMatrix();
            }
        }
    }
}
