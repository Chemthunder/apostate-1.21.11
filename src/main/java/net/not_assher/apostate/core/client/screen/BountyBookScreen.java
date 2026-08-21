package net.not_assher.apostate.core.client.screen;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.item.component.BookComponent;
import net.not_assher.apostate.core.utilities.records.Bounty;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Chemthunder
 */
public class BountyBookScreen extends Screen {
    private final ItemStack stack;
    private final List<Function<DrawContext, Drawable>> drawableFunctions = new ArrayList<>();

    private int currentPage = 0;

    public BountyBookScreen(ItemStack stack) {
        super(Text.empty());
        this.stack = stack;
    }

    protected void init() {
        drawableFunctions.clear();

        PlayerEntity player = client.player;
        BookComponent book = stack.get(ModDataComponentTypes.BOOK);

        if (book != null) {
            if (player != null) {
                ButtonWidget cycleFwd = ButtonWidget.builder(Text.literal("Next"), (button -> {
                    if (currentPage < book.posters().size() - 1) {
                        currentPage++;
                    } else {
                        currentPage = 0;
                    }
                })).build();

                cycleFwd.setDimensions(48, 48);
                cycleFwd.setPosition(width / 2 - (cycleFwd.getWidth() / 2) + 30, height - 60);
                drawableFunctions.add(context -> cycleFwd);
                this.addDrawableChild(cycleFwd);

                ButtonWidget cycleBwd = ButtonWidget.builder(Text.literal("Back"), (button -> {
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        currentPage = book.posters().size();
                    }
                })).build();

                cycleBwd.setDimensions(48, 48);
                cycleBwd.setPosition(width / 2 - (cycleBwd.getWidth() / 2) - 30, height - 60);
                drawableFunctions.add(context -> cycleBwd);
                this.addDrawableChild(cycleBwd);
            }
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        BookComponent book = stack.get(ModDataComponentTypes.BOOK);
        drawableFunctions.forEach(func -> func.apply(context).render(context, mouseX, mouseY, deltaTicks));

        if (book != null) {
            ItemStack display = book.posters().get(currentPage);

            if (display.contains(ModDataComponentTypes.STORED_BOUNTY)) {
                Bounty bounty = display.get(ModDataComponentTypes.STORED_BOUNTY);

                context.drawGuiTexture(
                        RenderPipelines.GUI_TEXTURED,
                        Apostate.id("screen/bounty_book"),
                        width / 2 - 90,
                        height / 2 - 90,
                        256,
                        256
                );

                if (bounty != null) {
                    Matrix3x2fStack matrices = context.getMatrices();

                    ItemStack head = new ItemStack(Items.PLAYER_HEAD);
                    head.set(DataComponentTypes.PROFILE, ProfileComponent.ofDynamic(bounty.targetName()));

                    matrices.pushMatrix();

                    int scale = 5;

                    matrices.translate(width / 2F - ((float) (16 * scale) / 2), height / 2F - ((float) (16 * scale) / 2));
                    matrices.scale(scale);

                    context.drawItem(
                            head,
                            0, 0
                    );

                    matrices.popMatrix();

                    if (bounty.completed()) {
                        matrices.pushMatrix();

                        matrices.translate(width / 2F - ((float) (scale) / 2), height / 2F - ((float) (10 * scale) / 2));
                        matrices.scale(scale + 2);

                        context.drawCenteredTextWithShadow(
                                client.textRenderer,
                                Text.literal("X").formatted(bounty.ctx().formatting),
                                0, 0,
                                0xFFFFFFFF
                        );

                        matrices.popMatrix();
                    }

                    context.drawCenteredTextWithShadow(
                            client.textRenderer,
                            Text.literal("WANTED").formatted(bounty.ctx().formatting),
                            width / 2,
                            height / 2 - 70,
                            0xFFFFFFFF
                    );

                    matrices.pushMatrix();

                    matrices.translate(width / 2F, height / 2F - 60);
                    matrices.scale(1.2F);

                    context.drawCenteredTextWithShadow(
                            client.textRenderer,
                            Text.literal(bounty.targetName())
                                    .formatted(bounty.ctx().formatting)
                                    .formatted(bounty.completed() ? Formatting.STRIKETHROUGH : Formatting.BOLD),
                            0,
                            0,
                            0xFFFFFFFF
                    );
                    matrices.popMatrix();

                    context.drawCenteredTextWithShadow(
                            client.textRenderer,
                            bounty.ctx().txt.copy()
                                    .formatted(bounty.ctx().formatting)
                                    .formatted(bounty.completed() ? Formatting.STRIKETHROUGH : Formatting.BOLD),
                            width / 2,
                            height / 2 + 40,
                            0xFFFFFFFF
                    );

                    matrices.pushMatrix();
                    matrices.translate(width / 2F - 50, height / 2F + 50);
                    matrices.scale(0.8F);

                    context.drawWrappedTextWithShadow(
                            client.textRenderer,
                            Text.literal("Wanted by " + bounty.ownerName())
                                    .formatted(bounty.ctx().formatting),
                            0, 0,
                            230,
                            0xFFFFFFFF
                    );
                    matrices.popMatrix();
                }
            }
        }
    }

    public boolean shouldPause() {
        return false;
    }
}
