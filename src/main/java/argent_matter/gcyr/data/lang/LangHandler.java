package argent_matter.gcyr.data.lang;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.material.GCYRMaterials;
import argent_matter.gcyr.common.worldgen.GCYROres;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

import net.minecraft.resources.ResourceLocation;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler extends com.gregtechceu.gtceu.data.lang.LangHandler {

    public static void init(RegistrateLangProvider provider) {
        // tag prefixes
        provider.add("tagprefix.moon", TagPrefix.get("moon").langValue());
        provider.add("tagprefix.mars", TagPrefix.get("mars").langValue());
        provider.add("tagprefix.venus", TagPrefix.get("venus").langValue());
        provider.add("tagprefix.mercury", TagPrefix.get("mercury").langValue());

        // materials
        replace(provider, GCYRMaterials.Bisalloy400.getUnlocalizedName(), "Bisalloy-400");
        replace(provider, GCYRMaterials.ParaPhenylenediamine.getUnlocalizedName(), "Para-Phenylenediamine");
        replace(provider, GCYRMaterials.ParaAramid.getUnlocalizedName(), "Para-Aramid");
        replace(provider, GCYRMaterials.ParaXylene.getUnlocalizedName(), "Para-Xylene");
        replace(provider, GCYRMaterials.BisTrichloromethylBenzene.getUnlocalizedName(), "Bis(trichloromethyl)benzene");

        // blocks
        replace(provider, "block.gcyr.aerospace_aluminium_casing", "Aerospace-grade Aluminium Machine Casing");

        // tooltips
        multilineLang(provider, "multiblock.gcyr.space_shuttle.launch",
                "Travel to selected Space Station\n§cRequires ID Chip!");
        provider.add("metaitem.gcyr.satellite_package.has_satellite", "Has satellite or space station");

        // items
        provider.add("metaitem.planet_id_circuit.id", "Currently selected planet: ");
        provider.add("metaitem.planet_id_circuit.station", "To in-orbit space station (ID: %s)");
        provider.add("metaitem.planet_id_circuit.pos", "Target Position: (%s,%s,%s)");
        provider.add("item.gcyr.smithing_template.space_upgrade.ingredients", "Space Fabric");
        provider.add("item.gcyr.smithing_template.space_upgrade.additions_slot_description", "Add Space Suit Part");
        provider.add("trim_material.gcyr.space", "Space Suit");
        provider.add("trim_pattern.gcyr.space", "Space Suit Upgrade");
        provider.add("tooltip.gcyr.spacesuit", "Works as a space suit.");
        provider.add("tooltip.gcyr.spacesuit.stored", "Stored Oxygen: %s / %s mB");
        provider.add("item.gcyr.space_suit_thermal_upgrade_smithing_template", "Space Suit Thermal Upgrade");
        provider.add("item.gcyr.smithing_template.space_suit_thermal_upgrade.ingredients", "Thermal Fabric");
        provider.add("item.gcyr.smithing_template.space_suit_thermal_upgrade.additions_slot_description",
                "Add Thermal Fabric");
        provider.add("tooltip.gcyr.heat_shielded", "Protects from extreme heat.");
        provider.add("tooltip.gcyr.freeze_protected", "Protects from extreme cold.");

        // machines
        provider.add("gcyr.machine.satellite_jammer.jammed", "Jammed %s");
        provider.add("gcyr.machine.satellite_jammer.position", "At %s");
        provider.add("gcyr.condition.requires_dyson_sphere.true", "Requires active Dyson Sphere");
        provider.add("gcyr.condition.requires_dyson_sphere.false", "Requires no Dyson Sphere to be active");
        provider.add("gcyr.condition.space", "Requires machine to be in space");

        // satellites
        provider.add("satellite.gcyr.gps", "GPS Satellite");
        provider.add("satellite.gcyr.laser", "LASER Satellite");
        provider.add("satellite.gcyr.empty", "Empty Satellite");
        provider.add("satellite.gcyr.dyson_swarm", "Dyson Swarm Satellite");

        provider.add("key.launchRocket", "Launch Rocket");
        provider.add("key.categories.gcyr", "Gregicality Rocketry");

        // GUIs
        provider.add("menu.gcyr.dyson_sphere.needs_maintenance", "DYSON SPHERE REQUIRES MAINTENANCE");
        provider.add("menu.gcyr.dyson_sphere.time_since_needed_maintenance", "Time since last maintenance: %s seconds");
        provider.add("menu.gcyr.dyson_sphere.implosion_chance", "Chance of implosion: %.2f%%");
        provider.add("menu.gcyr.dyson_sphere.collapsed", "DYSON SPHERE COLLAPSED");

        provider.add("menu.gcyr.launch", "LAUNCH");
        provider.add("menu.gcyr.rocket.unbuild", "UNBUILD");
        provider.add("menu.gcyr.rocket.thrust", "Thrust: %s m/s");

        provider.add("menu.gcyr.save_destination_position", "Save Launch platform position to ID Chip");
        provider.add("multiblock.gcyr.rocket.build", "Scan Rocket");
        provider.add("multiblock.gcyr.rocket.unbuild", "Destroy Rocket");
        provider.add("multiblock.gcyr.space_station.pack", "Pack station");
        provider.add("multiblock.gcyr.space_station.package_slot.tooltip", "Space Station Package here");
        provider.add("multiblock.gcyr.space_station.keycard_slot.tooltip", "Configured keycard here");

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
        provider.add("menu.gcyr.rocket_tier", "Tier %d Rocket");
        provider.add("menu.gcyr.requires_heat_shielding", "Requires Heat Shielding");
        provider.add("menu.gcyr.requires_insulation", "Requires Insulation");

        // messages
        provider.add("message.gcyr.no_fuel",
                "The rocket must be fueled fully. Has %s mB fuel, destination requires %s mB.");
        provider.add("message.gcyr.invalid_id", "The rocket must have a valid Planet ID Chip.");
        provider.add("message.gcyr.rocket_not_good_enough",
                "The rocket doesn't have enough tier %s components to travel to the selected planet.");
        provider.add("message.gcyr.notice_id_changed",
                "The destination of your Space Station ID chip might have changed. Remember to rewrite the personal destination chip with the new data before setting a new target!");
        provider.add("message.gcyr.already_at_destination", "Already at destination specified by ID!");

        // JEI ore vein labels
        GTRegistries.ORE_VEINS.unfreeze();
        GCYROres.init();
        for (GTOreDefinition oreDefinition : GTRegistries.ORE_VEINS) {
            ResourceLocation key = GTRegistries.ORE_VEINS.getKey(oreDefinition);
            if (GCYR.MOD_ID.equals(key.getNamespace())) {
                provider.add("gtceu.jei.ore_vein." + key.getPath(),
                        RegistrateLangProvider.toEnglishName(key.getPath()));
            }
        }

        // biomes
        provider.add("biome.gcyr.lunar_mare", "Lunar Mare");
        provider.add("biome.gcyr.lunar_plains", "Lunar Plains");
        provider.add("biome.gcyr.martian_canyon_creek", "Martian Canyon Creek");
        provider.add("biome.gcyr.martian_polar_caps", "Martian Polar Caps");
        provider.add("biome.gcyr.martian_wastelands", "Martian Wastelands");
        provider.add("biome.gcyr.mercury_deltas", "Mercury Deltas");
        provider.add("biome.gcyr.space", "Space");
        provider.add("biome.gcyr.venus_barren_plains", "Venus Barren Plains");
        provider.add("biome.gcyr.venus_eroded_plains", "Venus Eroded Plains");

        // planets
        provider.add("category.gcyr.solar_system", "Sol");
        provider.add("category.gcyr.alpha_centauri", "Alpha Centauri");
        provider.add("category.gcyr.proxima_centauri", "Proxima Centauri");
        provider.add("category.gcyr.milky_way", "Milky Way");

        // dimension names
        provider.add("dimension.minecraft.overworld", "Earth");
        provider.add("dimension.gcyr.luna", "Moon");
        provider.add("dimension.gcyr.luna_orbit", "Lunar Orbit");
        provider.add("dimension.gcyr.mars", "Mars");
        provider.add("dimension.gcyr.mars_orbit", "Mars Orbit");
        provider.add("dimension.gcyr.mercury", "Mercury");
        provider.add("dimension.gcyr.mercury_orbit", "Mercury Orbit");
        provider.add("dimension.gcyr.overworld_orbit", "Earth Orbit");
        provider.add("dimension.gcyr.venus", "Venus");
        provider.add("dimension.gcyr.venus_orbit", "Venus Orbit");
        provider.add("dimension.gcyr.proxima_centauri_b", "Proxima b");
        provider.add("dimension.gcyr.black_hole_orbit", "Black Hole Orbit");

        // behaviours
        provider.add("behaviour.gps_tracker.track_entity", "Track Entity");

        // gui
        provider.add("gui.gcyr.planet_selector", "Planet Selector");
    }
}
