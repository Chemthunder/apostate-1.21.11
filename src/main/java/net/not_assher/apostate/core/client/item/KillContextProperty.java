package net.not_assher.apostate.core.client.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.utilities.bounty.Bounty;
import net.not_assher.apostate.core.utilities.bounty.KillContext;
import org.jspecify.annotations.Nullable;

public record KillContextProperty() implements SelectProperty<KillContext> {
    public static final Identifier ID = Apostate.id("kill_context");
    public static final Type<KillContextProperty, KillContext> TYPE = Type.create(MapCodec.unit(KillContextProperty::new), KillContext.CODEC);

    public @Nullable KillContext getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        return stack.getOrDefault(ModDataComponentTypes.STORED_BOUNTY, Bounty.EMPTY).ctx();
    }

    public Codec<KillContext> valueCodec() {
        return KillContext.CODEC;
    }

    public Type<? extends SelectProperty<KillContext>, KillContext> getType() {
        return TYPE;
    }
}
