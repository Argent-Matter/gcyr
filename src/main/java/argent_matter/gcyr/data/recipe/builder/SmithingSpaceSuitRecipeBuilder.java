package argent_matter.gcyr.data.recipe.builder;


import argent_matter.gcyr.common.data.GCyRVanillaRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

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
        return new SmithingSpaceSuitRecipeBuilder(GCyRVanillaRecipeTypes.SMITHING_SPACESUIT_SERIALIZER.get(), template, base, addition);
    }

    public void save(Consumer<FinishedRecipe> recipeConsumer, ResourceLocation location) {
        recipeConsumer.accept(
                new SmithingSpaceSuitRecipeBuilder.Result(location, this.type, this.template, this.base, this.addition));
    }

    public record Result(
            ResourceLocation id,
            RecipeSerializer<?> type,
            Ingredient template,
            Ingredient base,
            Ingredient addition
    ) implements FinishedRecipe {
        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("template", this.template.toJson());
            json.add("base", this.base.toJson());
            json.add("addition", this.addition.toJson());
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.type;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
