package net.not_assher.apostate.core.utilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.utilities.records.Bounty;
import org.jetbrains.annotations.Nullable;

public class ModUtils {
    @Nullable
    public static ItemStack checkIfBounty(PlayerEntity player) {
        for (ItemStack slot : player.getInventory()) {
            if (slot.isOf(ModItems.BOUNTY_POSTER)) {
                Bounty bounty = slot.get(ModDataComponentTypes.STORED_BOUNTY);

                if (bounty != null) {
                    if (!bounty.completed() && bounty.signed()) {
                        return slot;
                    }
                }
            }
        }

        return null;
    }
}
