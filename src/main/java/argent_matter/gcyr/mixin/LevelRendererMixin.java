package argent_matter.gcyr.mixin;

import argent_matter.gcyr.GCyRClient;
import argent_matter.gcyr.client.dimension.renderer.DimensionEffects;
import argent_matter.gcyr.client.dimension.renderer.DimensionRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow @Final private static ResourceLocation SUN_LOCATION;

    @Shadow
    private ClientLevel level;
    @Final
    @Shadow
    private Minecraft minecraft;
    @Shadow private int ticks;

    @ModifyArg(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"))
    private ResourceLocation gcyr$removeSunMoon(int i, ResourceLocation texture) {
        if (GCyRClient.isDysonSphereActive && texture == SUN_LOCATION) {
            return GCyRClient.DYSON_SUN_LOCATION;
        }
        return texture;
    }

    @Inject(at = @At("HEAD"), method = "renderSnowAndRain", cancellable = true)
    private void gcyr$renderSnowAndRain(LightTexture manager, float tickDelta, double x, double y, double z, CallbackInfo info) {
        if (this.minecraft.level != null) {
            DimensionSpecialEffects effects = DimensionEffects.forType(this.level.dimensionType());
            if (effects instanceof DimensionRenderer dimensionRenderer && dimensionRenderer.shouldRenderSnowAndRain()) {
                dimensionRenderer.renderSnowAndRain(this.level, this.ticks, tickDelta, manager, x, y, z);
                info.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "renderClouds", cancellable = true)
    private void gcyr$renderClouds(PoseStack matrices, Matrix4f matrix4f, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo info) {
        if (this.minecraft.level != null) {
            DimensionSpecialEffects effects = DimensionEffects.forType(this.level.dimensionType());
            if (effects instanceof DimensionRenderer dimensionRenderer && dimensionRenderer.shouldRenderClouds()) {
                dimensionRenderer.renderClouds(level, ticks, tickDelta, matrices, cameraX, cameraY, cameraZ, matrix4f);
                info.cancel();
            }
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER, ordinal = 0, remap = false), method = "renderSky", cancellable = true)
    private void gcyr$renderSky(PoseStack matrices, Matrix4f matrix4f, float tickDelta, Camera camera, boolean isFoggy, Runnable setupFog, CallbackInfo info) {
        if (this.minecraft.level != null) {
            DimensionSpecialEffects effects = DimensionEffects.forType(this.level.dimensionType());
            if (effects instanceof DimensionRenderer dimensionRenderer && dimensionRenderer.shouldRenderSky()) {
                dimensionRenderer.renderSky(this.level, this.ticks, tickDelta, matrices, camera, matrix4f, isFoggy, setupFog);
                info.cancel();
            }
        }
    }
}
