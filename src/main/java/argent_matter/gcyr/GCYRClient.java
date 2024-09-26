package argent_matter.gcyr;

import argent_matter.gcyr.api.space.planet.Galaxy;
import argent_matter.gcyr.api.space.planet.PlanetRing;
import argent_matter.gcyr.api.space.planet.PlanetSkyRenderer;
import argent_matter.gcyr.api.space.planet.SolarSystem;
import argent_matter.gcyr.common.gui.EntityOxygenHUD;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = GCYR.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GCYRClient {

    public static VertexFormat.Mode MODE_QUAD_STRIP = null;

    public static boolean isDysonSphereActive = false;

    public static boolean hasUpdatedPlanets = false;
    public static List<PlanetSkyRenderer> skyRenderers = new ArrayList<>();
    public static Map<ResourceLocation, ShaderInstance> skyShaders = new HashMap<>();
    public static List<SolarSystem> solarSystems = new ArrayList<>();
    public static List<PlanetRing> planetRings = new ArrayList<>();
    public static List<Galaxy> galaxies = new ArrayList<>();

    public static void init() {
        //GCYRKeyMappings.init();
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerBelowAll(GCYR.id("oxygen_tank"), new EntityOxygenHUD());
    }
}
