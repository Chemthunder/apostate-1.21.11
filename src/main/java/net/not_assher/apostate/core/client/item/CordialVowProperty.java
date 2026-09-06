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
import net.not_assher.apostate.core.index.ModComponentTypes;
import net.not_assher.apostate.core.item.component.CordialVowComponent;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public record CordialVowProperty() implements SelectProperty<String> {
    public static final Identifier ID = Apostate.id("cordial_vow");

    public static final Type<CordialVowProperty, String> TYPE = Type.create(
            MapCodec.unit(CordialVowProperty::new),
            Codec.STRING
    );

    public String getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        CordialVowComponent vow = stack.getOrDefault(ModComponentTypes.VOW, CordialVowComponent.EMPTY);

        if (!vow.completed()) {
            if (vow.owner().isBlank() && vow.signer().isBlank()) {
                return "empty";
            }
            if (!vow.owner().isBlank() && vow.signer().isBlank()) {
                return "half";
            }
            if (!vow.owner().isBlank() && !vow.signer().isBlank()) {
                return "full";
            }
        } else {
            return "full";
        }
        return "unknown";
    }

    public Codec<String> valueCodec() {
        return Codec.STRING;
    }

    public Type<? extends SelectProperty<String>, String> getType() {
        return TYPE;
    }
}
