package net.not_assher.apostate.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.item.component.BookComponent;
import net.not_assher.apostate.core.networking.s2c.OpenBountyBookPayload;
import net.not_assher.apostate.core.networking.s2c.OpenFlyerPayload;
import net.not_assher.apostate.core.utilities.records.Bounty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class BountyBookItem extends Item {
    public BountyBookItem(Settings settings) {
        super(settings);
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        BookComponent book = stack.get(ModDataComponentTypes.BOOK);

        if (book != null) {
            List<ItemStack> entries = new ArrayList<>(book.posters());

            if (clickType == ClickType.RIGHT) {
                if (!otherStack.isEmpty()) {
                    if (otherStack.contains(ModDataComponentTypes.STORED_BOUNTY)) {
                        ItemStack split = otherStack.split(1);

                        entries.add(split);
                        stack.set(ModDataComponentTypes.BOOK, new BookComponent(entries));
                        if (player.getEntityWorld().isClient()) {
                            player.playSound(SoundEvents.ITEM_BUNDLE_INSERT);
                        }
                        return true;
                    }
                } else {
                    ItemStack top = entries.getLast();

                    cursorStackReference.set(top);

                    entries.remove(top);
                    stack.set(ModDataComponentTypes.BOOK, new BookComponent(entries));
                    if (player.getEntityWorld().isClient()) {
                        player.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof ServerPlayerEntity serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new OpenBountyBookPayload(user.getStackInHand(hand)));
        }

        user.swingHand(hand);
        return super.use(world, user, hand);
    }

    public static class Tooltip implements BetterItemTooltipEvent {
        public void getTooltip(ItemStack stack, TooltipContext tooltipContext, TooltipType tooltipType, Consumer<Text> consumer) {
            if (stack.isOf(ModItems.BOUNTY_BOOK)) {
                BookComponent book = stack.get(ModDataComponentTypes.BOOK);

                if (book != null) {
                    for (ItemStack storedStack : book.posters()) {
                        Bounty bounty = storedStack.get(ModDataComponentTypes.STORED_BOUNTY);

                        if (bounty != null) {
                            consumer.accept(Text.literal(bounty.targetName() + " | " + bounty.ownerName())
                                    .formatted(bounty.ctx().formatting)
                                    .formatted(bounty.completed() ? Formatting.STRIKETHROUGH : Formatting.BOLD));
                        }
                    }
                }
            }
        }

        public static void create() {
            BetterItemTooltipEvent.EVENT.register(new Tooltip());

            Apostate.LOGGER.info("Created BountyBookItem/Tooltip");
        }
    }
}
