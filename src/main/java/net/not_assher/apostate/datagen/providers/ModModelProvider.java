package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.client.item.PactCrystalProperty;
import net.not_assher.apostate.core.client.item.SignedContractProperty;
import net.not_assher.apostate.core.index.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        createContract(itemModelGenerator);
        createPactCrystal(itemModelGenerator);

        itemModelGenerator.register(ModItems.PARCHMENT, Models.GENERATED);
    }

    private void createContract(ItemModelGenerator generator) {
        Item item = ModItems.CONTRACT;
        Identifier baseId = ModelIds.getItemModelId(item);

        generator.output.accept(item,
                ItemModels.condition(
                        new SignedContractProperty(),
                        ItemModels.basic(Models.GENERATED.upload(
                                baseId.withSuffixedPath("_signed"),
                                TextureMap.layer0(baseId.withSuffixedPath("_signed")),
                                generator.modelCollector
                        )),
                        ItemModels.basic(Models.GENERATED.upload(
                                baseId,
                                TextureMap.layer0(baseId),
                                generator.modelCollector
                        ))
                )
        );
    }

    private void createPactCrystal(ItemModelGenerator generator) {
        Item item = ModItems.PACT_CRYSTAL;
        Identifier baseId = ModelIds.getItemModelId(item);

        Identifier CLEAR = Models.GENERATED.upload(
                baseId.withSuffixedPath("_clear"),
                TextureMap.layer0(baseId.withSuffixedPath("_clear")),
                generator.modelCollector
        );

        generator.output.accept(item,
                ItemModels.select(
                        new PactCrystalProperty(),
                        ItemModels.switchCase(
                                "pact_clear",
                                ItemModels.basic(Models.GENERATED.upload(
                                        baseId.withSuffixedPath("_clear"),
                                        TextureMap.layer0(baseId.withSuffixedPath("_clear")),
                                        generator.modelCollector
                                ))
                        ),
                        ItemModels.switchCase(
                                "pact_half",
                                ItemModels.basic(Models.GENERATED.upload(
                                                baseId.withSuffixedPath("_half"),
                                                TextureMap.layer0(baseId.withSuffixedPath("_half")),
                                                generator.modelCollector
                                        )
                                )
                        ),
                        ItemModels.switchCase(
                                "pact_full",
                                ItemModels.basic(Models.GENERATED.upload(
                                                baseId.withSuffixedPath("_full"),
                                                TextureMap.layer0(baseId.withSuffixedPath("_full")),
                                                generator.modelCollector
                                        )
                                )
                        )
                )
        );
    }
}