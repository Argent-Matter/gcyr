package argent_matter.gcyr.client.renderer.entity;

import argent_matter.gcyr.common.data.GCyRNetworking;
import argent_matter.gcyr.common.entity.RocketEntity;
import argent_matter.gcyr.common.networking.c2s.PacketRequestRocketBlocks;
import argent_matter.gcyr.util.PosWithState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RocketEntityRenderer extends EntityRenderer<RocketEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public RocketEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public ResourceLocation getTextureLocation(RocketEntity entity) {
        return ModelBakery.MISSING_MODEL_LOCATION;
    }

    @Override
    public void render(RocketEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.getBlocks().isEmpty()) {
            GCyRNetworking.NETWORK.sendToServer(new PacketRequestRocketBlocks(entity.getId()));
        }

        poseStack.pushPose();

        // render blocks
        for (PosWithState state : entity.getBlocks()) {
            poseStack.pushPose();
            BlockPos pos = state.pos();
            poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
            blockRenderer.getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(Sheets.translucentCullBlockSheet()),
                    state.state(), blockRenderer.getBlockModel(state.state()),
                    1, 1, 1, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }
}
