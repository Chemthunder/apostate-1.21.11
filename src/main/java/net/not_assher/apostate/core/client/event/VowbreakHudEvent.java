package net.not_assher.apostate.core.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.StringHelper;
import net.not_assher.apostate.core.cca.entity.VowbreakComponent;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class VowbreakHudEvent implements HudElement {
    public void render(DrawContext context, RenderTickCounter tickCounter) {
        String[] mercies = new String[] {
            "DESTINY HAS ARRIVED.",
            "Spend your final moments.",
            "Tick tock.",
            "You know what you've done.",
            "Your actions have consequences.",
            "Remember your place.",
            "There always could've been a safe harbor."
        };

        MinecraftClient client = MinecraftClient.getInstance();

        PlayerEntity player = client.player;
        if (player == null) return;

        VowbreakComponent vow = VowbreakComponent.KEY.get(player);

        if (vow.isActive()) {
            int bound = 20;

            context.drawCenteredTextWithShadow(
                    client.textRenderer,
                    Text.literal(StringHelper.formatTicks(vow.getTickAge(), client.world.getTickManager().getTickRate())),
                    context.getScaledWindowWidth() / 2,
                    context.getScaledWindowHeight() / 2 - 80,
                    0xFFff0000
            );

            Random random = new Random();
            int x = context.getScaledWindowWidth() / 2;
            int y = context.getScaledWindowHeight() / 2 - 40;
            Text text = Text.literal(mercies[random.nextInt(mercies.length)]);

            if (player.age % 20 == 0) {
                x = (context.getScaledWindowWidth() / 2) + random.nextInt(-bound, bound);
                y = (context.getScaledWindowHeight() / 2 - 40) + random.nextInt(-bound, bound);

                text = Text.literal(mercies[random.nextInt(mercies.length)]);
            }

            for (int i = 0; i < 4; i++) {
                context.drawCenteredTextWithShadow(
                        client.textRenderer,
                        text,
                        x + random.nextInt(-10, 10),
                        y + random.nextInt(-10, 10),
                        0xFF7e2626
                );
            }

            context.drawCenteredTextWithShadow(
                    client.textRenderer,
                    Text.literal("VOW BREAKER."),
                    x,
                    y,
                    0xFFd51d1d
            );
        }
    }
}
