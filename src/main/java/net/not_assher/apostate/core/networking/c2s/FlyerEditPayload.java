package net.not_assher.apostate.core.networking.c2s;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModDataComponentTypes;

/**
 * @author Chemthunder
 */
public record FlyerEditPayload(ItemStack stack, String string) implements CustomPayload {
    public static final Id<FlyerEditPayload> ID = new Id<>(Apostate.id("flyer_edit"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, FlyerEditPayload> CODEC = PacketCodec.tuple(
            ItemStack.PACKET_CODEC, FlyerEditPayload::stack,
            PacketCodecs.STRING, FlyerEditPayload::string,
            FlyerEditPayload::new
    );

    public static void send(ItemStack stack, String string) {
        ClientPlayNetworking.send(new FlyerEditPayload(stack, string));
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<FlyerEditPayload> {
        public void receive(FlyerEditPayload payload, ServerPlayNetworking.Context context) {
            ServerPlayerEntity player = context.player();
//
//            context.server().execute(() -> payload.stack().set(ModDataComponentTypes.STRING, payload.string()));

            player.getStackInHand(player.getActiveHand()).set(ModDataComponentTypes.STRING, payload.string());
            player.swingHand(player.getActiveHand());
        }
    }
}
