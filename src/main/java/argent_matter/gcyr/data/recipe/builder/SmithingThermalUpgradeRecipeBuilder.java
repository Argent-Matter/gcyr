package argent_matter.gcyr.data.recipe.builder;

import argent_matter.gcyr.common.data.recipe.GCYRVanillaRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import com.google.gson.JsonObject;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

public class SmithingThermalUpgradeRecipeBuilder {

    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final RecipeSerializer<?> type;

    public SmithingThermalUpgradeRecipeBuilder(RecipeSerializer<?> type, Ingredient template, Ingredient base,
                                               Ingredient addition) {
        this.type = type;
        this.template = template;
        this.base = base;
        this.addition = addition;
    }

    public static SmithingThermalUpgradeRecipeBuilder smithingThermal(Ingredient template, Ingredient base,
                                                                      Ingredient addition) {
        return new SmithingThermalUpgradeRecipeBuilder(
                GCYRVanillaRecipeTypes.SMITHING_THERMAL_UPGRADE_SERIALIZER.get(), template, base, addition);
    }

    public void save(Consumer<FinishedRecipe> recipeConsumer, ResourceLocation location) {
        recipeConsumer.accept(new Result(location, this.type, this.template, this.base, this.addition));
    }

    public record Result(
                         ResourceLocation id,
                         RecipeSerializer<?> type,
                         Ingredient template,
                         Ingredient base,
                         Ingredient addition)
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
