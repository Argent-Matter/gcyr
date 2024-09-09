package argent_matter.gcyr.mixin;

import argent_matter.gcyr.client.dimension.ClientModSkies;
import argent_matter.gcyr.client.dimension.renderer.DimensionEffects;
import argent_matter.gcyr.client.dimension.renderer.DimensionRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow
    private ClientLevel level;
    @Final
    @Shadow
    private Minecraft minecraft;
    @Shadow private int ticks;

    /* TODO find out how to make sodium happy with this. :sadge:
    @WrapWithCondition(method = "renderSky",
            slice = @Slice(
              from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V")
            ),
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferUploader;drawWithShader(Lcom/mojang/blaze3d/vertex/BufferBuilder$RenderedBuffer;)V"))
    private boolean gcyr$removeSunMoon(BufferBuilder.RenderedBuffer buffer, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean bl, Runnable skyFogSetup) {
        if (GCYRClient.isDysonSphereActive) {
            buffer.release();
            return false;
        }
        return true;
    }
     */

    @Inject(at = @At("HEAD"), method = "renderSnowAndRain", cancellable = true)
    private void gcyr$renderSnowAndRain(LightTexture manager, float tickDelta, double x, double y, double z, CallbackInfo info) {
        if (this.minecraft.level != null) {
            DimensionSpecialEffects effects = ClientModSkies.DIMENSION_SPECIAL_EFFECTS.get(this.level.dimension().location());
            if (effects instanceof DimensionRenderer dimensionRenderer && dimensionRenderer.shouldRenderSnowAndRain()) {
                dimensionRenderer.renderSnowAndRain(this.level, this.ticks, tickDelta, manager, x, y, z);
                info.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "renderClouds", cancellable = true)
    private void gcyr$renderClouds(PoseStack poseStack, Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
        if (this.minecraft.level != null) {
            DimensionSpecialEffects effects = ClientModSkies.DIMENSION_SPECIAL_EFFECTS.get(this.level.dimension().location());
            if (effects instanceof DimensionRenderer dimensionRenderer && dimensionRenderer.shouldRenderClouds()) {
                dimensionRenderer.renderClouds(level, ticks, partialTick, poseStack, camX, camY, camZ, projectionMatrix);
                ci.cancel();
            }
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER, ordinal = 0, remap = false), method = "renderSky", cancellable = true)
    private void gcyr$renderSky(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci) {
        if (this.minecraft.level != null) {
            DimensionSpecialEffects effects = ClientModSkies.DIMENSION_SPECIAL_EFFECTS.get(this.level.dimension().location());
            if (effects instanceof DimensionRenderer dimensionRenderer && dimensionRenderer.shouldRenderSky()) {
                PoseStack poseStack = new PoseStack();
                poseStack.mulPose(frustumMatrix);
                dimensionRenderer.renderSky(this.level, this.ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, skyFogSetup);
                ci.cancel();
            }
        }
    }
}
