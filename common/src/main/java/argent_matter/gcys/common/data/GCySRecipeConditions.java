package argent_matter.gcys.common.data;

import argent_matter.gcys.common.recipe.DysonSphereCondition;
import argent_matter.gcys.common.recipe.OrbitCondition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

public class GCySRecipeConditions {

    public static void init() {
        GTRegistries.RECIPE_CONDITIONS.register(DysonSphereCondition.INSTANCE.getType(), DysonSphereCondition.class);
        GTRegistries.RECIPE_CONDITIONS.register(OrbitCondition.INSTANCE.getType(), OrbitCondition.class);
    }
}
