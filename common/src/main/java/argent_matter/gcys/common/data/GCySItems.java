package argent_matter.gcys.common.data;

import argent_matter.gcys.common.item.GpsTrackerBehaviour;
import argent_matter.gcys.common.item.KeyCardBehaviour;
import argent_matter.gcys.common.item.PlanetIdChipBehaviour;
import argent_matter.gcys.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcys.data.recipe.GCySTags;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import static argent_matter.gcys.api.registries.GcysRegistries.REGISTRATE;

@SuppressWarnings("Convert2MethodRef")
public class GCySItems {

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
            .tag(GCySTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .defaultModel()
            .register();
    public static final ItemEntry<Item> LASER_SATELLITE = REGISTRATE.item("laser_satellite", Item::new)
            .tag(GCySTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .defaultModel()
            .register();
    public static final ItemEntry<Item> EMPTY_SATELLITE = REGISTRATE.item("empty_satellite", Item::new)
            .tag(GCySTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .defaultModel()
            .register();
    public static final ItemEntry<Item> ORE_FINDER_SATELLITE = REGISTRATE.item("ore_finder_satellite", Item::new)
            .tag(GCySTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .defaultModel()
            .register();
    public static final ItemEntry<Item> DYSON_SWARM_SATELLITE = REGISTRATE.item("dyson_swarm_satellite", Item::new)
            .tag(GCySTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .defaultModel()
            .register();

    // endregion

    // region space
    public static final ItemEntry<Item> SPACE_FABRIC = REGISTRATE.item("space_fabric", Item::new)
            .properties(p -> p.fireResistant())
            .defaultModel()
            .register();

    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_HELMET = REGISTRATE.item("space_helmet", properties -> SpaceSuitArmorItem.create(ArmorItem.Type.HELMET, properties))
            .properties(p -> p.fireResistant())
            .defaultModel()
            .register();
    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_CHEST = REGISTRATE.item("space_chestplate", properties -> SpaceSuitArmorItem.create(ArmorItem.Type.CHESTPLATE, properties))
            .properties(p -> p.fireResistant())
            .defaultModel()
            .register();
    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_LEGS = REGISTRATE.item("space_leggings", properties -> SpaceSuitArmorItem.create(ArmorItem.Type.LEGGINGS, properties))
            .properties(p -> p.fireResistant())
            .defaultModel()
            .register();
    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_BOOTS = REGISTRATE.item("space_boots", properties -> SpaceSuitArmorItem.create(ArmorItem.Type.BOOTS, properties))
            .properties(p -> p.fireResistant())
            .defaultModel()
            .register();

    // endregion

    public static <T extends ComponentItem> NonNullConsumer<T> attach(IItemComponent... components) {
        return item -> item.attachComponents(components);
    }

    public static void init() {

    }
}
