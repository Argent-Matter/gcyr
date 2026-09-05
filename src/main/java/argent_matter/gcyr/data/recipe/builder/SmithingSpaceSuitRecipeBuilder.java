package argent_matter.gcyr.data.recipe.builder;

import argent_matter.gcyr.common.data.recipe.GCYRRecipeSerializers;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import com.google.gson.JsonObject;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

public class SmithingSpaceSuitRecipeBuilder {

    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;

    public SmithingSpaceSuitRecipeBuilder(Ingredient template, Ingredient base, Ingredient addition) {
        this.template = template;
        this.base = base;
        this.addition = addition;
    }

    public static SmithingSpaceSuitRecipeBuilder smithingSpacesuit(Ingredient template, Ingredient base,
                                                                   Ingredient addition) {
        return new SmithingSpaceSuitRecipeBuilder(template, base, addition);
    }

    public void save(Consumer<FinishedRecipe> provider, ResourceLocation recipeId) {
        provider.accept(new Result(recipeId, this.template, this.base, this.addition));
    }

    public record Result(ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition)
            implements FinishedRecipe {

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
            return GCYRRecipeSerializers.SMITHING_SPACESUIT.get();
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
