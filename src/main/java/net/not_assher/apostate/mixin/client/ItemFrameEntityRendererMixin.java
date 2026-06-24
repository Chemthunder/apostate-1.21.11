package net.not_assher.apostate.mixin.client;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.entity.state.ItemFrameEntityRenderState;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.not_assher.apostate.core.item.BountyPosterItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemFrameEntityRenderer.class)
public abstract class ItemFrameEntityRendererMixin<T extends ItemFrameEntity> extends EntityRenderer<T, ItemFrameEntityRenderState> {
    protected ItemFrameEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/decoration/ItemFrameEntity;Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;F)V", at = @At(value = "TAIL"))
    private void aspos(T itemFrameEntity, ItemFrameEntityRenderState itemFrameEntityRenderState, float f, CallbackInfo ci) {
        if (itemFrameEntity.getHeldItemStack().getItem() instanceof BountyPosterItem) {
            itemFrameEntityRenderState.invisible = true;
        }
    }
}
