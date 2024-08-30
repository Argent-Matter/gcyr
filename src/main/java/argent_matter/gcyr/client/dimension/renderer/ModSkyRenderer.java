package argent_matter.gcyr.client.dimension.renderer;

import argent_matter.gcyr.GCYRClient;
import argent_matter.gcyr.api.space.planet.PlanetSkyRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.Camera;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;

public class ModSkyRenderer {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<ResourceLocation> skyShaderLocation;
    private final PlanetSkyRenderer.StarsRenderer starsRenderer;
    private final List<PlanetSkyRenderer.SkyObject> skyObjects;
    private final int horizonAngle;
    private final boolean shouldRenderWhileRaining;

    private VertexBuffer starsBuffer;
    private int starsCount;

    public ModSkyRenderer(PlanetSkyRenderer skyRenderer) {
        this.skyShaderLocation = skyRenderer.skyShaderLocation();
        this.starsRenderer = skyRenderer.starsRenderer();
        this.skyObjects = skyRenderer.skyObjects();
        this.horizonAngle = skyRenderer.horizonAngle();
        this.shouldRenderWhileRaining = !skyRenderer.weatherEffects().equals(PlanetSkyRenderer.WeatherEffects.NONE);
    }

    public void render(ClientLevel level, int ticks, float tickDelta, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean foggy) {

        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        Minecraft minecraft = Minecraft.getInstance();

        if (shouldRenderWhileRaining && level.isRaining()) {
            return;
        }
        // Cancel rendering if the player is in fog, i.e. in lava or powdered snow
        if (SkyUtil.isSubmerged(camera)) {
            return;
        }

        ShaderInstance lastShader = RenderSystem.getShader();
        skyShaderLocation.ifPresent(shaderId -> {
            RenderSystem.setShader(() -> GCYRClient.skyShaders.get(shaderId));
        });

        SkyUtil.preRender(level, minecraft.levelRenderer, camera, projectionMatrix, bufferBuilder, horizonAngle, poseStack, tickDelta);

        RenderSystem.setShader(() -> lastShader);

        // Stars
        if (this.starsRenderer.fastStars() > 0) {
            int stars = (!minecraft.options.graphicsMode().get().equals(GraphicsStatus.FAST) ? this.starsRenderer.fancyStars() : this.starsRenderer.fastStars());
            starsBuffer = renderStars(level, poseStack, tickDelta, bufferBuilder, stars, this.starsRenderer, projectionMatrix);
        }

        // Render all sky objects
        for (PlanetSkyRenderer.SkyObject skyObject : this.skyObjects) {

            float scale = skyObject.scale();
            Vector3f rotation = skyObject.rotation();
            switch (skyObject.renderType()) {
                case STATIC -> {
                } // Do not modify the scale or rotation
                case DYNAMIC ->
                    rotation = new Vector3f(level.getTimeOfDay(tickDelta) * 360.0f + rotation.x(), rotation.y(), rotation.z());
                case SCALING -> scale *= SkyUtil.getScale();
                case DEBUG -> rotation = new Vector3f(60, 0, 0); // Test things without restarting Minecraft
            }
            SkyUtil.render(poseStack, bufferBuilder, skyObject.texture(), skyObject.colour(), rotation, scale, skyObject.blending());
        }

        SkyUtil.postRender(minecraft.gameRenderer, level, tickDelta);
    }

    private VertexBuffer renderStars(ClientLevel level, PoseStack poseStack, float tickDelta, BufferBuilder bufferBuilder, int stars, PlanetSkyRenderer.StarsRenderer starsRenderer, Matrix4f projectionMatrix) {

        SkyUtil.startRendering(poseStack, new Vector3f(-30.0f, 0.0f, level.getTimeOfDay(tickDelta) * 360.0f));
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        createStarBuffer(bufferBuilder, stars);

        if (!starsRenderer.daylightVisible()) {
            float rot = level.getStarBrightness(tickDelta);
            RenderSystem.setShaderColor(rot, rot, rot, rot);
        } else {
            RenderSystem.setShaderColor(0.8f, 0.8f, 0.8f, 0.8f);
        }

        FogRenderer.setupNoFog();
        starsBuffer.bind();
        starsBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionColorShader());
        VertexBuffer.unbind();

        poseStack.popPose();
        return starsBuffer;
    }

    private void createStarBuffer(BufferBuilder bufferBuilder, int stars) {
        if (starsBuffer != null) {
            if (starsCount == stars) {
                return;
            }
            starsBuffer.close();
        }

        starsBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        starsCount = stars;
        BufferBuilder.RenderedBuffer renderedBuffer = SkyUtil.renderStars(bufferBuilder, stars, starsRenderer.colouredStars());
        starsBuffer.bind();
        starsBuffer.upload(renderedBuffer);
        VertexBuffer.unbind();
    }
}
