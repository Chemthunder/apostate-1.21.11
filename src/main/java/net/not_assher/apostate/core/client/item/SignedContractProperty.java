package net.not_assher.apostate.core.client.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.utilities.records.Contract;
import org.jspecify.annotations.Nullable;

public record SignedContractProperty() implements BooleanProperty {
    public static final Identifier ID = Apostate.id("signed_contract");
    public static final MapCodec<SignedContractProperty> CODEC = MapCodec.unit(SignedContractProperty::new);

    public MapCodec<? extends BooleanProperty> getCodec() {
        return CODEC;
    }

    public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        return stack.getOrDefault(ModDataComponentTypes.STORED_CONTRACT, Contract.EMPTY).signed();
    }
}
