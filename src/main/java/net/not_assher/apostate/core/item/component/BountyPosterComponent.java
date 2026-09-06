package net.not_assher.apostate.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.not_assher.apostate.core.utilities.enums.KillContext;

/**
 * @author Chemthunder
 */
public record BountyPosterComponent(String targetName, String ownerName, KillContext ctx, boolean completed, boolean failed, boolean signed) {
    public static final BountyPosterComponent EMPTY = new BountyPosterComponent("", "", KillContext.EITHER, false, false, false);

    public static final Codec<BountyPosterComponent> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.optionalFieldOf("targetName", "").forGetter(BountyPosterComponent::targetName),
            Codec.STRING.optionalFieldOf("ownerName", "").forGetter(BountyPosterComponent::ownerName),

            KillContext.CODEC.optionalFieldOf("killContext", KillContext.EITHER).forGetter(BountyPosterComponent::ctx),

            Codec.BOOL.optionalFieldOf("completed", false).forGetter(BountyPosterComponent::completed),
            Codec.BOOL.optionalFieldOf("failed", false).forGetter(BountyPosterComponent::failed),

            Codec.BOOL.optionalFieldOf("signed", false).forGetter(BountyPosterComponent::signed)
    ).apply(codec, BountyPosterComponent::new));

    public static final PacketCodec<ByteBuf, BountyPosterComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
