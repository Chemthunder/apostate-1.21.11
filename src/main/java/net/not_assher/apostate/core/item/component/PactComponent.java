package net.not_assher.apostate.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

/**
 * @author Chemthunder
 */
public record PactComponent(String signer, String owner, boolean completed) {
    public static final PactComponent EMPTY = new PactComponent("", "", false);

    public static final Codec<PactComponent> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.optionalFieldOf("signer", "").forGetter(PactComponent::signer),
            Codec.STRING.optionalFieldOf("bearer", "").forGetter(PactComponent::owner),

            Codec.BOOL.optionalFieldOf("signed", false).forGetter(PactComponent::completed)
    ).apply(codec, PactComponent::new));

    public static final PacketCodec<ByteBuf, PactComponent> PACKET = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
