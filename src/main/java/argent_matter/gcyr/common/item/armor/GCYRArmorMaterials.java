package argent_matter.gcyr.common.item.armor;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.GCYRItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class GCYRArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, GCYR.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SPACE = ARMOR_MATERIALS.register("space", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
        map.put(ArmorItem.Type.BODY, 8);
    }), 5, SoundEvents.ARMOR_EQUIP_DIAMOND, () -> Ingredient.of(GCYRItems.SPACE_FABRIC.get()), List.of(new ArmorMaterial.Layer(GCYR.id("space"))), 0, 0));


    public static void register(IEventBus modBus) {
        ARMOR_MATERIALS.register(modBus);
    }
}
