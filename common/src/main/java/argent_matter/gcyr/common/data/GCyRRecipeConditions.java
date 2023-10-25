package argent_matter.gcyr.common.data;

import argent_matter.gcyr.common.recipe.DysonSphereCondition;
import argent_matter.gcyr.common.recipe.OrbitCondition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

public class GCyRRecipeConditions {

    public static void init() {
        GTRegistries.RECIPE_CONDITIONS.register(DysonSphereCondition.INSTANCE.getType(), DysonSphereCondition.class);
        GTRegistries.RECIPE_CONDITIONS.register(OrbitCondition.INSTANCE.getType(), OrbitCondition.class);
    }
}
