package net.not_assher.apostate.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(value = ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "getPlayerListName", at = @At("RETURN"), cancellable = true)
    public void apostate$updateListName(CallbackInfoReturnable<Text> cir) {
        cir.setReturnValue(((PlayerEntity)(Object)this).getDisplayName());
    }
}