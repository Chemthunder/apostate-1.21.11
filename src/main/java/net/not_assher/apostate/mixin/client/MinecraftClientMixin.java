package net.not_assher.apostate.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author Chemthunder
 */
@Mixin(value = MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Nullable public ClientWorld world;
    @Shadow @Nullable public ClientPlayerEntity player;

    @WrapMethod(method = "hasOutline")
    private boolean apostate$echoShardOutline(Entity entity, Operation<Boolean> original) {
        if (this.world != null) {
            if (entity instanceof PlayerEntity target) {
                PlayerEntity player = this.player;

                if (player != null) {
                    PlayerComponent component = PlayerComponent.KEY.get(player);

                    if (component.getTabletTarget().equals(target.getName().getString()) && component.getEchoTicks() > 0) {
                        return true;
                    }
                }
            }
        }
        return original.call(entity);
    }
}
