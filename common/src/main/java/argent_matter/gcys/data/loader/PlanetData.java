package argent_matter.gcys.data.loader;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.GregicalitySpaceClient;
import argent_matter.gcys.api.space.planet.Planet;
import argent_matter.gcys.api.space.planet.SolarSystem;
import argent_matter.gcys.common.data.GcysDimensionTypes;
import argent_matter.gcys.common.data.GcysNetworking;
import argent_matter.gcys.common.networking.s2c.PacketReturnPlanetData;
import argent_matter.gcys.util.GcysValues;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PlanetData extends SimpleJsonResourceReloadListener {

    private static final BiMap<ResourceLocation, Planet> PLANETS = HashBiMap.create();
    private static final BiMap<ResourceLocation, List<Planet>> SOLAR_SYSTEMS = HashBiMap.create();
    private static final BiMap<ResourceKey<Level>, Planet> LEVEL_TO_PLANET = HashBiMap.create();
    private static final Set<ResourceKey<Level>> PLANET_LEVELS = new HashSet<>();
    private static final Set<ResourceKey<Level>> OXYGEN_LEVELS = new HashSet<>();

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public PlanetData() {
        super(GSON, "gcys/planets");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objects, ResourceManager resourceManager, ProfilerFiller profiler) {
        profiler.push("Gregicality Space Planet Deserialization");
        Map<ResourceLocation, Planet> planets = new HashMap<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : objects.entrySet()) {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(entry.getValue(), "planet");
            Planet newPlanet = Planet.DIRECT_CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, GregicalitySpace.LOGGER::error);
            planets.entrySet().removeIf(planet -> planet.getValue().level().equals(newPlanet.level()));
            planets.put(entry.getKey(), newPlanet);
        }

        PlanetData.updatePlanets(planets);
        profiler.pop();
    }

    public static void updatePlanets(Map<ResourceLocation, Planet> planets) {
        clear();
        for (var entry : planets.entrySet()) {
            ResourceLocation id = entry.getKey();
            Planet planet = entry.getValue();
            PLANETS.put(id, planet);
            SOLAR_SYSTEMS.computeIfAbsent(planet.solarSystem(), system -> new ArrayList<>()).add(planet);
            LEVEL_TO_PLANET.put(planet.level(), planet);
            PLANET_LEVELS.add(planet.level());
            if (planet.hasOxygen()) {
                OXYGEN_LEVELS.add(planet.level());
            }
        }
    }

    private static void clear() {
        PLANETS.clear();
        LEVEL_TO_PLANET.clear();
        OXYGEN_LEVELS.clear();
    }

    public static void writePlanetData(FriendlyByteBuf buf) {
        CompoundTag nbt = new CompoundTag();
        for (var entry : PLANETS.entrySet()) {
            nbt.put(entry.getKey().toString(), Planet.DIRECT_CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue())
                    .getOrThrow(false, GregicalitySpace.LOGGER::error));
        }
        buf.writeNbt(nbt);
    }

    public static void readPlanetData(FriendlyByteBuf buf) {
        try {
            CompoundTag nbt = buf.readNbt();
            if (nbt == null) {
                PlanetData.updatePlanets(Map.of());
                return;
            }

            Map<ResourceLocation, Planet> planets = new HashMap<>();
            for (String key : nbt.getAllKeys()) {
                planets.put(ResourceLocation.tryParse(key), Planet.DIRECT_CODEC.parse(NbtOps.INSTANCE, nbt.getCompound(key)).result().orElseThrow());
            }
            PlanetData.updatePlanets(planets);
        } catch (Exception e) {
            GregicalitySpace.LOGGER.error("Failed to parse planet data!");
            e.printStackTrace();
            PlanetData.updatePlanets(Map.of());
        }
    }

    public static Map<ResourceLocation, Planet> planets() {
        return PLANETS;
    }

    @Nullable
    public static ResourceLocation getPlanetId(Planet planet) {
        return PLANETS.inverse().get(planet);
    }

    @Nullable
    public static Planet getPlanet(ResourceLocation id) {
        return PLANETS.get(id);
    }

    public static List<Planet> getSolarSystemPlanets(ResourceLocation solarSystemId) {
        return SOLAR_SYSTEMS.get(solarSystemId);
    }

    public static Optional<Planet> getPlanetFromLevel(ResourceKey<Level> level) {
        return Optional.ofNullable(LEVEL_TO_PLANET.get(level));
    }

    public static Optional<ResourceKey<Level>> getLevelFromPlanet(Planet planet) {
        return Optional.ofNullable(LEVEL_TO_PLANET.inverse().get(planet));
    }

    public static boolean isOrbitLevel(ResourceKey<Level> level) {
        return level.location().equals(GcysDimensionTypes.SPACE_LEVEL.location());
    }

    public static boolean isPlanetLevel(Level level) {
        if (level.isClientSide && !GregicalitySpaceClient.hasUpdatedPlanets) {
            GcysNetworking.NETWORK.sendToServer(new PacketReturnPlanetData());
            GregicalitySpaceClient.hasUpdatedPlanets = true;
        }
        return PLANET_LEVELS.contains(level.dimension());
    }

    public static boolean isOxygenated(Level level) {
        return OXYGEN_LEVELS.contains(level.dimension()) || !isSpaceLevel(level);
    }

    /**
     * Checks if the level is either a planet or an orbit level.
     */
    public static boolean isSpaceLevel(Level level) {
        return isPlanetLevel(level) || isOrbitLevel(level.dimension());
    }

    /**
     * Gets the temperature of the level in kelvin.
     *
     * @return The temperature of the level, or 293K for dimensions without a defined temperature
     */
    public static float getWorldTemperature(Level level) {
        if (isOrbitLevel(level.dimension())) {
            return GcysValues.ORBIT_TEMPERATURE;
        }
        return PlanetData.getPlanetFromLevel(level.dimension()).map(Planet::temperature).orElse(293.0f);
    }
}