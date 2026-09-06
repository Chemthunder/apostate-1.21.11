package net.not_assher.apostate.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

/**
 * @author Chemthunder
 */
public record CordialVowComponent(String signer, String owner, boolean completed) {
    public static final CordialVowComponent EMPTY = new CordialVowComponent("", "", false);

    public static final Codec<CordialVowComponent> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.optionalFieldOf("signer", "").forGetter(CordialVowComponent::signer),
            Codec.STRING.optionalFieldOf("bearer", "").forGetter(CordialVowComponent::owner),

            Codec.BOOL.optionalFieldOf("signed", false).forGetter(CordialVowComponent::completed)
    ).apply(codec, CordialVowComponent::new));

    public static final PacketCodec<ByteBuf, CordialVowComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}

