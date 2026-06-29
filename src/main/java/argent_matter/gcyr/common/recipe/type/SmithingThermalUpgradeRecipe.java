package argent_matter.gcyr.common.recipe.type;

import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.common.data.GCYRVanillaRecipeTypes;
import argent_matter.gcyr.common.item.armor.SpaceSuitArmorItem;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;

import net.minecraftforge.common.ForgeHooks;

import com.google.gson.JsonObject;

import java.util.stream.Stream;

public class SmithingThermalUpgradeRecipe implements SmithingRecipe {

    public static final String HEAT_SHIELDED_KEY = "gcyr:heat_shielded";
    public static final String FREEZE_PROTECTED_KEY = "gcyr:freeze_protected";

    private final ResourceLocation id;
    final Ingredient template;
    final Ingredient base;
    final Ingredient addition;

    public SmithingThermalUpgradeRecipe(ResourceLocation id, Ingredient template, Ingredient base,
                                        Ingredient addition) {
        this.id = id;
        this.template = template;
        this.base = base;
        this.addition = addition;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.template.test(container.getItem(0)) && this.base.test(container.getItem(1)) &&
                this.addition.test(container.getItem(2));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack baseItem = container.getItem(1);
        ItemStack additionItem = container.getItem(2);

        boolean isSpaceSuit = baseItem.getItem() instanceof SpaceSuitArmorItem ||
                (baseItem.hasTag() && baseItem.getTag().getBoolean(SmithingSpaceSuitRecipe.SPACE_SUIT_ARMOR_KEY));
        if (!isSpaceSuit) return ItemStack.EMPTY;

        String nbtKey;
        if (additionItem.is(GCYRItems.HEAT_SHIELDING_FABRIC.get())) {
            nbtKey = HEAT_SHIELDED_KEY;
        } else if (additionItem.is(GCYRItems.INSULATING_FABRIC.get())) {
            nbtKey = FREEZE_PROTECTED_KEY;
        } else {
            return ItemStack.EMPTY;
        }

        if (baseItem.hasTag() && baseItem.getTag().getBoolean(nbtKey)) return ItemStack.EMPTY;

        ItemStack result = baseItem.copy();
        result.setCount(1);
        result.getOrCreateTag().putBoolean(nbtKey, true);
        return result;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        ItemStack result = new ItemStack(GCYRItems.SPACE_SUIT_HELMET.get());
        result.getOrCreateTag().putBoolean(HEAT_SHIELDED_KEY, true);
        return result;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return this.template.test(stack);
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return this.base.test(stack);
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return this.addition.test(stack);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return GCYRVanillaRecipeTypes.SMITHING_THERMAL_UPGRADE_SERIALIZER.get();
    }

    @Override
    public boolean isIncomplete() {
        return Stream.of(this.template, this.base, this.addition).anyMatch(ForgeHooks::hasNoElements);
    }

    public static class Serializer implements RecipeSerializer<SmithingThermalUpgradeRecipe> {

        @Override
        public SmithingThermalUpgradeRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient template = Ingredient.fromJson(GsonHelper.getNonNull(json, "template"));
            Ingredient base = Ingredient.fromJson(GsonHelper.getNonNull(json, "base"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getNonNull(json, "addition"));
            return new SmithingThermalUpgradeRecipe(id, template, base, addition);
        }

        @Override
        public SmithingThermalUpgradeRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient template = Ingredient.fromNetwork(buf);
            Ingredient base = Ingredient.fromNetwork(buf);
            Ingredient addition = Ingredient.fromNetwork(buf);
            return new SmithingThermalUpgradeRecipe(id, template, base, addition);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SmithingThermalUpgradeRecipe recipe) {
            recipe.template.toNetwork(buf);
            recipe.base.toNetwork(buf);
            recipe.addition.toNetwork(buf);
        }
    }
}
