package net.not_assher.apostate.core.utilities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

/**
 * @author Chemthunder
 */
public record Contract(String signer, String bearer) {
    public static final Contract EMPTY = new Contract("", "");

    public static final Codec<Contract> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.optionalFieldOf("signer", "").forGetter(Contract::signer),
            Codec.STRING.optionalFieldOf("bearer", "").forGetter(Contract::bearer)
    ).apply(codec, Contract::new));

    public static final PacketCodec<ByteBuf, Contract> PACKET = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
