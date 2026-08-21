package net.not_assher.apostate.core.networking.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.client.screen.BountyBookScreen;

/**
 * @author Chemthunder
 */
public record OpenBountyBookPayload(ItemStack stack) implements CustomPayload {
    public static final Id<OpenBountyBookPayload> ID = new Id<>(Apostate.id("open_bounty_book"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, OpenBountyBookPayload> CODEC = PacketCodec.tuple(
            ItemStack.PACKET_CODEC, OpenBountyBookPayload::stack,
            OpenBountyBookPayload::new
    );

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<OpenBountyBookPayload> {
        public void receive(OpenBountyBookPayload payload, ClientPlayNetworking.Context context) {
            context.client().execute(() -> context.client().setScreen(new BountyBookScreen(payload.stack)));
        }
    }
}
