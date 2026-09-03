package argent_matter.gcyr.common.entity;

import argent_matter.gcyr.GCYR;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;

public final class RocketFuelData {

    public static final String SPECIFIC_ENERGY = "specific_energy";

    private RocketFuelData() {}

    public static double specificEnergy(GTRecipe recipe) {
        if (recipe == null || !recipe.data.contains(SPECIFIC_ENERGY)) {
            if (recipe != null) GCYR.LOGGER.warn("Rocket fuel recipe {} is missing specific energy data", recipe.id);
            return 0.0D;
        }
        double value = recipe.data.getDouble(SPECIFIC_ENERGY);
        if (!Double.isFinite(value) || value <= 0.0D) {
            GCYR.LOGGER.warn("Rocket fuel recipe {} has invalid specific energy {}", recipe.id, value);
            return 0.0D;
        }
        return value;
    }
}
