package argent_matter.gcyr.integration.emi;

import argent_matter.gcyr.integration.emi.recipe.RocketFuelEmiRecipe;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;

@EmiEntrypoint
public class GCYREmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        RocketFuelEmiRecipe.registerContent(registry);
    }
}
