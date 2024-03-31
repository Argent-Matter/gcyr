package argent_matter.gcyr.common.recipe.type;

import argent_matter.gcyr.common.data.GCyRRecipeTypes;
import argent_matter.gcyr.common.data.GCyRVanillaRecipeTypes;
import argent_matter.gcyr.common.item.armor.trim.GCyRTrimMaterials;
import com.google.gson.JsonObject;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;

import java.util.Optional;
import java.util.stream.Stream;

public class SmithingSpaceSuitRecipe implements SmithingRecipe {
    public static final String SPACE_SUIT_ARMOR_KEY = "gcyr:spacesuit";

    private final ResourceLocation id;
    final Ingredient template;
    final Ingredient base;
    final Ingredient addition;

    public SmithingSpaceSuitRecipe(ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition) {
        this.id = id;
        this.template = template;
        this.base = base;
        this.addition = addition;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.template.test(container.getItem(0)) && this.base.test(container.getItem(1)) && this.addition.test(container.getItem(2));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack trimItem = container.getItem(1);
        if ((!trimItem.hasTag() || !trimItem.getTag().getBoolean(SPACE_SUIT_ARMOR_KEY)) && this.base.test(trimItem)) {
            Optional<Holder.Reference<TrimMaterial>> trimMaterial = registryAccess.registryOrThrow(Registries.TRIM_MATERIAL).getHolder(GCyRTrimMaterials.SPACE);
            Optional<Holder.Reference<TrimPattern>> trimPattern = TrimPatterns.getFromTemplate(registryAccess, container.getItem(0));
            if (/*trimMaterial.isPresent() && trimPattern.isPresent()*/ true) { // maybe add textures too, idk?
                ItemStack trimCopied = trimItem.copy();
                trimCopied.setCount(1);
                ArmorTrim trim = new ArmorTrim(trimMaterial.get(), trimPattern.get());
                setTrim(registryAccess, trimCopied, trim);
                return trimCopied;
            }
        }

        return ItemStack.EMPTY;
    }

    public static void setTrim(RegistryAccess registryAccess, ItemStack armor, ArmorTrim trim) {
        armor.getOrCreateTag().putBoolean(SPACE_SUIT_ARMOR_KEY, true);
        /*
        if (armor.is(ItemTags.TRIMMABLE_ARMOR)) {
            armor.getOrCreateTag().put("Trim", ArmorTrim.CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, registryAccess), trim).result().orElseThrow());
        }
         */
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        ItemStack itemstack = new ItemStack(Items.DIAMOND_CHESTPLATE);
        Optional<Holder.Reference<TrimPattern>> optional = registryAccess.registryOrThrow(Registries.TRIM_PATTERN).holders().findFirst();
        if (optional.isPresent()) {
            Optional<Holder.Reference<TrimMaterial>> optional1 = registryAccess.registryOrThrow(Registries.TRIM_MATERIAL).getHolder(TrimMaterials.REDSTONE);
            if (optional1.isPresent()) {
                ArmorTrim armortrim = new ArmorTrim(optional1.get(), optional.get());
                ArmorTrim.setTrim(registryAccess, itemstack, armortrim);
            }
        }

        return itemstack;
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
        return GCyRVanillaRecipeTypes.SMITHING_SPACESUIT_SERIALIZER.get();
    }

    @Override
    public boolean isIncomplete() {
        return Stream.of(this.template, this.base, this.addition).anyMatch(ForgeHooks::hasNoElements);
    }

    public static class Serializer implements RecipeSerializer<SmithingSpaceSuitRecipe> {
        public SmithingSpaceSuitRecipe fromJson(ResourceLocation arg, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "template"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "base"));
            Ingredient ingredient2 = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "addition"));
            return new SmithingSpaceSuitRecipe(arg, ingredient, ingredient1, ingredient2);
        }

        public SmithingSpaceSuitRecipe fromNetwork(ResourceLocation arg, FriendlyByteBuf arg2) {
            Ingredient ingredient = Ingredient.fromNetwork(arg2);
            Ingredient ingredient1 = Ingredient.fromNetwork(arg2);
            Ingredient ingredient2 = Ingredient.fromNetwork(arg2);
            return new SmithingSpaceSuitRecipe(arg, ingredient, ingredient1, ingredient2);
        }

        public void toNetwork(FriendlyByteBuf arg, SmithingSpaceSuitRecipe arg2) {
            arg2.template.toNetwork(arg);
            arg2.base.toNetwork(arg);
            arg2.addition.toNetwork(arg);
        }
    }
}
