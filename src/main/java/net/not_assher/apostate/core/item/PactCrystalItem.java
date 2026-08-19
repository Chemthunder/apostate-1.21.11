package net.not_assher.apostate.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.minecraft.component.type.TooltipDisplayComponent;
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
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModCriteria;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.data.ModDamageTypes;
import net.not_assher.apostate.core.utilities.records.Pact;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

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

        if (user.isSneaking() && world instanceof ServerWorld serverWorld) {
            if (!pact.completed()) {
                if (pact.owner().isBlank() && pact.signer().isBlank()) {
                    stack.set(ModDataComponentTypes.STORED_PACT, new Pact(
                                    pact.signer(),
                                    user.getName().getString(),
                                    false
                            )
                    );

                    user.swingHand(hand);

                    user.damage(serverWorld, user.getDamageSources().create(ModDamageTypes.PACT), 2.0F);

                    return ActionResult.PASS;
                }

                if (!pact.owner().isBlank() && pact.signer().isBlank()) {
                    stack.set(ModDataComponentTypes.STORED_PACT, new Pact(
                                    user.getName().getString(),
                                    pact.owner(),
                                    false
                            )
                    );

                    user.swingHand(hand);

                    user.damage(serverWorld, user.getDamageSources().create(ModDamageTypes.PACT), 4.0F);

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

                    if (user instanceof ServerPlayerEntity serverPlayer) {
                        ModCriteria.SIGN_CONTRACT.trigger(serverPlayer);
                    }

                    user.damage(serverWorld, user.getDamageSources().create(ModDamageTypes.PACT), 6.0F);

                    return ActionResult.PASS;
                }
            }
        }
        return super.use(world, user, hand);
    }

    public static class Tooltip implements BetterItemTooltipEvent {
        public void getTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplayComponent tooltipDisplayComponent, @Nullable PlayerEntity playerEntity, TooltipType tooltipType, Consumer<Text> consumer) {
            Pact pact = itemStack.get(ModDataComponentTypes.STORED_PACT);

            if (pact != null) {
                if (!pact.owner().isBlank() && !pact.signer().isBlank()) {
                    consumer.accept(Text.empty().append(Text.literal("Owned by ").formatted(Formatting.DARK_GRAY).append(Text.literal(pact.owner()).formatted(Formatting.AQUA))));
                    consumer.accept(Text.empty().append(Text.literal("Signed by ").formatted(Formatting.DARK_GRAY).append(Text.literal(pact.signer()).formatted(Formatting.AQUA))));
                }
            }
        }

        public static void create() {
            BetterItemTooltipEvent.EVENT.register(new PactCrystalItem.Tooltip());

            Apostate.LOGGER.info("Created PactCrystalItem/Tooltip");
        }
    }
}
