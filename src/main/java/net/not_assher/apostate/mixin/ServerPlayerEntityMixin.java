package net.not_assher.apostate.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @WrapMethod(method = "getPlayerListName")
    private Text apostate$applyNick(Operation<Text> original) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (!PlayerComponent.KEY.get(player).getName().isBlank()) {
            return Text.literal(PlayerComponent.KEY.get(player).getName());
        }
        return original.call();
    }
}
