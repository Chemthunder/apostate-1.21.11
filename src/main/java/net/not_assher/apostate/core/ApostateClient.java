package net.not_assher.apostate.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;
import net.not_assher.apostate.core.client.event.BountyDisplayEvent;
import net.not_assher.apostate.core.client.item.KillContextProperty;
import net.not_assher.apostate.core.client.item.PactCrystalProperty;

import static net.not_assher.apostate.core.Apostate.id;

/**
 * @author Chemthunder
 */
public class ApostateClient implements ClientModInitializer {
    public void onInitializeClient() {
        this.bootstrapData();
        this.bootstrapEvents();
    }

    private void bootstrapData() {
        SelectProperties.ID_MAPPER.put(KillContextProperty.ID, KillContextProperty.TYPE);
        SelectProperties.ID_MAPPER.put(PactCrystalProperty.ID, PactCrystalProperty.TYPE);
    }

    private void bootstrapEvents() {
        HudElementRegistry.addFirst(
                id("bounty_display"),
                new BountyDisplayEvent()
        );
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
