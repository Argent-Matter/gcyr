package argent_matter.gcys.common.data;

import argent_matter.gcys.common.recipe.DysonSphereCondition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

public class GcysRecipeConditions {

    public static void init() {
        GTRegistries.RECIPE_CONDITIONS.register(DysonSphereCondition.INSTANCE.getType(), DysonSphereCondition.class);
    }
}
