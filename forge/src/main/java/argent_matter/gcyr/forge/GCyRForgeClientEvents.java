package argent_matter.gcyr.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.client.data.GCyRParticleProviders;
import argent_matter.gcyr.client.dimension.forge.ClientModSkiesImpl;
import argent_matter.gcyr.client.dimension.renderer.DimensionEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GCyR.MOD_ID, value = Dist.CLIENT)
public class GCyRForgeClientEvents {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        GCyRParticleProviders.init();
        GCyRParticleProviders.PARTICLES.forEach(event::registerSpriteSet);
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        for (var entry : ClientModSkiesImpl.DIMENSION_SPECIAL_EFFECTS.entrySet()) {
            if (entry.getValue() instanceof DimensionEffects effects1) {
                event.register(entry.getKey(), new DimensionEffects(effects1.getRenderer()) {
                    @Override
                    public boolean renderClouds(ClientLevel level, int ticks, float tickDelta, PoseStack poseStack, double cameraX, double cameraY, double cameraZ, Matrix4f projectionMatrix) {
                        return super.renderClouds(level, ticks, tickDelta, poseStack, cameraX, cameraY, cameraZ, projectionMatrix);
                    }

                    @Override
                    public boolean renderSnowAndRain(ClientLevel level, int ticks, float tickDelta, LightTexture manager, double cameraX, double cameraY, double cameraZ) {
                        return super.renderSnowAndRain(level, ticks, tickDelta, manager, cameraX, cameraY, cameraZ);
                    }

                    @Override
                    public boolean renderSky(ClientLevel level, int ticks, float tickDelta, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean foggy, Runnable setupFog) {
                        return super.renderSky(level, ticks, tickDelta, poseStack, camera, projectionMatrix, foggy, setupFog);
                    }
                });
            }
        }
    }
}
