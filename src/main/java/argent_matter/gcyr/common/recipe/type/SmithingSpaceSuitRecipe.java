package argent_matter.gcyr.common.recipe.type;

import argent_matter.gcyr.common.data.GCYRDataComponents;
import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.common.data.GCYRVanillaRecipeTypes;
import argent_matter.gcyr.common.item.armor.trim.GCYRTrimMaterials;
import argent_matter.gcyr.common.item.armor.trim.GCYRTrimPatterns;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.armortrim.TrimPatterns;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;

import java.util.Optional;
import java.util.stream.Stream;

public class SmithingSpaceSuitRecipe implements SmithingRecipe {

    public static final String SPACE_SUIT_ARMOR_KEY = "gcyr:spacesuit";

    final Ingredient template;
    final Ingredient base;
    final Ingredient addition;

    public SmithingSpaceSuitRecipe(Ingredient template, Ingredient base, Ingredient addition) {
        this.template = template;
        this.base = base;
        this.addition = addition;
    }

    @Override
    public boolean matches(SmithingRecipeInput container, Level level) {
        return this.template.test(container.getItem(0)) && this.base.test(container.getItem(1)) && this.addition.test(container.getItem(2));
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput container, HolderLookup.Provider provider) {
        ItemStack baseItem = container.getItem(1);
        ItemStack additionItem = container.getItem(2);
        if (!baseItem.has(GCYRDataComponents.SPACE_SUIT) && this.base.test(baseItem)) {
            Optional<Holder.Reference<TrimMaterial>> trimMaterial = provider.lookupOrThrow(Registries.TRIM_MATERIAL).get(GCYRTrimMaterials.SPACE);
            Optional<Holder.Reference<TrimPattern>> trimPattern = TrimPatterns.getFromTemplate(provider, container.getItem(0));
            if (/*trimMaterial.isPresent() && trimPattern.isPresent()*/ true) { // maybe add textures too, idk?
                ItemStack trimCopied = baseItem.copy();
                trimCopied.setCount(1);
                ArmorTrim trim = new ArmorTrim(trimMaterial.get(), trimPattern.get());
                setTrim(provider, trimCopied, additionItem, trim);
                return trimCopied;
            }
        }

        return ItemStack.EMPTY;
    }

    public static void setTrim(HolderLookup.Provider provider, ItemStack armor, ItemStack addition, ArmorTrim trim) {
        // Set the "is this a space suit" NBT
        armor.set(GCYRDataComponents.SPACE_SUIT, SimpleFluidContent.EMPTY);
        // if the armor piece is a chestplate, copy the fluid from the "addition" item (space suit) if the space suit isn't empty
        if (armor.is(ItemTags.CHEST_ARMOR)) {
            var cap = addition.getCapability(Capabilities.FluidHandler.ITEM);
            if (cap != null) {
                FluidStack stack = cap.getFluidInTank(0);
                if (!stack.isEmpty()) {
                    armor.set(GCYRDataComponents.SPACE_SUIT, SimpleFluidContent.copyOf(stack));
                }
            }
        }
        /*
        if (armor.is(ItemTags.TRIMMABLE_ARMOR)) {
            armor.getOrCreateTag().put("Trim", ArmorTrim.CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, provider), trim).result().orElseThrow());
        }
         */
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        ItemStack itemstack = new ItemStack(Items.DIAMOND_CHESTPLATE);
        Optional<Holder.Reference<TrimPattern>> pattern = provider.lookupOrThrow(Registries.TRIM_PATTERN).get(GCYRTrimPatterns.SPACE);
        if (pattern.isPresent()) {
            Optional<Holder.Reference<TrimMaterial>> material = provider.lookupOrThrow(Registries.TRIM_MATERIAL).get(GCYRTrimMaterials.SPACE);
            if (material.isPresent()) {
                ArmorTrim armortrim = new ArmorTrim(material.get(), pattern.get());
                setTrim(provider, itemstack, new ItemStack(GCYRItems.SPACE_SUIT_CHEST.get()), armortrim);
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
    public RecipeSerializer<?> getSerializer() {
        return GCYRVanillaRecipeTypes.SMITHING_SPACESUIT_SERIALIZER.get();
    }

    @Override
    public boolean isIncomplete() {
        return Stream.of(this.template, this.base, this.addition).anyMatch(Ingredient::isEmpty);
    }

    public static class Serializer implements RecipeSerializer<SmithingSpaceSuitRecipe> {
        private static final MapCodec<SmithingSpaceSuitRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC.fieldOf("template").forGetter(p -> p.template),
                                Ingredient.CODEC.fieldOf("base").forGetter(p -> p.base),
                                Ingredient.CODEC.fieldOf("addition").forGetter(p -> p.addition)
                        )
                        .apply(instance, SmithingSpaceSuitRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingSpaceSuitRecipe> STREAM_CODEC = StreamCodec.of(
                SmithingSpaceSuitRecipe.Serializer::toNetwork, SmithingSpaceSuitRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<SmithingSpaceSuitRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SmithingSpaceSuitRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SmithingSpaceSuitRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient ingredient1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient ingredient2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            return new SmithingSpaceSuitRecipe(ingredient, ingredient1, ingredient2);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SmithingSpaceSuitRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
        }
    }
}
