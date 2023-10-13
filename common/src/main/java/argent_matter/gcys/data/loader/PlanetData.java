package argent_matter.gcys.data.loader;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.GregicalitySpaceClient;
import argent_matter.gcys.api.space.planet.Planet;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PlanetData extends SimpleJsonResourceReloadListener {

    private static final Set<Planet> PLANETS = new HashSet<>();
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
        List<Planet> planets = new ArrayList<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : objects.entrySet()) {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(entry.getValue(), "planet");
            Planet newPlanet = Planet.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, GregicalitySpace.LOGGER::error);
            planets.removeIf(planet -> planet.level().equals(newPlanet.level()));
            planets.add(newPlanet);
        }

        PlanetData.updatePlanets(planets);
        profiler.pop();
    }

    public static void updatePlanets(Collection<Planet> planets) {
        clear();
        for (Planet planet : planets) {
            PLANETS.add(planet);
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
        nbt.put("planets", Planet.CODEC.listOf().encodeStart(NbtOps.INSTANCE, PlanetData.planets().stream().toList())
                .getOrThrow(false, GregicalitySpace.LOGGER::error));
        buf.writeNbt(nbt);
    }

    public static void readPlanetData(FriendlyByteBuf buf) {
        try {
            PlanetData.updatePlanets(Planet.CODEC.listOf().parse(NbtOps.INSTANCE, buf.readNbt().get("planets"))
                .result()
                .orElseThrow());
        } catch (Exception e) {
            GregicalitySpace.LOGGER.error("Failed to parse planet data!");
            e.printStackTrace();
            PlanetData.updatePlanets(List.of());
        }
    }

    public static Set<Planet> planets() {
        return PLANETS;
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
     * @return The temperature of the level, or 20° for dimensions without a defined temperature
     */
    public static float getWorldTemperature(Level level) {
        if (isOrbitLevel(level.dimension())) {
            return GcysValues.ORBIT_TEMPERATURE;
        }
        return PlanetData.getPlanetFromLevel(level.dimension()).map(Planet::temperature).orElse(293.0f);
    }
}