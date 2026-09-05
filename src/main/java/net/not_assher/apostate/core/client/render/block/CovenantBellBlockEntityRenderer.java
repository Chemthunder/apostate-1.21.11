package net.not_assher.apostate.core.client.render.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.model.BellBlockModel;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.block.entity.CovenantBellBlockEntity;
import net.not_assher.apostate.core.client.render.state.block.CovenantBellBlockEntityRenderState;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class CovenantBellBlockEntityRenderer implements BlockEntityRenderer<CovenantBellBlockEntity, CovenantBellBlockEntityRenderState> {
    public static final SpriteIdentifier BELL_BODY_TEXTURE = TexturedRenderLayers.ENTITY_SPRITE_MAPPER.map(Apostate.id("covenant_bell_body"));
    private final BellBlockModel bellBody;

    public CovenantBellBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.bellBody = new BellBlockModel(context.getLayerModelPart(EntityModelLayers.BELL));
    }

    public CovenantBellBlockEntityRenderState createRenderState() {
        return new CovenantBellBlockEntityRenderState();
    }

    public void render(CovenantBellBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        BellBlockModel.BellModelState bellModelState = new BellBlockModel.BellModelState(0, Direction.NORTH);

        this.bellBody.setAngles(bellModelState);

        queue.submitModel(
                this.bellBody,
                bellModelState,
                matrices,
                RenderLayers.entitySolid(state.hasPact ? Apostate.id("textures/entity/covenant_bell_body_slot.png") : Apostate.id("textures/entity/covenant_bell_body.png")),
                state.hasPact ? LightmapTextureManager.MAX_LIGHT_COORDINATE : state.lightmapCoordinates,
                OverlayTexture.DEFAULT_UV,
                0x000000,
                null
        );
    }

    public void updateRenderState(CovenantBellBlockEntity blockEntity, CovenantBellBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);

        state.hasPact = blockEntity.getPactStack() != null;
    }
}
