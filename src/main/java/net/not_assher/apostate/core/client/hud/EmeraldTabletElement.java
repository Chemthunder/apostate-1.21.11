package net.not_assher.apostate.core.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;
import org.joml.Matrix3x2fStack;

import java.util.Optional;

/**
 * @author Chemthunder
 */
public class EmeraldTabletElement implements HudElement {

    public void render(DrawContext context, RenderTickCounter tickCounter) {
        PlayerEntity tracked = null;

        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        World world = MinecraftClient.getInstance().world;
        if (world == null) return;

        PlayerComponent component = PlayerComponent.KEY.get(player);

        if (!component.getTabletTarget().isBlank() && component.getEmeraldTicks() > 0) {
            for (PlayerEntity target : world.getPlayers()) {
                if (target.getGameProfile().name().equals(component.getTabletTarget())) {
                    tracked = target;
                    break;
                }
            }

            if (tracked != null) {
                ItemStack compass = new ItemStack(Items.COMPASS);
                compass.set(
                        DataComponentTypes.LODESTONE_TRACKER,
                        new LodestoneTrackerComponent(
                                Optional.of(
                                        GlobalPos.create(
                                                tracked.getEntityWorld().getRegistryKey(),
                                                tracked.getBlockPos()
                                        )
                                ),
                                true
                        )
                );

                Matrix3x2fStack stack = context.getMatrices();

                stack.pushMatrix();

                stack.translate(context.getScaledWindowWidth() / 2.0F - 11.5F, context.getScaledWindowHeight() / 2.0F - 90.0F);
                stack.scale(1.6F);

                context.drawItem(
                        compass,
                        0, 0
                );

                stack.popMatrix();
            }
        }
    }
}
