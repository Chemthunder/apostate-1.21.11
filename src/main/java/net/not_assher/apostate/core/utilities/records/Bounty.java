package net.not_assher.apostate.core.utilities.records;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.not_assher.apostate.core.utilities.enums.KillContext;

/**
 * @author Chemthunder
 */
public record Bounty(String targetName, String ownerName, KillContext ctx, boolean completed, boolean failed, boolean signed) {
    public static final Bounty EMPTY = new Bounty("", "", KillContext.EITHER, false, false, false);

    public static final Codec<Bounty> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.optionalFieldOf("targetName", "").forGetter(Bounty::targetName),
            Codec.STRING.optionalFieldOf("ownerName", "").forGetter(Bounty::ownerName),

            KillContext.CODEC.optionalFieldOf("killContext", KillContext.EITHER).forGetter(Bounty::ctx),

            Codec.BOOL.optionalFieldOf("completed", false).forGetter(Bounty::completed),
            Codec.BOOL.optionalFieldOf("failed", false).forGetter(Bounty::failed),

            Codec.BOOL.optionalFieldOf("signed", false).forGetter(Bounty::signed)
    ).apply(codec, Bounty::new));

    public static final PacketCodec<ByteBuf, Bounty> PACKET = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
