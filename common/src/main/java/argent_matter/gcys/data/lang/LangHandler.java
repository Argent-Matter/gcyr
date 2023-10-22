package argent_matter.gcys.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler extends com.gregtechceu.gtceu.data.lang.LangHandler {
    public static void init(RegistrateLangProvider provider) {
        // blocks
        replace(provider, "block.gcys.aerospace_aluminium_casing", "Aerospace-grade Aluminium Machine Casing");

        // tooltips
        multilineLang(provider, "gcys.multiblock.space_shuttle.launch", "Travel to selected Space Station\n§cRequires ID Chip!");
        provider.add("gcys.multiblock.rocket.build", "Scan Rocket");
        provider.add("gcys.multiblock.rocket.unbuild", "Destroy Rocket");

        // items
        provider.add("metaitem.id_circuit.id", "Circuit ID: %d");
        provider.add("metaitem.id_circuit.position", "Station Position: [x=%d,z=%d]");

        provider.add("metaitem.planet_id_circuit.id", "Currently selected planet: ");
        provider.add("metaitem.planet_id_circuit.station", "To in-orbit space station (ID: %s)");

        // materials

        // machines
        provider.add("gcys.machine.satellite_jammer.jammed", "Jammed %s");
        provider.add("gcys.machine.satellite_jammer.position", "At %s");
        provider.add("gcys.condition.requires_dyson_sphere.true", "Requires active Dyson Sphere");
        provider.add("gcys.condition.requires_dyson_sphere.false", "Requires no Dyson Sphere to be active");

        // satellites
        provider.add("gcys.satellite.gps", "GPS Satellite");
        provider.add("gcys.satellite.laser", "LASER Satellite");
        provider.add("gcys.satellite.empty", "Empty Satellite");
        provider.add("gcys.satellite.dyson_swarm", "Dyson Swarm Satellite");
        provider.add("behaviour.satellite.type", "Satellite Type: ");

        provider.add("key.startRocket", "Start RocketEntity");
        provider.add("key.categories.gcys", "Gregicality Space");

        provider.add("menu.gcys.dyson_sphere.needs_maintenance", "DYSON SPHERE REQUIRES MAINTENANCE");
        provider.add("menu.gcys.dyson_sphere.time_since_needed_maintenance", "Time since last maintenance: %s seconds");
        provider.add("menu.gcys.dyson_sphere.implosion_chance", "Chance of implosion: %.2f%%");
        provider.add("menu.gcys.dyson_sphere.collapsed", "DYSON SPHERE COLLAPSED");

        provider.add("menu.gcys.launch", "LAUNCH");

        provider.add("menu.gcys.catalog", "Catalog");
        provider.add("menu.gcys.back", "Back");
        provider.add("menu.gcys.planet", "Planet");
        provider.add("menu.gcys.moon", "Moon");
        provider.add("menu.gcys.orbit", "Orbit");
        provider.add("menu.gcys.no_gravity", "No Gravity");
        provider.add("menu.gcys.space_station", "Space Station");
        provider.add("menu.gcys.solar_system", "Solar System");
        provider.add("menu.gcys.galaxy", "Galaxy");
        provider.add("menu.gcys.category", "Category");
        provider.add("menu.gcys.provided", "Provided");
        provider.add("menu.gcys.type", "Type");
        provider.add("menu.gcys.gravity", "Gravity");
        provider.add("menu.gcys.oxygen", "Oxygen");
        provider.add("menu.gcys.temperature", "Temperature");
        provider.add("menu.gcys.oxygen.true", "Has oxygen");
        provider.add("menu.gcys.oxygen.false", "Doesn't have oxygen");

        // messages
        provider.add("message.gcys.no_fuel", "The rocket must be fueled fully, and have a valid Planet ID Chip.");
        provider.add("message.gcys.notice_id_changed", "The destination of your Space Station ID chip might have changed. Remember to rewrite the personal destination chip with the new data before setting a new target!");
    }
}
