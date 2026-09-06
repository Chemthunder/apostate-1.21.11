package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.not_assher.apostate.core.index.data.ModDamageTypes;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class ModDamageTypeTagProvider extends FabricTagProvider<DamageType> {
    public ModDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries) {
        this.getTagBuilder(DamageTypeTags.NO_KNOCKBACK)
                .add(ModDamageTypes.PACT.getValue())
                .add(ModDamageTypes.BELL.getValue())
                .add(ModDamageTypes.VOWBREAK.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .add(ModDamageTypes.VOWBREAK.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY)
                .add(ModDamageTypes.VOWBREAK.getValue());
    }
}
