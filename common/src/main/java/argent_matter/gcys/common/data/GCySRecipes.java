package argent_matter.gcys.common.data;

import argent_matter.gcys.data.recipe.MiscRecipes;
import argent_matter.gcys.data.recipe.OxygenSpreaderRecipeLoader;
import argent_matter.gcys.data.recipe.RecipeOverrides;
import argent_matter.gcys.data.recipe.DysonSphereRecipeLoader;
import argent_matter.gcys.data.recipe.chemistry.PolymerRecipes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class GCySRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        OxygenSpreaderRecipeLoader.init(provider);
        DysonSphereRecipeLoader.init(provider);
        PolymerRecipes.init(provider);
        MiscRecipes.init(provider);

        RecipeOverrides.init(provider);
    }
}
