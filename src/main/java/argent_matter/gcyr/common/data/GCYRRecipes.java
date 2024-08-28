package argent_matter.gcyr.common.data;

import argent_matter.gcyr.data.recipe.*;
import argent_matter.gcyr.data.recipe.chemistry.ChemistryRecipes;
import argent_matter.gcyr.data.recipe.chemistry.PolymerRecipes;
import net.minecraft.data.recipes.RecipeOutput;

public class GCYRRecipes {

    public static void init(RecipeOutput provider) {
        OxygenSpreaderRecipeLoader.init(provider);
        DysonSphereRecipeLoader.init(provider);
        PolymerRecipes.init(provider);
        ChemistryRecipes.init(provider);
        MiscRecipes.init(provider);
        RocketFuelRecipes.init(provider);

        RecipeOverrides.init(provider);
    }
}
