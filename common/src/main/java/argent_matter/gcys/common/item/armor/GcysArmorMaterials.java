package argent_matter.gcys.common.item.armor;

import argent_matter.gcys.common.data.GCySItems;
import lombok.Getter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum GcysArmorMaterials implements ArmorMaterial {
    SPACE("gcys:space", 5, new int[]{3, 6, 8, 3}, 5, SoundEvents.ARMOR_EQUIP_DIAMOND, 0, 0, () -> Ingredient.of(GCySItems.SPACE_FABRIC.get()));

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    @Getter
    private final String name;
    @Getter
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    @Getter
    private final int enchantmentValue;
    @Getter
    private final SoundEvent equipSound;
    @Getter
    private final float toughness;
    @Getter
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    GcysArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_PER_SLOT[type.getSlot().getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.slotProtections[type.getSlot().getIndex()];
    }

    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }
}
