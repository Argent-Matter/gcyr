package argent_matter.gcyr.data.recipe.builder;

import argent_matter.gcyr.common.data.GCYRVanillaRecipeTypes;
import argent_matter.gcyr.common.recipe.type.SmithingSpaceSuitRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SmithingSpaceSuitRecipeBuilder {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final RecipeSerializer<?> type;

    public SmithingSpaceSuitRecipeBuilder(RecipeSerializer<?> type,Ingredient template, Ingredient base, Ingredient addition) {
        this.type = type;
        this.template = template;
        this.base = base;
        this.addition = addition;
    }

    public static SmithingSpaceSuitRecipeBuilder smithingSpacesuit(Ingredient template, Ingredient base, Ingredient addition) {
        return new SmithingSpaceSuitRecipeBuilder(GCYRVanillaRecipeTypes.SMITHING_SPACESUIT_SERIALIZER.get(), template, base, addition);
    }

    public void save(RecipeOutput recipeConsumer, ResourceLocation location) {
        recipeConsumer.accept(location,
                new SmithingSpaceSuitRecipe(this.template, this.base, this.addition),
                null);
    }
}
