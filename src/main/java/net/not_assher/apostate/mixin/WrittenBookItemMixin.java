package net.not_assher.apostate.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = WrittenBookItem.class)
public abstract class WrittenBookItemMixin extends Item {
    public WrittenBookItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT) {
            if (otherStack.isOf(Items.FEATHER)) {
                WrittenBookContentComponent content = stack.get(DataComponentTypes.WRITTEN_BOOK_CONTENT);

                if (player.isCreative()) {
                    if (content != null) {
                        if (player.getDisplayName() != null) {
                            stack.set(DataComponentTypes.WRITTEN_BOOK_CONTENT, new WrittenBookContentComponent(
                                    content.title(),
                                    player.getDisplayName().getString(),
                                    content.generation(),
                                    content.pages(),
                                    content.resolved()
                            ));

                            if (player.getEntityWorld().isClient()) {
                                player.playSound(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT);
                                player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN);
                            }

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
