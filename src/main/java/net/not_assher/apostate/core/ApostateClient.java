package net.not_assher.apostate.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.not_assher.apostate.core.client.event.BountyDisplayEvent;
import net.not_assher.apostate.core.client.item.KillContextProperty;
import net.not_assher.apostate.core.client.item.PactCrystalProperty;
import net.not_assher.apostate.core.client.item.SignedContractProperty;

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
        BooleanProperties.ID_MAPPER.put(SignedContractProperty.ID, SignedContractProperty.CODEC);
    }

    private void bootstrapEvents() {
        HudElementRegistry.addFirst(
                id("bounty_display"),
                new BountyDisplayEvent()
        );
    }
}
