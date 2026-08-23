package net.not_assher.apostate.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author Chemthunder
 */
@Mixin(value = PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract GameProfile getGameProfile();

    @WrapMethod(method = "getDisplayName")
    private Text apostate$applyNickDisplay(Operation<Text> original) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (!PlayerComponent.KEY.get(player).getName().isBlank()) {
            return Text.literal(PlayerComponent.KEY.get(player).getName())
                    .fillStyle(Style.EMPTY.withHoverEvent(new HoverEvent.ShowText(Text.of(getGameProfile().name()))));
        }
        return original.call();
    }
}
