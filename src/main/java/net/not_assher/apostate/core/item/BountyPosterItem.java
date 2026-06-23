package net.not_assher.apostate.core.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.utilities.bounty.Bounty;

/**
 * @author Chemthunder
 */
public class BountyPosterItem extends Item {
    public BountyPosterItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Bounty bounty = stack.get(ModDataComponentTypes.STORED_BOUNTY);

        if (bounty != null) {
            if (bounty.isEmpty() && user.isSneaking()) {

            }
        }
        return super.use(world, user, hand);
    }
}
