package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModBlocks;
import net.not_assher.apostate.core.index.ModCriteria;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.item.component.BountyComponent;
import net.not_assher.apostate.core.utilities.enums.KillContext;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
@SuppressWarnings("unused")
public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry root = Advancement.Builder.createUntelemetered()
                .display(
                        ModItems.BOUNTY_POSTER,
                        Text.translatable("advancements.apostate.root.title"),
                        Text.translatable("advancements.apostate.root.desc"),
                        Apostate.id("gui/advancements/backgrounds/apostate"),
                        AdvancementFrame.TASK,
                        false,
                        false,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("tick")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("tick", ModCriteria.ROOT.create(new TickCriterion.Conditions(Optional.empty())))
                .build(Apostate.id("root"));

        consumer.accept(root);

        ItemStack placeBountyStack = new ItemStack(ModItems.BOUNTY_POSTER);
        placeBountyStack.set(ModDataComponentTypes.STORED_BOUNTY, new BountyComponent(
                "",
                "",
                KillContext.DEAD,
                false,
                false,
                true
        ));

        ItemStack collectBountyStack = new ItemStack(ModItems.BOUNTY_POSTER);
        collectBountyStack.set(ModDataComponentTypes.STORED_BOUNTY, new BountyComponent(
                "",
                "",
                KillContext.EITHER,
                true,
                false,
                true
        ));

        AdvancementEntry placeBounty = generateBasicAdvancement(
                consumer,
                root,
                new AdvancementContext(
                        placeBountyStack,
                        "place_bounty",
                        ModCriteria.PLACE_BOUNTY.create(new TickCriterion.Conditions(Optional.empty()))
                )
        );

        AdvancementEntry collectBounty = generateBasicAdvancement(
                consumer,
                placeBounty,
                new AdvancementContext(
                        collectBountyStack,
                        "collect_bounty",
                        ModCriteria.COLLECT_BOUNTY.create(new TickCriterion.Conditions(Optional.empty()))
                )
        );

        AdvancementEntry signContract = generateBasicAdvancement(
                consumer,
                root,
                new AdvancementContext(
                        ModItems.PACT_CRYSTAL.getDefaultStack(),
                        "sign_contract",
                        ModCriteria.SIGN_CONTRACT.create(new TickCriterion.Conditions(Optional.empty()))
                )
        );

        AdvancementEntry useTablet = generateBasicAdvancement(
                consumer,
                root,
                new AdvancementContext(
                        ModItems.DIVINING_TABLET.getDefaultStack(),
                        "use_tablet",
                        ModCriteria.USE_TABLET.create(new TickCriterion.Conditions(Optional.empty()))
                )
        );

        AdvancementEntry crimsonCandle = generateBasicAdvancement(
                consumer,
                root,
                new AdvancementContext(
                        ModBlocks.CRIMSON_CANDLE.asItem().getDefaultStack(),
                        "crimson_candle",
                        ModCriteria.CRIMSON_CANDLE.create(new TickCriterion.Conditions(Optional.empty()))
                )
        );

        AdvancementEntry bell = generateBasicAdvancement(
                consumer,
                signContract,
                new AdvancementContext(
                        ModBlocks.COVENANT_BELL.asItem().getDefaultStack(),
                        "covenant",
                        ModCriteria.COVENANT_BELL.create(new TickCriterion.Conditions(Optional.empty()))
                )
        );
    }

    private AdvancementEntry generateBasicAdvancement(Consumer<AdvancementEntry> consumer, AdvancementEntry root, AdvancementContext context) {
        AdvancementEntry generated = Advancement.Builder.createUntelemetered()
                .parent(root)
                .display(
                        context.displayStack,
                        Text.translatable("advancements.apostate." + context.title + ".title"),
                        Text.translatable("advancements.apostate." + context.title + ".desc"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("e")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("e", context.criterion)
                .build(Apostate.id(context.title));

        consumer.accept(generated);
        return generated;
    }

    private record AdvancementContext(ItemStack displayStack, String title, AdvancementCriterion<?> criterion) {}
}
