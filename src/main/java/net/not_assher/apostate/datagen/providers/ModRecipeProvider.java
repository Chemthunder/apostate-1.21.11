package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.not_assher.apostate.core.index.ModBlocks;
import net.not_assher.apostate.core.index.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new RecipeGenerator(registries, exporter) {
            public void generate() {
                createShaped(RecipeCategory.MISC, ModItems.BOUNTY_POSTER)
                        .pattern("d")
                        .pattern("p")
                        .pattern("d")
                        .input('d', Items.YELLOW_DYE)
                        .input('p', Items.PAPER)
                        .criterion("has_paper", conditionsFromItem(Items.PAPER))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.DIVINING_TABLET)
                        .pattern("tdt")
                        .pattern("dld")
                        .pattern("tdt")
                        .input('t', Items.TUFF)
                        .input('d', ModItems.IMMORTAL_DUST)
                        .input('l', Items.LODESTONE)
                        .criterion("has_immortal_dust", conditionsFromItem(ModItems.IMMORTAL_DUST))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.FLYER)
                        .pattern("pd")
                        .pattern("d ")
                        .input('p', Items.PAPER)
                        .input('d', Items.STRING)
                        .criterion("has_paper", conditionsFromItem(Items.PAPER))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.BOUNTY_BOOK)
                        .pattern("bg")
                        .pattern("pf")
                        .input('b', Items.BOOK)
                        .input('p', ModItems.BOUNTY_POSTER)
                        .input('g', Items.GOLD_NUGGET)
                        .input('f', Items.FEATHER)
                        .criterion("has_book", conditionsFromItem(Items.BOOK))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRIMSON_CANDLE.asItem())
                        .pattern("t")
                        .pattern("c")
                        .pattern("d")
                        .input('t', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                        .input('c', ItemTags.CANDLES)
                        .input('d', Items.NETHER_WART_BLOCK)
                        .criterion("has_candle", conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);


                createShaped(RecipeCategory.MISC, Items.BELL)
                        .pattern("BGB")
                        .pattern("BGB")
                        .pattern("L L")
                        .input('B', Items.GOLD_BLOCK)
                        .input('G', Items.GOLD_INGOT)
                        .input('L', ItemTags.LOGS)
                        .criterion("has_gold", conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.CHTHONIC_GOLD_INGOT, 4)
                        .pattern(" G ")
                        .pattern("GDG")
                        .pattern(" G ")
                        .input('G', Items.GOLD_INGOT)
                        .input('D', ModItems.IMMORTAL_DUST)
                        .criterion("has_gold", conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(exporter);


                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHTHONIC_GOLD_CHAIN, 6)
                        .pattern("n")
                        .pattern("g")
                        .pattern("n")
                        .input('n', ModItems.CHTHONIC_GOLD_NUGGET)
                        .input('g', ModItems.CHTHONIC_GOLD_INGOT)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHTHONIC_GOLD_TILES, 4)
                        .pattern("gg")
                        .pattern("gg")
                        .input('g', ModItems.CHTHONIC_GOLD_INGOT)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_CHTHONIC_GOLD_BLOCK, 4)
                        .pattern("gg")
                        .pattern("gg")
                        .input('g', ModBlocks.CHTHONIC_GOLD_TILES)
                        .criterion("has_ch", conditionsFromItem(ModBlocks.CHTHONIC_GOLD_TILES.asItem()))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHTHONIC_GOLD_BARS, 16)
                        .pattern("ggg")
                        .pattern("ggg")
                        .input('g', ModItems.CHTHONIC_GOLD_INGOT)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_INGOT))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ModItems.CHTHONIC_GOLD_NUGGET, 9)
                        .input(ModItems.CHTHONIC_GOLD_INGOT)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.CHTHONIC_GOLD_INGOT, 1)
                        .pattern("ggg")
                        .pattern("ggg")
                        .pattern("ggg")
                        .input('g', ModItems.CHTHONIC_GOLD_NUGGET)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_NUGGET))
                        .offerTo(exporter, "nuggets_into_ingot");

                createShapeless(RecipeCategory.MISC, ModItems.CHTHONIC_GOLD_INGOT, 9)
                        .input(ModBlocks.CHTHONIC_GOLD_BLOCK)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_INGOT))
                        .offerTo(exporter, "gold_into_ingots");

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHTHONIC_GOLD_LANTERN, 2)
                        .pattern("ggg")
                        .pattern("gig")
                        .pattern("ggg")
                        .input('g', ModItems.CHTHONIC_GOLD_NUGGET)
                        .input('i', Items.SOUL_TORCH)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_NUGGET))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHTHONIC_GOLD_DOOR, 3)
                        .pattern("gg")
                        .pattern("gg")
                        .pattern("gg")
                        .input('g', ModItems.CHTHONIC_GOLD_INGOT)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHTHONIC_GOLD_TRAPDOOR)
                        .pattern("ggg")
                        .input('g', ModItems.CHTHONIC_GOLD_INGOT)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHTHONIC_GOLD_GRATE, 4)
                        .pattern(" g ")
                        .pattern("g g")
                        .pattern(" g ")
                        .input('g', ModItems.CHTHONIC_GOLD_INGOT)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_INGOT))
                        .offerTo(exporter);

                offerSmelting(
                        List.of(ModItems.CHTHONIC_GOLD_NUGGET),
                        RecipeCategory.MISC,
                        ModBlocks.CHTHONIC_GOLD_PILE,
                        0.25F,
                        200,
                        "chthonic_smelting"
                );

                createShaped(RecipeCategory.TOOLS, ModBlocks.COVENANT_BELL)
                        .pattern("CNC")
                        .pattern("CBC")
                        .pattern("III")
                        .input('C', ModBlocks.CHTHONIC_GOLD_BLOCK)
                        .input('B', Items.BELL)
                        .input('N', Items.NETHER_STAR)
                        .input('I', ModItems.IMMORTAL_DUST)
                        .criterion("has_ch", conditionsFromItem(Items.NETHER_STAR))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHTHONIC_GOLD_BLOCK)
                        .pattern("ggg")
                        .pattern("ggg")
                        .pattern("ggg")
                        .input('g', ModItems.CHTHONIC_GOLD_INGOT)
                        .criterion("has_ch", conditionsFromItem(ModItems.CHTHONIC_GOLD_INGOT))
                        .offerTo(exporter);
            }
        };
    }

    public String getName() {
        return "Apostate Recipes";
    }
}
