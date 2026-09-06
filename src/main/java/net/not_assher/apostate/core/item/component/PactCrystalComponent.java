package net.not_assher.apostate.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

/**
 * @author Chemthunder
 */
public record PactCrystalComponent(String signer, String owner, boolean completed) {
    public static final PactCrystalComponent EMPTY = new PactCrystalComponent("", "", false);

    public static final Codec<PactCrystalComponent> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.optionalFieldOf("signer", "").forGetter(PactCrystalComponent::signer),
            Codec.STRING.optionalFieldOf("bearer", "").forGetter(PactCrystalComponent::owner),

            Codec.BOOL.optionalFieldOf("signed", false).forGetter(PactCrystalComponent::completed)
    ).apply(codec, PactCrystalComponent::new));

    public static final PacketCodec<ByteBuf, PactCrystalComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
