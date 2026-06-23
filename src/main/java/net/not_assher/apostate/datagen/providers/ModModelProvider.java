package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.client.item.KillContextProperty;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.utilities.bounty.KillContext;

import java.util.ArrayList;
import java.util.List;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        createBountyPoster(itemModelGenerator);
    }

    private static void createBountyPoster(ItemModelGenerator generator) {
        Identifier baseId = Registries.ITEM.getId(ModItems.BOUNTY_POSTER);

        List<SelectItemModel.SwitchCase<KillContext>> MODELS = new ArrayList<>();

        for (KillContext ctx : KillContext.values()) {
            MODELS.add(ItemModels.switchCase(ctx, ItemModels.basic(Models.GENERATED.upload(
                    baseId.withSuffixedPath("_" + ctx.asString()),
                    TextureMap.layer0(baseId.withSuffixedPath("_" + ctx.asString())),
                    generator.modelCollector
            ))));

            MODELS.add(ItemModels.switchCase(ctx, ItemModels.basic(Models.GENERATED.upload(
                    baseId.withSuffixedPath("_" + ctx.asString() + "_complete"),
                    TextureMap.layer0(baseId.withSuffixedPath("_" + ctx.asString() + "_complete")),
                    generator.modelCollector
            ))));
        }

        generator.output.accept(ModItems.BOUNTY_POSTER, ItemModels.select(
                new KillContextProperty(),
                MODELS
        ));
    }
}


//     private static void createKnife(ItemModelGenerator generator) {
//        Identifier id = Registries.ITEM.getId(OrchardItems.KNIFE);
//
//        generator.output.accept(OrchardItems.KNIFE, ItemModels.condition(
//                ItemModels.usingItemProperty(),
//                ItemModels.basic(id.withPath(s -> "item/" + s + "_using")),
//                ItemModels.basic(id.withPath(s -> "item/" + s))
//        ));
//    }

//         Identifier id = Registries.ITEM.getId(item);
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