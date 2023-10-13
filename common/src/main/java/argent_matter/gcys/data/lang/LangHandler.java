package argent_matter.gcys.data.lang;

import argent_matter.gcys.common.data.GcysMaterials;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.tterrag.registrate.providers.RegistrateLangProvider;

import static com.gregtechceu.gtceu.data.lang.LangHandler.replace;

public class LangHandler extends com.gregtechceu.gtceu.data.lang.LangHandler {
    public static void init(RegistrateLangProvider provider) {
        // blocks
        replace(provider, "block.gcys.aluminium_aerospace", "Aerospace-grade Aluminium Machine Casing");

        // tooltips
        multilineLang(provider, "gcys.multiblock.space_shuttle.launch", "Travel to selected Space Station\n§cRequires ID Chip!");
        provider.add("gcys.multiblock.rocket.build", "Scan Rocket");
        provider.add("gcys.multiblock.rocket.unbuild", "Destroy Rocket");

        // items
        provider.add("metaitem.id_circuit.id", "Circuit ID: %d");
        provider.add("metaitem.id_circuit.position", "Station Position: [x=%d,z=%d]");

        provider.add("metaitem.planet_id_circuit.id", "Currently Selected Planet: ");

        // materials
        replace(provider, GcysMaterials.PolyOxydiphenylenePyromellitimide.getUnlocalizedName(), "Poly(4,4'-Oxydiphenylene-Pyromellitimide)");

        // machines
        provider.add("gcys.machine.satellite_jammer.jammed", "Jammed %s");
        provider.add("gcys.machine.satellite_jammer.position", "At %s");

        // satellites
        provider.add("gcys.satellite.gps", "GPS Satellite");
        provider.add("gcys.satellite.laser", "LASER Satellite");
        provider.add("gcys.satellite.empty", "Empty Satellite");
        provider.add("gcys.satellite.dyson_swarm", "Dyson Swarm Satellite");
        provider.add("behaviour.satellite.type", "Satellite Type: ");

        provider.add("key.startRocket", "Start RocketEntity");
        provider.add("key.categories.gcys", "Gregicality Space");

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

        provider.add("message.gcys.no_fuel", "The rocket must be fueled fully, and have a valid Planet ID Chip.");
    }
}
