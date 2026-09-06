package net.not_assher.apostate.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

/**
 * @author Chemthunder
 */
public record VowComponent(String signer, String owner, boolean completed) {
    public static final VowComponent EMPTY = new VowComponent("", "", false);

    public static final Codec<VowComponent> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.optionalFieldOf("signer", "").forGetter(VowComponent::signer),
            Codec.STRING.optionalFieldOf("bearer", "").forGetter(VowComponent::owner),

            Codec.BOOL.optionalFieldOf("signed", false).forGetter(VowComponent::completed)
    ).apply(codec, VowComponent::new));

    public static final PacketCodec<ByteBuf, VowComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}

