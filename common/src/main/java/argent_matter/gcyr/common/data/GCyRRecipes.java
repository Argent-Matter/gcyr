package argent_matter.gcyr.common.data;

import argent_matter.gcyr.data.recipe.MiscRecipes;
import argent_matter.gcyr.data.recipe.OxygenSpreaderRecipeLoader;
import argent_matter.gcyr.data.recipe.RecipeOverrides;
import argent_matter.gcyr.data.recipe.DysonSphereRecipeLoader;
import argent_matter.gcyr.data.recipe.chemistry.PolymerRecipes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class GCyRRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        OxygenSpreaderRecipeLoader.init(provider);
        DysonSphereRecipeLoader.init(provider);
        PolymerRecipes.init(provider);
        MiscRecipes.init(provider);

        RecipeOverrides.init(provider);
    }
}
