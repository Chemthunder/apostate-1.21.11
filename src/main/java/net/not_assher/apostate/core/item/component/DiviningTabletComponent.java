package net.not_assher.apostate.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.jetbrains.annotations.Nullable;

/**
 * @author Chemthunder
 */
public record DiviningTabletComponent(@Nullable ProfileComponent hunted, ItemStack ingredient) {
    public static final Codec<DiviningTabletComponent> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            ProfileComponent.CODEC.optionalFieldOf("profile", null).forGetter(DiviningTabletComponent::hunted),
            ItemStack.CODEC.optionalFieldOf("ingredient", ItemStack.EMPTY).forGetter(DiviningTabletComponent::ingredient)
    ).apply(codec, DiviningTabletComponent::new));

    public static final PacketCodec<ByteBuf, DiviningTabletComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public boolean shouldDisplay() {
        return hunted != null || !ingredient.isEmpty();
    }

    public boolean isEmpty() {
        return hunted == null && ingredient.isEmpty();
    }
}
