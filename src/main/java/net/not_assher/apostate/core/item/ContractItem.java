package net.not_assher.apostate.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModCriteria;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.utilities.records.Contract;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class ContractItem extends Item {
    public ContractItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Contract contract = stack.get(ModDataComponentTypes.STORED_CONTRACT);

        if (user.isSneaking()) {
            if (contract != null && !contract.signed()) {
                if (contract.bearer().isBlank()) {
                    stack.set(ModDataComponentTypes.STORED_CONTRACT, new Contract(
                            contract.signer(),
                            user.getNameForScoreboard(),
                            false
                    ));

                    user.sendMessage(Text.literal("You have become the bearer of this contract."), true);

                    if (world.isClient()) {
                        user.swingHand(hand);
                    }

                    return ActionResult.PASS;
                }

                if (contract.signer().isBlank() && !contract.bearer().isBlank()) {
                    stack.set(ModDataComponentTypes.STORED_CONTRACT, new Contract(
                            user.getNameForScoreboard(),
                            contract.bearer(),
                            false
                    ));

                    user.sendMessage(Text.literal("You have become the signer of this contract."), true);

                    if (world.isClient()) {
                        user.swingHand(hand);
                    }

                    return ActionResult.PASS;
                }

                if (!contract.signer().isBlank() && !contract.bearer().isBlank()) {
                    stack.set(ModDataComponentTypes.STORED_CONTRACT, new Contract(
                            contract.signer(),
                            contract.bearer(),
                            true
                    ));

                    world.getPlayers().forEach(capture -> {
                        if (capture instanceof ServerPlayerEntity serverPlayer) {
                            if (serverPlayer.getNameForScoreboard().equals(contract.signer())) {
                                ModCriteria.SIGN_CONTRACT.trigger(serverPlayer);
                            }
                        }
                    });

                    if (user instanceof ServerPlayerEntity serverPlayer) {
                        ModCriteria.SIGN_CONTRACT.trigger(serverPlayer);
                    }

                    if (world.isClient()) {
                        user.swingHand(hand);
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
        }
        return super.use(world, user, hand);
    }

    public static final class Tooltip implements BetterItemTooltipEvent {
        public void getTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent component, @Nullable PlayerEntity player, TooltipType type, Consumer<Text> lines) {
            if (stack.isOf(ModItems.CONTRACT)) {
                Contract contract = stack.get(ModDataComponentTypes.STORED_CONTRACT);

                if (contract != null) {
                    if (!contract.signed()) {
                        if (!contract.signer().isBlank()) {
                            lines.accept(Text.literal("For ").formatted(Formatting.DARK_GRAY).append(Text.literal(contract.signer()).formatted(Formatting.YELLOW)));
                        }

                        if (!contract.bearer().isBlank()) {
                            lines.accept(Text.literal("By ").formatted(Formatting.DARK_GRAY).append(Text.literal(contract.bearer()).formatted(Formatting.YELLOW)));
                        }
                    } else {
                        lines.accept(Text.literal("Signed by ").formatted(Formatting.DARK_GRAY).append(Text.literal(contract.signer()).formatted(Formatting.YELLOW).append(Text.literal(",").formatted(Formatting.DARK_GRAY))));
                        lines.accept(Text.literal("Bound by ").formatted(Formatting.DARK_GRAY).append(Text.literal(contract.bearer()).formatted(Formatting.YELLOW)));
                    }
                }
            }
        }

        public static void create() {
            BetterItemTooltipEvent.EVENT.register(new Tooltip());

            Apostate.LOGGER.info("Created ContractItem/Tooltip");
        }
    }
}
