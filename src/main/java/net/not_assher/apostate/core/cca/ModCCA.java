package net.not_assher.apostate.core.cca;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.not_assher.apostate.core.cca.entity.LassoComponent;
import net.not_assher.apostate.core.cca.entity.LassoProjectileComponent;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

/**
 * @author Chemthunder
 */
public class ModCCA implements EntityComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry cca) {
        cca.registerForPlayers(
                PlayerComponent.KEY,
                PlayerComponent::new,
                RespawnCopyStrategy.ALWAYS_COPY
        );

        cca.beginRegistration(
                LivingEntity.class,
                LassoComponent.KEY
        ).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LassoComponent::new);

        cca.beginRegistration(
                ProjectileEntity.class,
                LassoProjectileComponent.KEY
        ).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LassoProjectileComponent::new);
    }
}
