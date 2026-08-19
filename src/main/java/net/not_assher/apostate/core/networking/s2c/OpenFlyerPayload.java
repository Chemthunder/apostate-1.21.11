package net.not_assher.apostate.core.networking.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.client.screen.FlyerScreen;

/**
 * @author Chemthunder
 */
public record OpenFlyerPayload(ItemStack stack) implements CustomPayload {
    public static final Id<OpenFlyerPayload> ID = new Id<>(Apostate.id("open_flyer"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, OpenFlyerPayload> CODEC = PacketCodec.tuple(
            ItemStack.PACKET_CODEC, OpenFlyerPayload::stack,
            OpenFlyerPayload::new
    );

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<OpenFlyerPayload> {
        public void receive(OpenFlyerPayload payload, ClientPlayNetworking.Context context) {
            context.client().execute(() -> context.client().setScreen(new FlyerScreen(payload.stack)));
        }
    }
}
