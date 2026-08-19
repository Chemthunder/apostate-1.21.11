package net.not_assher.apostate.core.cca.entity;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import net.not_assher.apostate.core.Apostate;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class LassoProjectileComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<LassoProjectileComponent> KEY = ComponentRegistry.getOrCreate(
            Apostate.id("lasso_projectile"),
            LassoProjectileComponent.class
    );
    private final ProjectileEntity projectile;

    private boolean lasso = false;

    public LassoProjectileComponent(ProjectileEntity projectile) {
        this.projectile = projectile;
    }

    public void tick() {
        if (lasso) {
            World world = projectile.getEntityWorld();

            world.addParticleClient(
                    new DustParticleEffect(0xFF795e3d, 1.2F),
                    projectile.lastX,
                    projectile.lastY,
                    projectile.lastZ,
                    0,
                    0,
                    0
            );
        }
    }

    public void sync() {
        KEY.sync(projectile);
    }

    public void readData(ReadView readView) {
        lasso = readView.getBoolean("Lasso", false);
    }

    public void writeData(WriteView writeView) {
        writeView.putBoolean("Lasso", lasso);
    }

    public boolean isLasso() {
        return lasso;
    }

    public void setLasso(boolean lasso) {
        this.lasso = lasso;
        sync();
    }
}
