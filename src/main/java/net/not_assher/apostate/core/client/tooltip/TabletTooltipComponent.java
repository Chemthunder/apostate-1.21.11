package net.not_assher.apostate.core.client.tooltip;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.item.component.TabletComponent;
import org.joml.Matrix3x2fStack;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class TabletTooltipComponent implements TooltipComponent {
    private final ItemStack self;
    private final TabletComponent component;

    public TabletTooltipComponent(ItemStack self, TabletComponent component) {
        this.self = self;
        this.component = component;
    }

    public int getHeight(TextRenderer textRenderer) {
        return 54;
    }

    public int getWidth(TextRenderer textRenderer) {
        return component.hunted() != null ? 96 + component.hunted().getName().get().length() : 96;
    }

    public void drawItems(TextRenderer textRenderer, int x, int y, int width, int height, DrawContext context) {
        if (component.hunted() != null) {
            ItemStack headStack = new ItemStack(Items.PLAYER_HEAD);
            headStack.set(DataComponentTypes.PROFILE, component.hunted());

            Matrix3x2fStack stack = context.getMatrices();

            stack.pushMatrix();
            stack.translate(x, y);

            stack.scale(1.2F);

            context.drawItem(headStack, 0, 0);
            stack.popMatrix();
        } else {
            Matrix3x2fStack stack = context.getMatrices();

            stack.pushMatrix();
            stack.translate(x, y);

            stack.scale(1.2F);

            context.drawText(
                    textRenderer,
                    "X",
                    0,
                    0,
                    0xFFff0000,
                    true
            );
            stack.popMatrix();
        }

        if (!component.ingredient().isEmpty()) {
            Matrix3x2fStack stack = context.getMatrices();

            stack.pushMatrix();
            stack.translate(x, y + 25);

            stack.scale(1.2F);

            context.drawItem(component.ingredient(), 0, 0);
            stack.popMatrix();
        }

        if (component.shouldDisplay()) {
            context.drawVerticalLine(
                    x + 20,
                    y,
                    y + 50,
                    0xFF4d4d4d
            );

            context.drawHorizontalLine(
                    x,
                    x + width,
                    y + (height / 2),
                    0xFF4d4d4d
            );
        }
    }

    public void drawText(DrawContext context, TextRenderer textRenderer, int x, int y) {
        if (component.hunted() != null) {
            context.drawText(
                    textRenderer,
                    component.hunted().getName().get(),
                    x + 25,
                    y + 5,
                    0xFFffffff,
                    true
            );
        }

        if (!component.ingredient().isEmpty()) {
            context.drawText(
                    textRenderer,
                    MiscUtils.formatString(Registries.ITEM.getId(component.ingredient().getItem()).getPath()),
                    x + 25,
                    y + 30,
                    0xFFffffff,
                    true
            );
        } else {
            context.drawText(
                    textRenderer,
                    Text.literal("Empty"),
                    x + 25,
                    y + 30,
                    0xFFffffff,
                    true
            );
        }
    }
}
