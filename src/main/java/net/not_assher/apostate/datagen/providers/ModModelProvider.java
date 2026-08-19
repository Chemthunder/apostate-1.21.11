package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.property.select.DisplayContextProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.client.item.PactCrystalProperty;
import net.not_assher.apostate.core.client.item.TabletProperty;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.index.ModModels;

import java.util.Arrays;

/**
 * @author Chemthunder
 */
public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        createTablet(itemModelGenerator);
        createPactCrystal(itemModelGenerator);
        createFlyer(itemModelGenerator);

        itemModelGenerator.register(ModItems.IMMORTAL_DUST, Models.GENERATED);
    }

    private void createFlyer(ItemModelGenerator generator) {
        Item item = ModItems.FLYER;
        Identifier baseId = ModelIds.getItemModelId(item);

        generator.output.accept(item,
                ItemModels.select(
                        new DisplayContextProperty(),
                        ItemModels.basic(
                                Models.GENERATED.upload(
                                        baseId,
                                        TextureMap.layer0(baseId),
                                        generator.modelCollector
                                )
                        ),
                        ItemModels.switchCase(
                                Arrays.asList(
                                        ItemDisplayContext.ON_SHELF,
                                        ItemDisplayContext.FIXED
                                ),
                                ItemModels.basic(
                                        ModModels.DISPLAYED.upload(
                                                baseId.withSuffixedPath("_display"),
                                                TextureMap.layer0(baseId.withSuffixedPath("_display")),
                                                generator.modelCollector
                                        )
                                )
                        )
                ));
    }

    private void createTablet(ItemModelGenerator generator) {
        Item item = ModItems.DIVINING_TABLET;
        Identifier baseId = ModelIds.getItemModelId(item);

        generator.output.accept(item,
                ItemModels.select(
                        new TabletProperty(),
                        ItemModels.switchCase(
                                "tablet_empty",
                                ItemModels.basic(
                                        Models.GENERATED.upload(
                                                baseId.withSuffixedPath("_empty"),
                                                TextureMap.layer0(baseId.withSuffixedPath("_empty")),
                                                generator.modelCollector
                                        )
                                )
                        ),
                        ItemModels.switchCase(
                                "tablet" + fetchItem(Items.AMETHYST_SHARD),
                                ItemModels.basic(
                                        Models.GENERATED.upload(
                                                baseId.withSuffixedPath(fetchItem(Items.AMETHYST_SHARD)),
                                                TextureMap.layer0(baseId.withSuffixedPath(fetchItem(Items.AMETHYST_SHARD))),
                                                generator.modelCollector
                                        )
                                )
                        ),
                        ItemModels.switchCase(
                                "tablet" + fetchItem(Items.EMERALD),
                                ItemModels.basic(
                                        Models.GENERATED.upload(
                                                baseId.withSuffixedPath(fetchItem(Items.EMERALD)),
                                                TextureMap.layer0(baseId.withSuffixedPath(fetchItem(Items.EMERALD))),
                                                generator.modelCollector
                                        )
                                )
                        ),
                        ItemModels.switchCase(
                                "tablet" + fetchItem(Items.ECHO_SHARD),
                                ItemModels.basic(
                                        Models.GENERATED.upload(
                                                baseId.withSuffixedPath(fetchItem(Items.ECHO_SHARD)),
                                                TextureMap.layer0(baseId.withSuffixedPath(fetchItem(Items.ECHO_SHARD))),
                                                generator.modelCollector
                                        )
                                )
                        )
                ));
    }

    private void createPactCrystal(ItemModelGenerator generator) {
        Item item = ModItems.PACT_CRYSTAL;
        Identifier baseId = ModelIds.getItemModelId(item);

        generator.output.accept(item,
                ItemModels.select(
                        new PactCrystalProperty(),
                        ItemModels.switchCase(
                                "pact_clear",
                                ItemModels.basic(
                                        Models.GENERATED.upload(
                                                baseId.withSuffixedPath("_clear"),
                                                TextureMap.layer0(baseId.withSuffixedPath("_clear")),
                                                generator.modelCollector
                                        )
                                )
                        ),
                        ItemModels.switchCase(
                                "pact_half",
                                ItemModels.basic(
                                        Models.GENERATED.upload(
                                                baseId.withSuffixedPath("_half"),
                                                TextureMap.layer0(baseId.withSuffixedPath("_half")),
                                                generator.modelCollector
                                        )
                                )
                        ),
                        ItemModels.switchCase(
                                "pact_full",
                                ItemModels.basic(
                                        Models.GENERATED.upload(
                                                baseId.withSuffixedPath("_full"),
                                                TextureMap.layer0(baseId.withSuffixedPath("_full")),
                                                generator.modelCollector
                                        )
                                )
                        )
                )
        );
    }

    private static String fetchItem(Item item) {
        return "_" + Registries.ITEM.getId(item).getPath();
    }
}