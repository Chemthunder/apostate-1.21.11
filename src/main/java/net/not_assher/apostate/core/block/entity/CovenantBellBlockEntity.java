package net.not_assher.apostate.core.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.not_assher.apostate.core.index.ModBlockEntityTypes;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.item.component.PactComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

/**
 * @author Chemthunder
 */
public class CovenantBellBlockEntity extends BlockEntity {
    public static final int DURATION = (5 * 20);

    private @Nullable ItemStack pactStack = null;

    private boolean active = false;
    private int ticks = 0;

    public CovenantBellBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.COVENANT_BELL, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state, @NotNull CovenantBellBlockEntity entity) {
        if (entity.active && entity.pactStack != null) {
            PactComponent pact = entity.pactStack.get(ModDataComponentTypes.STORED_PACT);

            if (pact != null) {
                if (entity.ticks == DURATION - 40) {
                    world.playSound(
                            null,
                            pos.getX(),
                            pos.getY(),
                            pos.getZ(),
                            SoundEvents.BLOCK_BELL_RESONATE,
                            SoundCategory.BLOCKS,
                            1,
                            1
                    );
                }

                if (entity.ticks < DURATION) {
                    entity.ticks++;

                    for (int i = 0; i < ticks / 12; i++) {
                        float bound = 2.1F;

                        Random random = new Random();

                        Vec3d spawnPos = new Vec3d(
                                pos.toCenterPos().x + random.nextFloat(-bound, bound),
                                pos.toCenterPos().y + random.nextFloat(-bound, bound),
                                pos.toCenterPos().z + random.nextFloat(-bound, bound)
                        );

                        Vec3d velocity = pos.toCenterPos().subtract(spawnPos).normalize().negate().multiply(-0.1F);

                        world.addParticleClient(
                                ParticleTypes.SOUL,
                                spawnPos.x,
                                spawnPos.y,
                                spawnPos.z,
                                velocity.x,
                                velocity.y,
                                velocity.z
                        );
                    }

                    for (PlayerEntity serverPlayer : world.getPlayers()) {
                        if (Objects.equals(serverPlayer.getName().getString(), pact.signer())) {
                            for (int i = 0; i < ticks / 12; i++) {
                                float bound = 1.6F;

                                Random random = new Random();

                                Vec3d spawnPos = new Vec3d(
                                        serverPlayer.getX() + random.nextFloat(-bound, bound),
                                        (serverPlayer.getY() + 1.0F) + random.nextFloat(-bound, bound),
                                        serverPlayer.getZ() + random.nextFloat(-bound, bound)
                                );

                                Vec3d velocity = serverPlayer.getEntityPos().subtract(spawnPos).normalize().negate().multiply(-0.1F);

                                world.addParticleClient(
                                        ParticleTypes.SOUL,
                                        spawnPos.x,
                                        spawnPos.y,
                                        spawnPos.z,
                                        velocity.x,
                                        velocity.y,
                                        velocity.z
                                );
                            }
                            break;
                        }
                    }

                    if (entity.ticks == DURATION) {
                        entity.active = false;
                        entity.ticks = 0;
                        this.activate(world, pos, state, entity.pactStack, entity);
                        this.updateListeners();
                    }
                }
            }
        } else {
            if (entity.active) {
                entity.active = false;
                entity.ticks = 0;
                this.updateListeners();
            }
        }
    }

    public void trigger(CovenantBellBlockEntity entity) {
        entity.ticks = 0;
        entity.active = true;

        if (entity.getWorld() != null) {
            entity.getWorld().playSound(
                    null,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    SoundEvents.BLOCK_BELL_USE,
                    SoundCategory.BLOCKS,
                    1,
                    new Random().nextFloat(0.1F, 0.3F)
            );
        }
        this.updateListeners();
    }

    public void activate(World world, BlockPos pos, BlockState state, ItemStack pactStack, @NotNull CovenantBellBlockEntity entity) {
        MinecraftServer server = world.getServer();

        PactComponent pact = pactStack.get(ModDataComponentTypes.STORED_PACT);

        if (pact != null) {
            if (server != null && world instanceof ServerWorld serverWorld) {
                PlayerManager manager = server.getPlayerManager();

                for (ServerPlayerEntity serverPlayer : manager.getPlayerList()) {
                    if (Objects.equals(serverPlayer.getName().getString(), pact.signer())) {
                        BlockPos upwardsPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());

                        if (!world.getBlockState(upwardsPos).isAir()) {
                            upwardsPos = new BlockPos(pos.getX(), pos.getY() - 3, pos.getZ());

                            if (!world.getBlockState(upwardsPos).isAir()) {
                                upwardsPos = null;
                            }
                        }

                        if (upwardsPos != null) {
                            serverPlayer.teleportTo(new TeleportTarget(
                                    serverWorld,
                                    upwardsPos.toCenterPos(),
                                    Vec3d.ZERO,
                                    serverPlayer.getYaw(),
                                    serverPlayer.getPitch(),
                                    TeleportTarget.NO_OP
                            ));

                            serverWorld.spawnParticles(
                                    ParticleTypes.SOUL_FIRE_FLAME,
                                    upwardsPos.toCenterPos().x,
                                    upwardsPos.toCenterPos().y,
                                    upwardsPos.toCenterPos().z,
                                    25,
                                    0,
                                    0,
                                    0,
                                    0.2F
                            );
                        } else {
                            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_WITHER_HURT, SoundCategory.BLOCKS, 1, 1);
                        }
                        break;
                    }
                }
            }
        }
    }

    protected void readData(ReadView view) {
        pactStack = view.read("Pact", ItemStack.CODEC).orElse(null);

        active = view.getBoolean("Active", false);
        ticks = view.getInt("Ticks", 0);
    }

    protected void writeData(WriteView view) {
        if (pactStack != null) {
            view.put("Pact", ItemStack.CODEC, pactStack);
        }

        view.putBoolean("Active", active);
        view.putInt("Ticks", ticks);
    }

    @Nullable
    public ItemStack getPactStack() {
        return pactStack;
    }

    public void setPactStack(@Nullable ItemStack pactStack) {
        this.pactStack = pactStack;
        this.updateListeners();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        this.updateListeners();
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
        this.updateListeners();
    }

    public void updateListeners() {
        this.markDirty();
        if (this.getWorld() != null) {
            this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
        }
    }
}
