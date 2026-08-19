package net.not_assher.apostate.core.client.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.item.component.TabletComponent;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public record TabletProperty() implements SelectProperty<String> {
    public static final Identifier ID = Apostate.id("tablet");

    public static final Type<TabletProperty, String> TYPE = Type.create(
            MapCodec.unit(TabletProperty::new),
            Codec.STRING
    );

    public String getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        TabletComponent t = stack.getOrDefault(ModDataComponentTypes.TABLET, new TabletComponent(null, ItemStack.EMPTY));
        return t.ingredient().isEmpty() ? "tablet_empty" : "tablet_" + Registries.ITEM.getId(t.ingredient().getItem()).getPath();
    }

    public Codec<String> valueCodec() {
        return Codec.STRING;
    }

    public Type<? extends SelectProperty<String>, String> getType() {
        return TYPE;
    }
}
