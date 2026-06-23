package net.not_assher.apostate.core;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.not_assher.apostate.core.client.item.KillContextProperty;

/**
 * @author Chemthunder
 */
public class ApostateClient implements ClientModInitializer {
    public void onInitializeClient() {
        this.bootstrapData();
    }

    private void bootstrapData() {
        SelectProperties.ID_MAPPER.put(KillContextProperty.ID, KillContextProperty.TYPE);
    }
}
