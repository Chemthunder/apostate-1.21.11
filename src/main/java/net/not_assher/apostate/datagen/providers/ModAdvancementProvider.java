package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModCriteria;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.utilities.enums.KillContext;
import net.not_assher.apostate.core.utilities.records.Bounty;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
        placeBountyStack.set(ModDataComponentTypes.STORED_BOUNTY, new Bounty(
                "",
                "",
                KillContext.DEAD,
                false,
                false,
                true
        ));

        AdvancementEntry placeBounty = Advancement.Builder.createUntelemetered()
                .parent(root)
                .display(
                        placeBountyStack,
                        Text.translatable("advancements.apostate.place_bounty.title"),
                        Text.translatable("advancements.apostate.place_bounty.desc"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("e")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("e", ModCriteria.PLACE_BOUNTY.create(new TickCriterion.Conditions(Optional.empty())))
                .build(Apostate.id("place_bounty"));

        consumer.accept(placeBounty);

        ItemStack collectBountyStack = new ItemStack(ModItems.BOUNTY_POSTER);
        collectBountyStack.set(ModDataComponentTypes.STORED_BOUNTY, new Bounty(
                "",
                "",
                KillContext.EITHER,
                true,
                false,
                true
        ));

        AdvancementEntry collectBounty = Advancement.Builder.createUntelemetered()
                .parent(placeBounty)
                .display(
                        collectBountyStack,
                        Text.translatable("advancements.apostate.collect_bounty.title"),
                        Text.translatable("advancements.apostate.collect_bounty.desc"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("e")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("e", ModCriteria.COLLECT_BOUNTY.create(new TickCriterion.Conditions(Optional.empty())))
                .build(Apostate.id("collect_bounty"));

        consumer.accept(collectBounty);

        AdvancementEntry signContract = Advancement.Builder.createUntelemetered()
                .parent(root)
                .display(
                        ModItems.CONTRACT,
                        Text.translatable("advancements.apostate.sign_contract.title"),
                        Text.translatable("advancements.apostate.sign_contract.desc"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("e")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("e", ModCriteria.SIGN_CONTRACT.create(new TickCriterion.Conditions(Optional.empty())))
                .build(Apostate.id("sign_contract"));

        consumer.accept(signContract);
    }
}
