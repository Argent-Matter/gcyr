package argent_matter.gcyr.data.recipe.pregenerated;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;

public class GCYRStaticRecipes {

    public static void init(RegistrateRecipeProvider provider) {
        RocketFuelRecipes.init(provider);
    }
}
