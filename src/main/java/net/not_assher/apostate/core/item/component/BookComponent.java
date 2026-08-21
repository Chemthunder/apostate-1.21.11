package net.not_assher.apostate.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public record BookComponent(List<ItemStack> posters) {
    public static final Codec<BookComponent> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            ItemStack.CODEC.listOf().optionalFieldOf("posters", new ArrayList<>()).forGetter(BookComponent::posters)
    ).apply(codec, BookComponent::new));

    public static final PacketCodec<ByteBuf, BookComponent> PACKET = PacketCodecs.codec(CODEC);
}
