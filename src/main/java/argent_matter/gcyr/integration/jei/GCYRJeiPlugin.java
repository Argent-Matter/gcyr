package argent_matter.gcyr.integration.jei;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.integration.jei.recipe.RocketFuelJeiCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class GCYRJeiPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(RocketFuelJeiCategory.CATEGORY);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        RocketFuelJeiCategory.registerRecipes(registry);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return GCYR.id("jei_plugin");
    }
}
