package net.not_assher.apostate.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModCriteria;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.utilities.enums.KillContext;
import net.not_assher.apostate.core.utilities.records.Bounty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
@SuppressWarnings("DataFlowIssue")
public class BountyPosterItem extends Item implements ModelVaryingItem {
    public BountyPosterItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Bounty bounty = stack.get(ModDataComponentTypes.STORED_BOUNTY);

        if (bounty != null) {
            if (user.isSneaking() && !bounty.signed()) {
                if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                    stack.set(ModDataComponentTypes.STORED_BOUNTY, new Bounty(
                            stack.get(DataComponentTypes.CUSTOM_NAME).getString(),
                            user.getNameForScoreboard(),
                            bounty.ctx(),
                            false,
                            false,
                            true
                    ));

                    stack.remove(DataComponentTypes.CUSTOM_NAME);

                    if (world.isClient()) {
                        user.swingHand(hand);
                    }

                    if (user instanceof ServerPlayerEntity serverPlayer) {
                        ModCriteria.PLACE_BOUNTY.trigger(serverPlayer);
                    }

                    world.playSound(
                            null,
                            user.getBlockPos(),
                            SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT,
                            SoundCategory.PLAYERS,
                            1,
                            1
                    );
                }
            }

            if (bounty.signed() && !bounty.completed() && !bounty.failed()) {
                if ((bounty.ctx().equals(KillContext.ALIVE)) || bounty.ctx().equals(KillContext.EITHER)) {
                    if (user.getNameForScoreboard().equals(bounty.ownerName())) {
                        stack.set(ModDataComponentTypes.STORED_BOUNTY, new Bounty(
                                bounty.targetName(),
                                bounty.ownerName(),
                                bounty.ctx(),
                                true,
                                false,
                                true
                        ));

                        if (world.isClient()) {
                            user.swingHand(hand);
                        }

                        world.playSound(
                                null,
                                user.getBlockPos(),
                                SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(),
                                SoundCategory.PLAYERS,
                                1,
                                1
                        );

                        user.sendMessage(Text.literal("You have completed this Bounty!").formatted(Formatting.GREEN, Formatting.BOLD), true);
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT) {
            Bounty bounty = stack.get(ModDataComponentTypes.STORED_BOUNTY);

            if (bounty != null) {
                if (otherStack.isEmpty()) {
                    if (!bounty.signed()) {
                        KillContext currentCtx = bounty.ctx();
                        KillContext[] values = KillContext.values();

                        stack.set(ModDataComponentTypes.STORED_BOUNTY, new Bounty(
                                bounty.targetName(),
                                bounty.ownerName(),
                                values[(currentCtx.ordinal() + 1) % values.length],
                                false,
                                false,
                                false
                        ));

                        if (player.getEntityWorld().isClient()) {
                            player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1, 1);
                        }

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Identifier getModel(ItemDisplayContext itemDisplayContext, ItemStack itemStack, HeldItemContext heldItemContext) {
        Bounty bounty = itemStack.getOrDefault(ModDataComponentTypes.STORED_BOUNTY, Bounty.EMPTY);

        if (itemDisplayContext == ItemDisplayContext.FIXED) {
            return !bounty.signed() ? Apostate.id("bounty_unwrapped") : Apostate.id("bounty_poster_display");
        } else {
            return !bounty.signed() ? Apostate.id("bounty_unwrapped") : bounty.completed() ? Apostate.id("bounty_poster_complete") : Apostate.id("bounty_poster");
        }
    }

    public List<Identifier> getModelsToLoad() {
        return List.of(
                Apostate.id("bounty_unwrapped"),
                Apostate.id("bounty_poster_display"),
                Apostate.id("bounty_poster_complete"),
                Apostate.id("bounty_poster")
        );
    }

    public static final class Tooltip implements BetterItemTooltipEvent {
        public void getTooltip(ItemStack stack, TooltipContext tooltipContext, TooltipType tooltipType, Consumer<Text> consumer) {
            if (stack.isOf(ModItems.BOUNTY_POSTER)) {
                Bounty bounty = stack.get(ModDataComponentTypes.STORED_BOUNTY);

                if (bounty != null) {
                    if (bounty.signed()) {
                        List<Text> texts = List.of(
                                Text.literal("WANTED: " + bounty.targetName()),
                                Text.literal("Wanted by " + bounty.ownerName()),
                                Text.literal("Wanted ").append(bounty.ctx().txt)
                        );

                        List<Text> finalTexts = new ArrayList<>();

                        for (Text text : texts) {
                            finalTexts.add(text.copy().formatted(bounty.ctx().formatting));
                        }

                        for (Text text : finalTexts) {
                            if (!bounty.completed()) {
                                consumer.accept(text);
                            } else {
                                consumer.accept(text.copy().formatted(Formatting.STRIKETHROUGH));
                            }
                        }

                        if (bounty.failed()) {
                            consumer.accept(Text.literal("You have failed this bounty!").formatted(
                                    Formatting.BOLD,
                                    Formatting.DARK_RED,
                                    Formatting.ITALIC
                            ));
                        }
                    } else {
                        consumer.accept(bounty.ctx().txt.copy().formatted(bounty.ctx().formatting));
                    }
                }
            }
        }
    }
}
