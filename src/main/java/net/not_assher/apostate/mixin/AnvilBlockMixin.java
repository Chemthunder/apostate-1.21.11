package net.not_assher.apostate.mixin;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.not_assher.apostate.core.index.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

/**
 * @author Chemthunder
 */
@Mixin(value = AnvilBlock.class)
public abstract class AnvilBlockMixin {
    @Inject(method = "onLanding", at = @At(value = "TAIL"))
    private void apostate$createImmortalDust(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity, CallbackInfo ci) {
        Box detection = new Box(pos).expand(1);

        for (ItemEntity itemEntity : world.getEntitiesByClass(ItemEntity.class, detection, itemEntity -> true)) {
            if (itemEntity.getStack().isOf(Items.TOTEM_OF_UNDYING)) {
                Random random = new Random();

                itemEntity.discard();

                for (int i = 0; i < random.nextInt(2, 4); i++) {
                    ItemEntity droppedDust = new ItemEntity(EntityType.ITEM, world);
                    droppedDust.setStack(new ItemStack(ModItems.IMMORTAL_DUST));

                    droppedDust.setPos(pos.getX(), pos.getY(), pos.getZ());

                    world.spawnEntity(droppedDust);
                }
            }
        }
    }
}
