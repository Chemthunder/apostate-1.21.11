package net.not_assher.apostate.core.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.utilities.records.Bounty;
import net.not_assher.apostate.ext.ModConfig;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BountyDisplayEvent implements HudElement {
    public void render(@NotNull DrawContext drawContext, @NotNull RenderTickCounter renderTickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (ModConfig.showDisplays) {
            if (client.targetedEntity instanceof ItemFrameEntity itemFrame) {
                ItemStack stack = itemFrame.getHeldItemStack();

                if (client.player != null && client.player.getMainHandStack().isEmpty()) {
                    if (stack != null && stack.isOf(ModItems.BOUNTY_POSTER)) {
                        Bounty bounty = stack.getOrDefault(ModDataComponentTypes.STORED_BOUNTY, Bounty.EMPTY);

                        if (bounty.signed()) {
                            List<Text> texts = List.of(
                                    Text.literal("WANTED: " + bounty.targetName()),
                                    Text.literal("-----------"),
                                    Text.literal("Wanted by " + bounty.ownerName()),
                                    Text.literal("Wanted " + bounty.ctx().txt.getString()).formatted(bounty.ctx().formatting)
                            );

                            int x1 = drawContext.getScaledWindowWidth() / 2 + 37;
                            int x2 = drawContext.getScaledWindowWidth() / 2 + (43 * texts.size()) + (bounty.targetName().length() * 3);

                            int y1 = (drawContext.getScaledWindowHeight() / 2 - 30);
                            int y2 = (drawContext.getScaledWindowHeight() / 2 - 20) + (4 * 15);

                            drawContext.fill(
                                    x1,
                                    y1,
                                    x2,
                                    y2,
                                    ColorHelper.withAlpha(0.4F, 0x000000)
                            );

                            int color = ColorHelper.withAlpha(0.8F, bounty.ctx().color);

                            drawContext.drawHorizontalLine(
                                    x1,
                                    x2,
                                    y1,
                                    color
                            );

                            drawContext.drawHorizontalLine(
                                    x1,
                                    x2,
                                    y2,
                                    color
                            );

                            drawContext.drawVerticalLine(
                                    x1,
                                    y1,
                                    y2,
                                    color
                            );

                            drawContext.drawVerticalLine(
                                    x2,
                                    y1,
                                    y2,
                                    color
                            );

                            for (Text txt : texts) {
                                drawContext.drawText(
                                        client.textRenderer,
                                        !bounty.completed()
                                                ? txt.copy().formatted(bounty.ctx().formatting)
                                                : txt.copy().formatted(bounty.ctx().formatting).formatted(Formatting.STRIKETHROUGH),
                                        drawContext.getScaledWindowWidth() / 2 + 40,
                                        (drawContext.getScaledWindowHeight() / 2 - 20) + (texts.indexOf(txt) * 15),
                                        0xFFffffff,
                                        true
                                );
                            }
                        }
                    }
                }
            }
        }
    }
}