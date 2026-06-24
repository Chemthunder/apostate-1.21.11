package net.not_assher.apostate.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.utilities.bounty.Bounty;
import net.not_assher.apostate.core.utilities.bounty.KillContext;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class BountyPosterItem extends Item implements ModelVaryingItem {
    public BountyPosterItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Bounty bounty = stack.get(ModDataComponentTypes.STORED_BOUNTY);

        if (bounty != null) {
            if (user.isSneaking() && bounty.isEmpty()) {
                if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                    stack.set(ModDataComponentTypes.STORED_BOUNTY, new Bounty(
                            stack.get(DataComponentTypes.CUSTOM_NAME).getString(),
                            user.getNameForScoreboard(),
                            KillContext.ALIVE,
                            false
                    ));

                    stack.remove(DataComponentTypes.CUSTOM_NAME);

                    if (world.isClient()) {
                        user.swingHand(hand);
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
                if (!bounty.isEmpty()) {
                    KillContext currentCtx = bounty.ctx();
                    KillContext[] values = KillContext.values();

                    stack.set(ModDataComponentTypes.STORED_BOUNTY, new Bounty(
                            bounty.targetName(),
                            bounty.ownerName(),
                            values[(currentCtx.ordinal() + 1) % values.length],
                            bounty.completed()
                    ));

                    if (player.getEntityWorld().isClient()) {
                        player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1, 1);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public Identifier getModel(ItemStack itemStack, ItemDisplayContext itemDisplayContext, HeldItemContext heldItemContext) {
        Bounty bounty = itemStack.getOrDefault(ModDataComponentTypes.STORED_BOUNTY, Bounty.EMPTY);
        return bounty.isEmpty() ? Apostate.id("bounty_unwrapped") : bounty.completed() ? Apostate.id("bounty_poster_complete") : Apostate.id("bounty_poster");
    }

    public static final class Tooltip implements BetterItemTooltipEvent {
        public void getTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplayComponent tooltipDisplayComponent, @Nullable PlayerEntity playerEntity, TooltipType tooltipType, Consumer<Text> lines) {
            if (itemStack.isOf(ModItems.BOUNTY_POSTER)) {
                Bounty bounty = itemStack.get(ModDataComponentTypes.STORED_BOUNTY);

                if (bounty != null) {
                    Text line = Text.literal(bounty.targetName()).append(Text.literal(" is wanted by ").formatted(Formatting.DARK_GRAY).append(Text.literal(bounty.ownerName()).formatted(Formatting.YELLOW).append(Text.literal(", ").formatted(Formatting.DARK_GRAY).append(bounty.ctx().txt))));

                    lines.accept(bounty.completed() ? line.copy().formatted(Formatting.STRIKETHROUGH) : line);
                }
            }
        }
    }
}
