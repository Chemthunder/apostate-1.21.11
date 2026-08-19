package net.not_assher.apostate.core.cca.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.not_assher.apostate.core.Apostate;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class LassoComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<LassoComponent> KEY = ComponentRegistry.getOrCreate(
            Apostate.id("lasso"),
            LassoComponent.class
    );
    private final LivingEntity living;

    private @Nullable LivingEntity handler;
    private @Nullable Vec3d handlerPos;

    private int duration = 0;

    public LassoComponent(LivingEntity living) {
        this.living = living;
    }

    public void tick() {
        if (duration > 0) {
            if (handler != null) {
                if (living.getEntityPos().distanceTo(handler.getEntityPos()) > 2.5F) {
                    living.setVelocity(living.getEntityPos().subtract(handler.getEntityPos()).normalize().negate().multiply(1));
                }

                World world = handler.getEntityWorld();

                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(
                            new DustParticleEffect(0xFF795e3d, 1.3F),
                            living.getX(),
                            living.getY(),
                            living.getZ(),
                            15,
                            living.getWidth() / 2,
                            living.getHeight() / 2,
                            living.getWidth() / 2,
                            0.02F
                    );
                }

                duration--;

                if (duration == 0) {
                    sync();
                }
            } else {
                duration = 0;
            }
            sync();
        }
    }

    public void sync() {
        KEY.sync(living);
    }

    public void remove() {
        handler = null;
        handlerPos = null;
        duration = 0;
        sync();
    }

    public void readData(ReadView readView) {
        handlerPos = readView.read("HandlerPos", Vec3d.CODEC).orElse(null);

        duration = readView.getInt("Duration", 0);

        if (!readView.getBoolean("Active", false)) {
            handlerPos = null;
            handler = null;
            duration = 0;
        }
    }

    public void writeData(WriteView writeView) {
        if (handlerPos != null) {
            writeView.put("HandlerPos", Vec3d.CODEC, handlerPos);
        }

        writeView.putInt("Duration", duration);

        writeView.putBoolean("Active", duration > 0);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        sync();
    }

    @Nullable
    public LivingEntity getHandler() {
        return handler;
    }

    public void setHandler(@Nullable LivingEntity handler) {
        this.handler = handler;
        sync();
    }

    @Nullable
    public Vec3d getHandlerPos() {
        return handlerPos;
    }

    public void setHandlerPos(@Nullable Vec3d handlerPos) {
        this.handlerPos = handlerPos;
        sync();
    }
}
