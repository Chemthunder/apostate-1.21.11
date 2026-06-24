package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.client.item.SignedContractProperty;
import net.not_assher.apostate.core.index.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        createContract(itemModelGenerator);

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
}

//     public static void createSimpleGuiVarying(ItemModelGenerator generator, Item item, Model inHandModel) {
//        Identifier id = Registries.ITEM.getId(item);
//        Identifier inHandId = id.withPath(st -> "item/" + st + "_in_hand");
//
//        generator.upload(item, Models.GENERATED);
//        inHandModel.upload(inHandId, TextureMap.layer0(inHandId), generator.modelCollector);
//        generator.output.method_75343(item,
//                ItemModels.select(
//                        new DisplayContextProperty(),
//                        ItemModels.basic(id.withPath(st -> "item/" + st + "_in_hand")),
//                        ItemModels.switchCase(
//                                Arrays.asList(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED),
//                                ItemModels.basic(id.withPath(st -> "item/" + st))
//                        )
//                )
//        );
//    }