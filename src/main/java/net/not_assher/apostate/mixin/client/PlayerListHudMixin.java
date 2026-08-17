package net.not_assher.apostate.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.not_assher.apostate.core.ApostateClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(value = PlayerListHud.class)
public abstract class PlayerListHudMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderLatencyIcon", at = @At(value = "HEAD"))
    private void apostate$editListEntry(DrawContext context, int width, int x, int y, PlayerListEntry entry, CallbackInfo ci) {
        ApostateClient.drawListEntry(context, width, x, y, entry, this.client);
    }
}
