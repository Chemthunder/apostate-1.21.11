package net.not_assher.apostate.core.client.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Easing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.item.FlyerItem;

/**
 * @author Chemthunder
 */
public class TextOverlayEvents {
    public static class Ticker implements ClientTickEvents.StartTick {
        public static float opacity = 0.0F;
        public static String string = "";

        public void onStartTick(MinecraftClient client) {
            PlayerEntity player = client.player;
            World world = client.world;

            opacity = MathHelper.clamp(opacity, 0.0F, 0.9F);

            if (player != null && world != null) {
                if (client.targetedEntity instanceof ItemFrameEntity itemFrame) {
                    ItemStack stack = itemFrame.getHeldItemStack();

                    if (stack.getItem() instanceof FlyerItem) {
                        opacity += 0.1F;
                        string = stack.get(ModDataComponentTypes.STRING);
                        return;
                    }
                }
            }

            if (opacity > 0.0F) {
                opacity -= 0.1F;
            }
        }
    }

    public static class Render implements HudElement {
        public void render(DrawContext context, RenderTickCounter tickCounter) {
            float o = Ticker.opacity;
            String s = Ticker.string;

            if (o > 0) {
                context.drawCenteredTextWithShadow(
                        MinecraftClient.getInstance().textRenderer,
                        Text.literal(s),
                        context.getScaledWindowWidth() / 2,
                        context.getScaledWindowHeight() / 2 - 40,
                        ColorHelper.withAlpha(Easing.inOutQuad(o), 0xffffff)
                );
            }
        }
    }
}
