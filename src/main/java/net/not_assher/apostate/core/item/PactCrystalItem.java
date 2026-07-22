package net.not_assher.apostate.core.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.utilities.records.Pact;

/**
 * @author Chemthunder
 */
public class PactCrystalItem extends Item {
    public PactCrystalItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Pact pact = stack.getOrDefault(ModDataComponentTypes.STORED_PACT, Pact.EMPTY);

        if (user.isSneaking()) {
            if (!pact.completed()) {
                if (pact.owner().isBlank() && pact.signer().isBlank()) {
                    stack.set(ModDataComponentTypes.STORED_PACT, new Pact(
                                    pact.signer(),
                                    user.getDisplayName().toString(),
                                    false
                            )
                    );

                    user.swingHand(hand);

                    return ActionResult.PASS;
                }

                if (!pact.owner().isBlank() && pact.signer().isBlank()) {
                    stack.set(ModDataComponentTypes.STORED_PACT, new Pact(
                                    user.getNameForScoreboard(),
                                    pact.owner(),
                                    false
                            )
                    );

                    user.swingHand(hand);

                    return ActionResult.PASS;
                }

                if (!pact.owner().isBlank() && !pact.signer().isBlank()) {
                    stack.set(ModDataComponentTypes.STORED_PACT, new Pact(
                                    pact.signer(),
                                    pact.owner(),
                                    true
                            )
                    );

                    user.swingHand(hand);

                    return ActionResult.PASS;
                }
            }
        }
        return super.use(world, user, hand);
    }
}
