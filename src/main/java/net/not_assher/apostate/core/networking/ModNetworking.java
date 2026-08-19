package net.not_assher.apostate.core.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.not_assher.apostate.core.networking.c2s.FlyerEditPayload;
import net.not_assher.apostate.core.networking.s2c.OpenFlyerPayload;

/**
 * @author Chemthunder
 */
public interface ModNetworking {
    static void init() {
        PayloadTypeRegistry.playC2S().register(FlyerEditPayload.ID, FlyerEditPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(OpenFlyerPayload.ID, OpenFlyerPayload.CODEC);
    }

    static void c2s() {
        ServerPlayNetworking.registerGlobalReceiver(FlyerEditPayload.ID, new FlyerEditPayload.Receiver());
    }

    @Environment(EnvType.CLIENT)
    static void s2c() {
        ClientPlayNetworking.registerGlobalReceiver(OpenFlyerPayload.ID, new OpenFlyerPayload.Receiver());
    }
}
