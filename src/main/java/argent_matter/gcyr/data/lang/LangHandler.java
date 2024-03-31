package argent_matter.gcyr.data.lang;

import argent_matter.gcyr.common.data.GCyRMaterials;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.tterrag.registrate.providers.RegistrateLangProvider;

import java.util.Set;

import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;

public class LangHandler extends com.gregtechceu.gtceu.data.lang.LangHandler {

    public static void init(RegistrateLangProvider provider) {
        // tag prefixes
        provider.add("tagprefix.moon", TagPrefix.get("moon").langValue());
        provider.add("tagprefix.mars", TagPrefix.get("mars").langValue());
        provider.add("tagprefix.venus", TagPrefix.get("venus").langValue());
        provider.add("tagprefix.mercury", TagPrefix.get("mercury").langValue());

        // materials
        replace(provider, GCyRMaterials.Bisalloy400.getUnlocalizedName(), "Bisalloy-400");
        replace(provider, GCyRMaterials.CoMnBrCatalyst.getUnlocalizedName(), "Co-Mn-Br Catalyst");
        replace(provider, GCyRMaterials.ParaPhenylenediamine.getUnlocalizedName(), "Para-Phenylenediamine");
        replace(provider, GCyRMaterials.ParaAramid.getUnlocalizedName(), "Para-Aramid");
        replace(provider, GCyRMaterials.ParaXylene.getUnlocalizedName(), "Para-Xylene");
        replace(provider, GCyRMaterials.BisTrichloromethylBenzene.getUnlocalizedName(), "Bis(trichloromethyl)benzene");

        // blocks
        replace(provider, "block.gcyr.aerospace_aluminium_casing", "Aerospace-grade Aluminium Machine Casing");

        // tooltips
        multilineLang(provider, "gcyr.multiblock.space_shuttle.launch", "Travel to selected Space Station\n§cRequires ID Chip!");
        provider.add("metaitem.gcyr.satellite_package.has_satellite", "Has satellite or space station");

        // items
        provider.add("metaitem.planet_id_circuit.id", "Currently selected planet: ");
        provider.add("metaitem.planet_id_circuit.station", "To in-orbit space station (ID: %s)");
        provider.add("item.gcyr.smithing_template.space_upgrade.ingredients", "Space Fabric");
        provider.add("item.gcyr.smithing_template.space_upgrade.additions_slot_description", "Add Space Fabric");
        provider.add("trim_material.gcyr.space", "Space Suit");
        provider.add("tooltip.gcyr.spacesuit", "Works as a space suit.");

        // machines
        provider.add("gcyr.machine.satellite_jammer.jammed", "Jammed %s");
        provider.add("gcyr.machine.satellite_jammer.position", "At %s");
        provider.add("gcyr.condition.requires_dyson_sphere.true", "Requires active Dyson Sphere");
        provider.add("gcyr.condition.requires_dyson_sphere.false", "Requires no Dyson Sphere to be active");
        provider.add("gcyr.condition.space", "Requires machine to be in space");

        // satellites
        provider.add("gcyr.satellite.gps", "GPS Satellite");
        provider.add("gcyr.satellite.laser", "LASER Satellite");
        provider.add("gcyr.satellite.empty", "Empty Satellite");
        provider.add("gcyr.satellite.dyson_swarm", "Dyson Swarm Satellite");
        provider.add("behaviour.satellite.type", "Satellite Type: %s");

        provider.add("key.startRocket", "Start RocketEntity");
        provider.add("key.categories.gcyr", "Gregicality Rocketry");

        // GUIs
        provider.add("menu.gcyr.dyson_sphere.needs_maintenance", "DYSON SPHERE REQUIRES MAINTENANCE");
        provider.add("menu.gcyr.dyson_sphere.time_since_needed_maintenance", "Time since last maintenance: %s seconds");
        provider.add("menu.gcyr.dyson_sphere.implosion_chance", "Chance of implosion: %.2f%%");
        provider.add("menu.gcyr.dyson_sphere.collapsed", "DYSON SPHERE COLLAPSED");

        provider.add("menu.gcyr.launch", "LAUNCH");

        provider.add("menu.gcyr.save_destination_station", "Save Space Station ID to keycard");
        provider.add("gcyr.multiblock.rocket.build", "Scan Rocket");
        provider.add("gcyr.multiblock.rocket.unbuild", "Destroy Rocket");

        provider.add("menu.gcyr.catalog", "Catalog");
        provider.add("menu.gcyr.back", "Back");
        provider.add("menu.gcyr.planet", "Planet");
        provider.add("menu.gcyr.moon", "Moon");
        provider.add("menu.gcyr.orbit", "Orbit");
        provider.add("menu.gcyr.no_gravity", "No Gravity");
        provider.add("menu.gcyr.space_station", "Space Station");
        provider.add("menu.gcyr.solar_system", "Solar System");
        provider.add("menu.gcyr.galaxy", "Galaxy");
        provider.add("menu.gcyr.category", "Category");
        provider.add("menu.gcyr.provided", "Provided");
        provider.add("menu.gcyr.type", "Type");
        provider.add("menu.gcyr.gravity", "Gravity");
        provider.add("menu.gcyr.oxygen", "Oxygen");
        provider.add("menu.gcyr.temperature", "Temperature");
        provider.add("menu.gcyr.oxygen.true", "Has oxygen");
        provider.add("menu.gcyr.oxygen.false", "Doesn't have oxygen");

        // messages
        provider.add("message.gcyr.no_fuel", "The rocket must be fueled fully.");
        provider.add("message.gcyr.invalid_id", "The rocket must have a valid Planet ID Chip.");
        provider.add("message.gcyr.rocket_not_good_enough", "The rocket doesn't have enough tier %s components to travel to the selected planet.");
        provider.add("message.gcyr.notice_id_changed", "The destination of your Space Station ID chip might have changed. Remember to rewrite the personal destination chip with the new data before setting a new target!");
    }
}
