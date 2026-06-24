package net.not_assher.apostate.core.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.not_assher.apostate.core.index.ModCriteria;

public class ApplyApostateAdvancementEvent implements ServerPlayerEvents.Join {
    public void onJoin(ServerPlayerEntity serverPlayerEntity) {
        ModCriteria.ROOT.trigger(serverPlayerEntity);
    }
}
