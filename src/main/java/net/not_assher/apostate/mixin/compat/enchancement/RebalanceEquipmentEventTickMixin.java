package net.not_assher.apostate.mixin.compat.enchancement;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import moriyashiine.enchancement.common.event.config.RebalanceEquipmentEvent;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = RebalanceEquipmentEvent.Tick.class)
public abstract class RebalanceEquipmentEventTickMixin {

    @WrapMethod(method = "tick")
    private void apostate$removethefuckingding(ServerWorld world, Entity entity, Operation<Void> original) {}
}
