package argent_matter.gcys.data.recipe;

import argent_matter.gcys.GregicalitySpace;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcys.common.data.GcysItems.*;
import static argent_matter.gcys.common.data.GcysRecipeTypes.SPACE_SHUTTLE_RECIPES;
import static com.gregtechceu.gtceu.common.data.GTMaterials.RocketFuel;

public class SpaceShuttleRecipeLoader {

    public static void init(Consumer<FinishedRecipe> provider) {
        SPACE_SHUTTLE_RECIPES.recipeBuilder(GregicalitySpace.id("gps_satellite"))
                .inputItems(GPS_SATELLITE.asStack(), SATELLITE_ROCKET.asStack())
                .inputFluids(RocketFuel.getFluid(16000))
                .addData("satellite_type", "gcys:gps_satellite")
                .duration(36000).save(provider);

        SPACE_SHUTTLE_RECIPES.recipeBuilder(GregicalitySpace.id("ore_finder_satellite"))
                .inputItems(ORE_FINDER_SATELLITE.asStack(), SATELLITE_ROCKET.asStack())
                .inputFluids(RocketFuel.getFluid(16000))
                .addData("satellite_type", "gcys:ore_finder_satellite")
                .duration(36000).save(provider);

        SPACE_SHUTTLE_RECIPES.recipeBuilder(GregicalitySpace.id("laser_satellite"))
                .inputItems(LASER_SATELLITE.asStack(), SATELLITE_ROCKET.asStack())
                .inputFluids(RocketFuel.getFluid(24000))
                .addData("satellite_type", "gcys:laser_satellite")
                .duration(54000).save(provider);

        SPACE_SHUTTLE_RECIPES.recipeBuilder(GregicalitySpace.id("dyson_swarm_satellite"))
                .inputItems(DYSON_SWARM_SATELLITE.asStack(), SATELLITE_ROCKET.asStack())
                .inputFluids(RocketFuel.getFluid(32000))
                .addData("satellite_type", "gcys:dyson_swarm_satellite")
                .duration(72000).save(provider);

        SPACE_SHUTTLE_RECIPES.recipeBuilder(GregicalitySpace.id("manned_launch_station"))
                .inputItems(MANNED_ROCKET.asStack())
                .inputFluids(RocketFuel.getFluid(24000))
                .addData("satellite_type", "gcys:manned_rocket")
                .duration(36000).save(provider);
    }
}
