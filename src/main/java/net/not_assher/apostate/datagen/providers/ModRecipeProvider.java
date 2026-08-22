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
            }
        };
    }

    public String getName() {
        return "Apostate Recipes";
    }
}
