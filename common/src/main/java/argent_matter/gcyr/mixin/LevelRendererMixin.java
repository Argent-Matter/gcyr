package argent_matter.gcyr.mixin;

import argent_matter.gcyr.GCyRClient;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @WrapWithCondition(method = "renderSky",
            slice = @Slice(
              from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V")
            ),
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferUploader;drawWithShader(Lcom/mojang/blaze3d/vertex/BufferBuilder$RenderedBuffer;)V"))
    private boolean gcyr$removeSunMoon(BufferBuilder.RenderedBuffer buffer, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean bl, Runnable skyFogSetup) {
        if (GCyRClient.isDysonSphereActive) {
            buffer.release();
            return false;
        }
        return true;
    }
}
