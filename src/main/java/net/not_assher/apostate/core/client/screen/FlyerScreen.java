package net.not_assher.apostate.core.client.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.networking.c2s.FlyerEditPayload;

/**
 * @author Chemthunder
 */
public class FlyerScreen extends Screen {
    private final ItemStack stack;

    public FlyerScreen(ItemStack stack) {
        super(Text.empty());
        this.stack = stack;
    }

    protected void init() {
        String d = stack.get(ModDataComponentTypes.STRING);
        PlayerEntity player = client.player;

        if (player != null && d != null) {
            TextFieldWidget input = new TextFieldWidget(client.textRenderer, 128 * 4, 32, Text.literal(d));
            input.setPosition(width / 2 - (input.getWidth() / 2), height / 2);
            input.setCentered(true);
            input.setDrawsBackground(true);
            input.setInvertSelectionBackground(true);
            this.addDrawableChild(input);

            ButtonWidget confirm = ButtonWidget.builder(Text.literal("Confirm"), (button -> {
                this.close();

                if (!input.getText().isBlank()) {
                    FlyerEditPayload.send(stack, input.getText());
                }

                player.swingHand(client.player.getActiveHand());
                player.playSound(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT);
            })).build();
            confirm.setDimensions(96, 16);
            confirm.setPosition(width / 2 - (confirm.getWidth() / 2), height / 2 - 40);
            this.addDrawableChild(confirm);
        }
    }

    public boolean shouldPause() {
        return false;
    }
}
