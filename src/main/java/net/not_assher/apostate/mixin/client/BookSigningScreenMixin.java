package net.not_assher.apostate.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screen.ingame.BookSigningScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = BookSigningScreen.class)
public abstract class BookSigningScreenMixin {
    @WrapOperation(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getName()Lnet/minecraft/text/Text;"
            )
    )
    private Text apostate$applyNick(PlayerEntity instance, Operation<Text> original) {
        return Text.of(PlayerComponent.KEY.get(instance).getName());
    }
}
