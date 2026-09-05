package argent_matter.gcyr.integration.emi.recipe;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.item.GCYRItems;
import argent_matter.gcyr.common.data.recipe.GCYRRecipeTypes;
import argent_matter.gcyr.common.recipe.type.RocketFuelRecipe;
import argent_matter.gcyr.integration.recipeviewer.RocketFuelRecipeDisplay;

import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;

import brachy.modularui.integration.emi.EmiStackConverter;
import brachy.modularui.integration.emi.recipe.ModularUIEmiCategory;
import brachy.modularui.integration.emi.recipe.ModularUIEmiRecipe;
import brachy.modularui.integration.recipeviewer.entry.fluid.FluidEntryList;

import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import lombok.Getter;

import java.util.List;
import java.util.function.UnaryOperator;

@SuppressWarnings("UnstableApiUsage")
public class RocketFuelEmiRecipe extends ModularUIEmiRecipe {

    public static final EmiRecipeCategory CATEGORY = new Category();

    @Getter
    private final List<EmiIngredient> inputs;

    public RocketFuelEmiRecipe(RocketFuelRecipe recipe) {
        super(recipe.getId(), () -> RocketFuelRecipeDisplay.createWidgetForRecipe(recipe));

        this.inputs = List.of(toEmiIngredient(recipe.getFuel()));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of();
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    private static EmiIngredient toEmiIngredient(FluidIngredient ingredient) {
        FluidEntryList entryList = RocketFuelRecipeDisplay.toEntryList(ingredient);
        return EmiStackConverter.FLUID.convertTo(entryList, 1.0f, UnaryOperator.identity());
    }

    public static void registerContent(EmiRegistry registry) {
        registry.addCategory(CATEGORY);
        registry.getRecipeManager().getAllRecipesFor(GCYRRecipeTypes.ROCKET_FUEL.get())
                .forEach(r -> registry.addRecipe(new RocketFuelEmiRecipe(r)));
    }

    private static class Category extends ModularUIEmiCategory {

        private Category() {
            super(GCYR.id("rocket_fuel"), EmiStack.of(GCYRItems.GPS_SATELLITE));
        }
    }
}
