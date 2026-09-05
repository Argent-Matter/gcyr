package argent_matter.gcyr.client.renderer.entity;

import argent_matter.gcyr.common.entity.RocketEntity;
import argent_matter.gcyr.util.PosWithState;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

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
    public void render(RocketEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        for (PosWithState state : entity.getBlocks()) {
            poseStack.pushPose();
            BlockPos pos = state.pos();
            poseStack.translate(pos.getX(), pos.getY(), pos.getZ());

            if (state.state().getRenderShape() == RenderShape.ENTITYBLOCK_ANIMATED &&
                    state.state().getBlock() instanceof EntityBlock entityBlock) {
                BlockEntity fakeEntity = entityBlock.newBlockEntity(BlockPos.ZERO, state.state());
                if (fakeEntity != null) {
                    // Setting the level makes ChestRenderer (and similar) use getBlockState()
                    // rather than a hardcoded south-facing fallback, giving correct orientation.
                    fakeEntity.setLevel(entity.level());
                    if (state.entityTag() != null) {
                        fakeEntity.load(state.entityTag());
                    }
                    Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(fakeEntity,
                            poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
                }
            } else {
                blockRenderer.getModelRenderer().renderModel(poseStack.last(),
                        buffer.getBuffer(Sheets.translucentCullBlockSheet()),
                        state.state(), blockRenderer.getBlockModel(state.state()),
                        1, 1, 1, packedLight, OverlayTexture.NO_OVERLAY);
            }

            poseStack.popPose();
        }

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }
}
