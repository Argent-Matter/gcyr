package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.recipe.condition.DysonSphereCondition;
import argent_matter.gcyr.common.recipe.condition.OrbitCondition;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

public class GCYRRecipeConditions {
    public static final RecipeConditionType<DysonSphereCondition> DYSON_SPHERE = new RecipeConditionType<>(DysonSphereCondition::new, DysonSphereCondition.CODEC);
    public static final RecipeConditionType<OrbitCondition> ORBIT = new RecipeConditionType<>(OrbitCondition::new, OrbitCondition.CODEC);

    public static void init() {
        GTRegistries.register(GTRegistries.RECIPE_CONDITIONS, GCYR.id("dyson_sphere"), DYSON_SPHERE);
        GTRegistries.register(GTRegistries.RECIPE_CONDITIONS, GCYR.id("orbit"), ORBIT);
    }
}
