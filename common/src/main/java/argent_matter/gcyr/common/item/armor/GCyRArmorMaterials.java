package argent_matter.gcyr.common.item.armor;

import argent_matter.gcyr.common.data.GCyRItems;
import lombok.Getter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum GCyRArmorMaterials implements ArmorMaterial {
    SPACE("gcyr:space", 5, new int[]{3, 6, 8, 3}, 5, SoundEvents.ARMOR_EQUIP_DIAMOND, 0, 0, () -> Ingredient.of(GCyRItems.SPACE_FABRIC.get()));

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

    GCyRArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
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
