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
import net.not_assher.apostate.core.utilities.records.Pact;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public record PactCrystalProperty() implements SelectProperty<String> {
    public static final Identifier ID = Apostate.id("pact_crystal");

    public static final Type<PactCrystalProperty, String> TYPE = Type.create(
            MapCodec.unit(PactCrystalProperty::new),
            Codec.STRING
    );

    public @Nullable String getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        Pact pact = stack.getOrDefault(ModDataComponentTypes.STORED_PACT, Pact.EMPTY);

        if (!pact.completed()) {
            if (pact.owner().isBlank() && pact.signer().isBlank()) {
                return "pact_clear";
            }
            if (!pact.owner().isBlank() && pact.signer().isBlank()) {
                return "pact_half";
            }
            if (!pact.owner().isBlank() && !pact.signer().isBlank()) {
                return "pact_full";
            }
        } else {
            return "pact_full";
        }
        return "pact_unknown";
    }

    public Codec<String> valueCodec() {
        return Codec.STRING;
    }

    public Type<? extends SelectProperty<String>, String> getType() {
        return TYPE;
    }
}
