package net.not_assher.apostate.core.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;

/**
 * @author Chemthunder
 */
public class DebugRelogEvent implements ServerPlayerEvents.Join {
    public void onJoin(ServerPlayerEntity player) {
        PlayerComponent component = PlayerComponent.KEY.get(player);

        if (component.isAfk()) {
            component.setAfk(false);

            player.sendMessage(Text.literal("Hey! It looks like you're using the AFK feature from Apostate, which is currently banned. This is being disabled to adhere to the rulings of the Admins. Thank you for understanding!").formatted(Formatting.RED), false);
        }
    }
}
