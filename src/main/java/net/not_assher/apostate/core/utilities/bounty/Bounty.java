package net.not_assher.apostate.core.utilities.bounty;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

/**
 * @author Chemthunder
 */
public record Bounty(String targetName, String ownerName, String targetUUID, String ownerUUID, KillContext ctx) {
    public static final Bounty EMPTY = new Bounty("", "", "", "", KillContext.EITHER);

    public static final Codec<Bounty> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.optionalFieldOf("targetName", "").forGetter(Bounty::targetName),
            Codec.STRING.optionalFieldOf("ownerName", "").forGetter(Bounty::ownerName),

            Codec.STRING.optionalFieldOf("targetUUID", "").forGetter(Bounty::targetUUID),
            Codec.STRING.optionalFieldOf("ownerUUID", "").forGetter(Bounty::ownerUUID),

            KillContext.CODEC.optionalFieldOf("killContext", KillContext.EITHER).forGetter(Bounty::ctx)
    ).apply(codec, Bounty::new));

    public static final PacketCodec<ByteBuf, Bounty> PACKET = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
