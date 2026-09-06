package net.not_assher.apostate.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.not_assher.apostate.core.index.ModComponentTypes;
import net.not_assher.apostate.core.index.ModCriterions;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.item.component.CordialVowComponent;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class CordialVowItem extends Item {
    public CordialVowItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        CordialVowComponent vow = stack.getOrDefault(ModComponentTypes.VOW, CordialVowComponent.EMPTY);

        if (user.isSneaking() && world instanceof ServerWorld serverWorld) {
            if (!vow.completed()) {
                if (vow.owner().isBlank() && vow.signer().isBlank()) {
                    stack.set(ModComponentTypes.VOW, new CordialVowComponent(
                                    vow.signer(),
                                    user.getName().getString(),
                                    false
                            )
                    );

                    user.swingHand(hand);
                    return ActionResult.PASS;
                }

                if (!vow.owner().isBlank() && vow.signer().isBlank()) {
                    stack.set(ModComponentTypes.VOW, new CordialVowComponent(
                                    user.getName().getString(),
                                    vow.owner(),
                                    false
                            )
                    );

                    user.swingHand(hand);
                    return ActionResult.PASS;
                }

                if (!vow.owner().isBlank() && !vow.signer().isBlank()) {
                    stack.set(ModComponentTypes.VOW, new CordialVowComponent(
                                    vow.signer(),
                                    vow.owner(),
                                    true
                            )
                    );

                    user.swingHand(hand);

                    if (user instanceof ServerPlayerEntity serverPlayer) {
                        ModCriterions.SIGN_VOW.trigger(serverPlayer);
                    }
                    return ActionResult.PASS;
                }
            }
        }
        return super.use(world, user, hand);
    }

    public static class Tooltip implements BetterItemTooltipEvent {
        public void getTooltip(ItemStack stack, TooltipContext tooltipContext, TooltipType tooltipType, Consumer<Text> consumer) {
            CordialVowComponent vow = stack.get(ModComponentTypes.VOW);

            if (stack.isOf(ModItems.CORDIAL_VOW)) {
                if (vow != null) {
                    if (!vow.owner().isBlank() && !vow.signer().isBlank()) {
                        consumer.accept(Text.empty().append(Text.literal("Owned by ").formatted(Formatting.DARK_GRAY).append(Text.literal(vow.owner()).formatted(Formatting.AQUA))));
                        consumer.accept(Text.empty().append(Text.literal("Signed by ").formatted(Formatting.DARK_GRAY).append(Text.literal(vow.signer()).formatted(Formatting.AQUA))));
                    }
                }
            }
        }
    }
}
