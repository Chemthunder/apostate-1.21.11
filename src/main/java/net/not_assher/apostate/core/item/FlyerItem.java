package net.not_assher.apostate.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.networking.s2c.OpenFlyerPayload;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class FlyerItem extends Item {
    public FlyerItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getMainHandStack();

        if (user instanceof ServerPlayerEntity serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new OpenFlyerPayload(stack));
        }
        return super.use(world, user, hand);
    }

    public static class Tooltip implements BetterItemTooltipEvent {
        @SuppressWarnings("DataFlowIssue")
        public void getTooltip(ItemStack stack, TooltipContext tooltipContext, TooltipType tooltipType, Consumer<Text> consumer) {
            if (stack.isOf(ModItems.FLYER)) {
                if (stack.get(ModDataComponentTypes.STRING) != null) {
                    consumer.accept(Text.literal(stack.get(ModDataComponentTypes.STRING)).formatted(Formatting.DARK_GRAY));
                }
            }
        }
    }
}
