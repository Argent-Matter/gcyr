package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.item.GpsTrackerBehaviour;
import argent_matter.gcyr.common.item.KeyCardBehaviour;
import argent_matter.gcyr.common.item.PlanetIdChipBehaviour;
import argent_matter.gcyr.common.item.StationContainerBehaviour;
import argent_matter.gcyr.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcyr.common.item.armor.trim.GCyRTrimPatterns;
import argent_matter.gcyr.data.recipe.GCyRTags;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

import static argent_matter.gcyr.api.registries.GCyRRegistries.REGISTRATE;

@SuppressWarnings("Convert2MethodRef")
public class GCyRItems {

    // region chips
    public static final ItemEntry<ComponentItem> GPS_TRACKER = REGISTRATE.item("gps_tracker", ComponentItem::create)
            .lang("GPS Tracker")
            .properties(p -> p.stacksTo(16))
            .onRegister(attach(new GpsTrackerBehaviour()))
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> KEYCARD = REGISTRATE.item("keycard", ComponentItem::create)
            .lang("Advanced Electric Locking Chip")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new KeyCardBehaviour()))
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> ID_CHIP = REGISTRATE.item("id_chip", ComponentItem::create)
            .lang("Magnet-strip Identification Chip")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new PlanetIdChipBehaviour()))
            .defaultModel()
            .register();

    // endregion


    // region dyson stuff

    public static final ItemEntry<Item> PHOTOVOLTAIC_CELL = REGISTRATE.item("photovoltaic_cell", Item::new)
            .lang("Photovoltaic Cell")
            .defaultModel()
            .register();

    public static final ItemEntry<Item> DYSON_CONSTRUCTION_DRONE = REGISTRATE.item("dyson_construction_drone", Item::new)
            .lang("Dyson Construction Drone")
            .defaultModel()
            .register();

    // endregion

    // region satellites
    public static final ItemEntry<Item> GPS_SATELLITE = REGISTRATE.item("gps_satellite", Item::new)
            .tag(GCyRTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .defaultModel()
            .register();
    public static final ItemEntry<Item> LASER_SATELLITE = REGISTRATE.item("laser_satellite", Item::new)
            .tag(GCyRTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .defaultModel()
            .register();
    public static final ItemEntry<Item> EMPTY_SATELLITE = REGISTRATE.item("empty_satellite", Item::new)
            .tag(GCyRTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .defaultModel()
            .register();
    public static final ItemEntry<Item> ORE_FINDER_SATELLITE = REGISTRATE.item("ore_finder_satellite", Item::new)
            .tag(GCyRTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .defaultModel()
            .register();
    public static final ItemEntry<Item> DYSON_SWARM_SATELLITE = REGISTRATE.item("dyson_swarm_satellite", Item::new)
            .tag(GCyRTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> SPACE_STATION_PACKAGE = REGISTRATE.item("space_station_package", ComponentItem::create)
            .lang("Space Station Package")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new StationContainerBehaviour()))
            .register();

    // endregion

    // region space
    public static final ItemEntry<Item> SPACE_FABRIC = REGISTRATE.item("space_fabric", Item::new)
            .properties(p -> p.fireResistant())
            .defaultModel()
            .register();

    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_HELMET = REGISTRATE.item("space_helmet", properties -> new SpaceSuitArmorItem(ArmorItem.Type.HELMET, properties))
            .properties(p -> p.fireResistant())
            .defaultModel()
            .register();
    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_CHEST = REGISTRATE.item("space_chestplate", properties -> new SpaceSuitArmorItem(ArmorItem.Type.CHESTPLATE, properties))
            .properties(p -> p.fireResistant())
            .defaultModel()
            .register();
    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_LEGS = REGISTRATE.item("space_leggings", properties -> new SpaceSuitArmorItem(ArmorItem.Type.LEGGINGS, properties))
            .properties(p -> p.fireResistant())
            .defaultModel()
            .register();
    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_BOOTS = REGISTRATE.item("space_boots", properties -> new SpaceSuitArmorItem(ArmorItem.Type.BOOTS, properties))
            .properties(p -> p.fireResistant())
            .defaultModel()
            .register();

    // SmithingTemplateItem.createArmorTrimTemplate(GCyRTrimPatterns.SPACE)
    public static final ItemEntry<SmithingTemplateItem> SPACE_UPGRADE_SMITHING_TEMPLATE = REGISTRATE.item("space_upgrade_smithing_template", properties ->
                    new SmithingTemplateItem(SmithingTemplateItem.ARMOR_TRIM_APPLIES_TO,
                            Component.translatable(Util.makeDescriptionId("item", GCyR.id("smithing_template.space_upgrade.ingredients"))),
                            Component.translatable(Util.makeDescriptionId("trim_pattern", GCyRTrimPatterns.SPACE.location())).withStyle(ChatFormatting.GRAY),
                            SmithingTemplateItem.ARMOR_TRIM_BASE_SLOT_DESCRIPTION,
                            Component.translatable(Util.makeDescriptionId("item", GCyR.id("smithing_template.space_upgrade.additions_slot_description"))),
                            SmithingTemplateItem.createTrimmableArmorIconList(),
                            List.of(GCyR.id("item/empty_slot_space_fabric"))))
            .register();

    // endregion

    public static <T extends ComponentItem> NonNullConsumer<T> attach(IItemComponent... components) {
        return item -> item.attachComponents(components);
    }

    public static void init() {

    }
}
