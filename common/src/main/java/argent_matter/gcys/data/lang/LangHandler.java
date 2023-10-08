package argent_matter.gcys.data.lang;

import argent_matter.gcys.common.data.GcysMaterials;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.tterrag.registrate.providers.RegistrateLangProvider;

import static com.gregtechceu.gtceu.data.lang.LangHandler.replace;

public class LangHandler extends com.gregtechceu.gtceu.data.lang.LangHandler {
    public static void init(RegistrateLangProvider provider) {
        // blocks
        replace(provider, "block.gcys.casing_aluminium_aerospace", "Aerospace-grade Aluminium Machine Casing");
        replace(provider, "block.gcys.active_casing_rocket_motor", "Rocket motor");

        // tooltips
        multilineLang(provider, "gcys.multiblock.space_shuttle.launch", "Travel to selected Space Station\n§cRequires ID Chip!");
        provider.add("gcys.multiblock.rocket.build", "Scan Rocket");
        provider.add("gcys.multiblock.rocket.unbuild", "Destroy Rocket");

        // items
        provider.add("metaitem.id_circuit.id", "Circuit ID: %d");
        provider.add("metaitem.id_circuit.position", "Station Position: [x=%d,z=%d]");

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
    }
}
