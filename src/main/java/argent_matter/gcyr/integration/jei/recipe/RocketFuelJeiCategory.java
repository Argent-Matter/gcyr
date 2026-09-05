package argent_matter.gcyr.integration.jei.recipe;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.item.GCYRItems;
import argent_matter.gcyr.common.data.recipe.GCYRRecipeTypes;
import argent_matter.gcyr.common.recipe.type.RocketFuelRecipe;
import argent_matter.gcyr.integration.recipeviewer.RocketFuelRecipeDisplay;

import brachy.modularui.integration.jei.ModularUIJeiPlugin;
import brachy.modularui.integration.jei.recipe.ModularUIJeiCategory;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeManager;

import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;

import java.util.Arrays;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class RocketFuelJeiCategory extends ModularUIJeiCategory<RocketFuelRecipe> {

    public static final RecipeType<RocketFuelRecipe> RECIPE_TYPE = new RecipeType<>(GCYR.id("rocket_fuel"),
            RocketFuelRecipe.class);
    public static final RocketFuelJeiCategory CATEGORY = new RocketFuelJeiCategory();

    private RocketFuelJeiCategory() {
        super(RocketFuelRecipeDisplay::createWidgetForRecipe, RocketFuelRecipe::getId);
    }

    @Override
    public int getMaxWidth() {
        return 152;
    }

    @Override
    public int getMaxHeight() {
        return 52;
    }

    @Override
    public void setupRecipeIngredients(IRecipeLayoutBuilder builder, RocketFuelRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot().addIngredients(ForgeTypes.FLUID_STACK, Arrays.asList(recipe.getFuel().getStacks()));
    }

    @Override
    public RecipeType<RocketFuelRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return ModularUIJeiPlugin.jeiHelpers.getGuiHelper()
                .createDrawableItemLike(GCYRItems.GPS_SATELLITE);
    }

    public static void registerRecipes(IRecipeRegistration registry) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        registry.addRecipes(RECIPE_TYPE, recipeManager.getAllRecipesFor(GCYRRecipeTypes.ROCKET_FUEL.get()));
    }
}
