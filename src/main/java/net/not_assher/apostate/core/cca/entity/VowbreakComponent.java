package net.not_assher.apostate.core.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.index.data.ModDamageTypes;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class VowbreakComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<VowbreakComponent> KEY = ComponentRegistry.getOrCreate(
            Apostate.id("vowbreak"),
            VowbreakComponent.class
    );
    private final PlayerEntity player;

    public static final int TIME = 40;

    private int tickAge = (TIME * 20);
    private int tickUp = 0;
    private boolean active = false;

    public VowbreakComponent(PlayerEntity player) {
        this.player = player;
    }

    public void tick() {
        World world = player.getEntityWorld();

        Vec3d startPos = player.getEntityPos();
        Vec3d endPos = new Vec3d(startPos.x, startPos.y + 1, startPos.z);

        if (active) {
            tickAge--;
            tickUp++;

            if (tickAge < 0) {
                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(
                            ParticleTypes.SOUL_FIRE_FLAME,
                            startPos.x,
                            startPos.y,
                            startPos.z,
                            50,
                            0,
                            0,
                            0,
                            0.2F
                    );

                    serverWorld.playSound(
                            null,
                            startPos.x,
                            startPos.y,
                            startPos.z,
                            SoundEvents.ENTITY_ALLAY_DEATH,
                            SoundCategory.PLAYERS,
                            5,
                            0.1F
                    );

                    if (!serverWorld.isClient()) {
                        player.damage(serverWorld, player.getDamageSources().create(ModDamageTypes.VOWBREAK), player.getMaxHealth() * player.getMaxHealth());
                    }
                }

                active = false;
                tickAge = (TIME * 20);
                sync();
            }

            for (int i = 0; i < tickUp / 20; i++) {
                float bound = 6F;

                Random random = new Random();

                Vec3d spawnPos = new Vec3d(
                        startPos.x + random.nextFloat(-bound, bound),
                        startPos.y + random.nextFloat(-bound, bound),
                        startPos.z + random.nextFloat(-bound, bound)
                );

                Vec3d velocity = endPos.subtract(spawnPos).normalize().negate().multiply(-tickUp / 1500F);

                world.addImportantParticleClient(
                        ParticleTypes.SOUL,
                        spawnPos.x,
                        spawnPos.y,
                        spawnPos.z,
                        velocity.x,
                        velocity.y,
                        velocity.z
                );
            }
        }
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readData(ReadView readView) {
        tickAge = readView.getInt("TickAge", (TIME * 20));
        tickUp = readView.getInt("TickUp", 0);
        active = readView.getBoolean("Active", false);
    }

    public void writeData(WriteView writeView) {
        writeView.putInt("TickAge", tickAge);
        writeView.putInt("TickUp", tickUp);
        writeView.putBoolean("Active", active);
    }

    public int getTickAge() {
        return tickAge;
    }

    public void setTickAge(int tickAge) {
        this.tickAge = tickAge;
        sync();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        sync();
    }

    public int getTickUp() {
        return tickUp;
    }

    public void setTickUp(int tickUp) {
        this.tickUp = tickUp;
        sync();
    }

    public void targetAndLoad() {
        active = true;
        tickAge = (TIME * 20);
        tickUp = 0;
        sync();
    }
}
