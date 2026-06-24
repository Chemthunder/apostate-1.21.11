package net.not_assher.apostate.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.not_assher.apostate.core.index.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = SugarCaneBlock.class)
public abstract class SugarcaneBlockMixin extends Block {
    public SugarcaneBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        createDrops(world, pos);
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (world.getServer() != null) {
            if (world.getServer().getOverworld() != null) {
                createDrops(world.getServer().getOverworld(), pos);
            }
        }
        super.onBroken(world, pos, state);
    }

    @Unique
    private static void createDrops(World world, BlockPos pos) {
        if (world.getRandom().nextBetween(1, 3) == 2) {
            ItemStack toDrop = new ItemStack(ModItems.PARCHMENT);

            toDrop.setCount(world.getRandom().nextBetween(1, 3));

            dropStack(
                    world,
                    pos,
                    toDrop
            );
        }
    }
}
