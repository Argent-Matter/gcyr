package argent_matter.gcyr.client.dimension.renderer;

import argent_matter.gcyr.api.space.planet.PlanetSkyRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class DimensionEffects extends DimensionSpecialEffects implements DimensionRenderer {
    private final PlanetSkyRenderer renderer;
    private final ModSkyRenderer skyRenderer;

    public DimensionEffects(PlanetSkyRenderer renderer) {
        super(192, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
        this.renderer = renderer;
        this.skyRenderer = new ModSkyRenderer(renderer);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
        if (renderer.effects().type().equals(PlanetSkyRenderer.DimensionEffectType.COLORED_HORIZON)) {
            int colour = renderer.effects().colour();
            return new Vec3((colour >> 16 & 0xFF) / 255.0f, (colour >> 8 & 0xFF) / 255.0f, (colour & 0xFF) / 255.0f);
        }
        return color.multiply(sunHeight * 0.94f + 0.06f, sunHeight * 0.94f + 0.06f, sunHeight * 0.91f + 0.09f);
    }

    @Override
    public boolean isFoggyAt(int camX, int camY) {
        return renderer.effects().type().isFoggy();
    }

    @Override
    public float[] getSunriseColor(float skyAngle, float tickDelta) {
        PlanetSkyRenderer.DimensionEffectType type = renderer.effects().type();
        if (type == PlanetSkyRenderer.DimensionEffectType.FOGGY_REVERSED || type == PlanetSkyRenderer.DimensionEffectType.NONE) {
            return null;
        }
        return super.getSunriseColor(skyAngle, tickDelta);
    }

    @Override
    public boolean renderClouds(ClientLevel level, int ticks, float tickDelta, PoseStack poseStack, double cameraX, double cameraY, double cameraZ, Matrix4f projectionMatrix) {
        return switch (renderer.cloudEffects()) {
            case NONE -> true;
            case VANILLA -> false;
        };
    }

    @Override
    public boolean shouldRenderClouds() {
        return renderer.cloudEffects() != PlanetSkyRenderer.CloudEffects.VANILLA;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float tickDelta, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean foggy, Runnable setupFog) {
        setupFog.run();
        skyRenderer.render(level, ticks, tickDelta, poseStack, camera, projectionMatrix, foggy);
        return true;
    }

    @Override
    public boolean shouldRenderSky() {
        return true;
    }

    @Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float tickDelta, LightTexture manager, double cameraX, double cameraY, double cameraZ) {
        return switch (renderer.weatherEffects()) {
            case NONE -> true;
            case VANILLA -> false;
        };
    }

    @Override
    public boolean shouldRenderSnowAndRain() {
        return renderer.weatherEffects() != PlanetSkyRenderer.WeatherEffects.VANILLA;
    }
}