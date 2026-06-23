package net.not_assher.apostate.core.cca;

import net.not_assher.apostate.core.cca.world.BountyManagerComponent;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

/**
 * @author Chemthunder
 */
public class ModCCA implements WorldComponentInitializer {
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry cca) {
        cca.register(
                BountyManagerComponent.KEY,
                BountyManagerComponent::new
        );
    }
}
