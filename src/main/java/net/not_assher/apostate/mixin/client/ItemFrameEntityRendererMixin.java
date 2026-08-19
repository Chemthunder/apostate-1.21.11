package net.not_assher.apostate.mixin.client;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.entity.state.ItemFrameEntityRenderState;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.not_assher.apostate.core.index.data.ModItemTags;
import net.not_assher.apostate.core.item.BountyPosterItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(value = ItemFrameEntityRenderer.class)
public abstract class ItemFrameEntityRendererMixin<T extends ItemFrameEntity> extends EntityRenderer<T, ItemFrameEntityRenderState> {
    protected ItemFrameEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/decoration/ItemFrameEntity;Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;F)V",
            at = @At(
                    value = "TAIL"
            )
    )
    private void apostate$hideItemFrames(T itemFrameEntity, ItemFrameEntityRenderState itemFrameEntityRenderState, float f, CallbackInfo ci) {
        if (itemFrameEntity.getHeldItemStack().isIn(ModItemTags.HIDES_FRAME)) {
            itemFrameEntityRenderState.invisible = true;
        }
    }
}
