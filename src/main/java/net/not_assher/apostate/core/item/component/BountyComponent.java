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
public record BountyComponent(String targetName, String ownerName, KillContext ctx, boolean completed, boolean failed, boolean signed) {
    public static final BountyComponent EMPTY = new BountyComponent("", "", KillContext.EITHER, false, false, false);

    public static final Codec<BountyComponent> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.optionalFieldOf("targetName", "").forGetter(BountyComponent::targetName),
            Codec.STRING.optionalFieldOf("ownerName", "").forGetter(BountyComponent::ownerName),

            KillContext.CODEC.optionalFieldOf("killContext", KillContext.EITHER).forGetter(BountyComponent::ctx),

            Codec.BOOL.optionalFieldOf("completed", false).forGetter(BountyComponent::completed),
            Codec.BOOL.optionalFieldOf("failed", false).forGetter(BountyComponent::failed),

            Codec.BOOL.optionalFieldOf("signed", false).forGetter(BountyComponent::signed)
    ).apply(codec, BountyComponent::new));

    public static final PacketCodec<ByteBuf, BountyComponent> PACKET = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
