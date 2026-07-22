package net.not_assher.apostate.core.utilities.records;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

/**
 * @author Chemthunder
 */
public record Pact(String signer, String owner, boolean completed) {
    public static final Pact EMPTY = new Pact("", "", false);

    public static final Codec<Pact> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.optionalFieldOf("signer", "").forGetter(Pact::signer),
            Codec.STRING.optionalFieldOf("bearer", "").forGetter(Pact::owner),

            Codec.BOOL.optionalFieldOf("signed", false).forGetter(Pact::completed)
    ).apply(codec, Pact::new));

    public static final PacketCodec<ByteBuf, Pact> PACKET = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
