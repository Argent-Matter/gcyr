package argent_matter.gcys.common.data;

import argent_matter.gcys.common.item.GpsTrackerBehaviour;
import argent_matter.gcys.common.item.IdChipBehaviour;
import argent_matter.gcys.common.item.KeyCardBehaviour;
import argent_matter.gcys.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcys.data.recipe.GcysTags;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import static argent_matter.gcys.api.registries.GcysRegistries.REGISTRATE;
import static com.gregtechceu.gtceu.common.data.GTItems.attach;

@SuppressWarnings("Convert2MethodRef")
public class GcysItems {

    public static final ItemEntry<ComponentItem> GPS_TRACKER = REGISTRATE.item("gps_tracker", ComponentItem::create)
            .lang("GPS Tracker")
            .properties(p -> p.stacksTo(16))
            .onRegister(attach(new GpsTrackerBehaviour()))
            .register();

    public static final ItemEntry<ComponentItem> KEYCARD = REGISTRATE.item("keycard", ComponentItem::create)
            .lang("Advanced Electric Locking Chip")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new KeyCardBehaviour()))
            .register();

    public static final ItemEntry<ComponentItem> ID_CHIP = REGISTRATE.item("id_chip", ComponentItem::create)
            .lang("Magnet-strip Identification Chip")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new IdChipBehaviour()))
            .register();


    public static final ItemEntry<Item> GPS_SATELLITE = REGISTRATE.item("gps_satellite", Item::new)
            .tag(GcysTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .register();
    public static final ItemEntry<Item> LASER_SATELLITE = REGISTRATE.item("laser_satellite", Item::new)
            .tag(GcysTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .register();
    public static final ItemEntry<Item> EMPTY_SATELLITE = REGISTRATE.item("empty_satellite", Item::new)
            .tag(GcysTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .register();
    public static final ItemEntry<Item> ORE_FINDER_SATELLITE = REGISTRATE.item("ore_finder_satellite", Item::new)
            .tag(GcysTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .register();
    public static final ItemEntry<Item> DYSON_SWARM_SATELLITE = REGISTRATE.item("dyson_swarm_satellite", Item::new)
            .tag(GcysTags.SATELLITES)
            .properties(p -> p.rarity(Rarity.UNCOMMON).stacksTo(16))
            .register();

    public static final ItemEntry<Item> SATELLITE_ROCKET = REGISTRATE.item("satellite_rocket", Item::new)
            .properties(p -> p.rarity(Rarity.RARE).stacksTo(1).fireResistant())
            .register();
    public static final ItemEntry<Item> MANNED_ROCKET = REGISTRATE.item("manned_rocket", Item::new)
            .properties(p -> p.rarity(Rarity.RARE).stacksTo(1).fireResistant())
            .register();
    public static final ItemEntry<Item> SPACE_FABRIC = REGISTRATE.item("space_fabric", Item::new)
            .properties(p -> p.fireResistant())
            .register();

    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_HELMET = REGISTRATE.item("space_helmet", properties -> SpaceSuitArmorItem.create(EquipmentSlot.HEAD, properties))
            .properties(p -> p.fireResistant())
            .register();
    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_CHEST = REGISTRATE.item("space_chestplate", properties -> SpaceSuitArmorItem.create(EquipmentSlot.CHEST, properties))
            .properties(p -> p.fireResistant())
            .register();
    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_LEGS = REGISTRATE.item("space_leggings", properties -> SpaceSuitArmorItem.create(EquipmentSlot.LEGS, properties))
            .properties(p -> p.fireResistant())
            .register();
    public static final ItemEntry<SpaceSuitArmorItem> SPACE_SUIT_BOOTS = REGISTRATE.item("space_boots", properties -> SpaceSuitArmorItem.create(EquipmentSlot.FEET, properties))
            .properties(p -> p.fireResistant())
            .register();

    public static void init() {

    }
}
