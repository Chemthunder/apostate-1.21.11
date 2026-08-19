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
public record TabletComponent(@Nullable ProfileComponent hunted, ItemStack ingredient) {
    public static final Codec<TabletComponent> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            ProfileComponent.CODEC.optionalFieldOf("profile", null).forGetter(TabletComponent::hunted),
            ItemStack.CODEC.optionalFieldOf("ingredient", ItemStack.EMPTY).forGetter(TabletComponent::ingredient)
    ).apply(codec, TabletComponent::new));

    public static final PacketCodec<ByteBuf, TabletComponent> PACKET = PacketCodecs.codec(CODEC);

    public boolean shouldDisplay() {
        return hunted != null || !ingredient.isEmpty();
    }

    public boolean isEmpty() {
        return hunted == null && ingredient.isEmpty();
    }
}
