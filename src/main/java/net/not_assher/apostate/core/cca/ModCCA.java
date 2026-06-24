package net.not_assher.apostate.core.cca;

import net.not_assher.apostate.core.cca.entity.PlayerComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

/**
 * @author Chemthunder
 */
public class ModCCA implements WorldComponentInitializer, EntityComponentInitializer {
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry cca) {}

    public void registerEntityComponentFactories(EntityComponentFactoryRegistry cca) {
        cca.registerForPlayers(
                PlayerComponent.KEY,
                PlayerComponent::new,
                RespawnCopyStrategy.ALWAYS_COPY
        );
    }
}
