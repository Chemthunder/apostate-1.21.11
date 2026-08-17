package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
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
            }
        };
    }

    public String getName() {
        return "Apostate Recipes";
    }
}
