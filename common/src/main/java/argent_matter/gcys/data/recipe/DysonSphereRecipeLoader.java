package argent_matter.gcys.data.recipe;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.common.data.GcysBlocks;
import argent_matter.gcys.common.data.GcysDimensionTypes;
import argent_matter.gcys.common.data.GcysItems;
import argent_matter.gcys.common.data.GcysMaterials;
import argent_matter.gcys.common.recipe.DysonSphereCondition;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcys.common.data.GcysRecipeTypes.DYSON_ENERGY_RECIPES;
import static argent_matter.gcys.common.data.GcysRecipeTypes.SPACE_ELEVATOR_RECIPES;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLY_LINE_RECIPES;

public class DysonSphereRecipeLoader {

    public static void init(Consumer<FinishedRecipe> provider) {
        SPACE_ELEVATOR_RECIPES.recipeBuilder(GregicalitySpace.id("dyson_sphere_casing"))
                .inputItems(TagPrefix.plate, GcysMaterials.Bisalloy400, 32)
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy, 12)
                .inputItems(TagPrefix.plateDense, GTMaterials.RhodiumPlatedPalladium, 48)
                .inputItems(TagPrefix.rod, GTMaterials.TitaniumTungstenCarbide, 12)
                .outputItems(GcysBlocks.CASING_DYSON_SPHERE.asStack(6))
                .EUt(VA[ZPM]).duration(8000)
                .save(provider);

        SPACE_ELEVATOR_RECIPES.recipeBuilder(GregicalitySpace.id("dyson_solar_cell"))
                .inputItems(GcysItems.PHOTOVOLTAIC_CELL.asStack(16))
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy, 12)
                .inputItems(TagPrefix.plateDense, GTMaterials.RhodiumPlatedPalladium, 8)
                .outputItems(GcysBlocks.CASING_DYSON_CELL.asStack(4))
                .EUt(VA[ZPM]).duration(8000)
                .save(provider);

        SPACE_ELEVATOR_RECIPES.recipeBuilder(GregicalitySpace.id("dyson_sphere_maintenance_port"))
                .inputItems(GTMachines.CLEANING_MAINTENANCE_HATCH.asStack(4))
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy, 6)
                .inputItems(TagPrefix.plateDense, GTMaterials.RhodiumPlatedPalladium, 4)
                .inputItems(TagPrefix.gear, GTMaterials.HSSS, 12)
                .outputItems(GcysBlocks.CASING_DYSON_PORT.asStack(2))
                .EUt(VA[ZPM]).duration(4000)
                .save(provider);

        ASSEMBLY_LINE_RECIPES.recipeBuilder(GregicalitySpace.id("dyson_construction_drone"))
                .inputItems(TagPrefix.rotor, GTMaterials.HSSS, 4)
                .inputItems(CustomTags.ZPM_CIRCUITS, 2)
                .inputItems(CustomTags.ZPM_BATTERIES, 4)
                .inputItems(TagPrefix.plateDense, GTMaterials.Darmstadtium, 4)
                .outputItems(GcysItems.DYSON_CONSTRUCTION_DRONE.asStack(1))
                .EUt(VA[ZPM]).duration(200)
                .save(provider);

        DYSON_ENERGY_RECIPES.recipeBuilder(GregicalitySpace.id("build_dyson_sphere"))
                .inputItems(GcysBlocks.CASING_DYSON_SPHERE.asStack(64), GcysBlocks.CASING_DYSON_SPHERE.asStack(64), GcysBlocks.CASING_DYSON_SPHERE.asStack(32))
                .inputItems(GcysBlocks.CASING_DYSON_CELL.asStack(48))
                .inputItems(GcysBlocks.CASING_DYSON_PORT.asStack(32))
                .inputItems(GcysItems.DYSON_CONSTRUCTION_DRONE.asStack(32))
                .dimension(GcysDimensionTypes.SPACE_LEVEL.location())
                .EUt(VA[UV]).duration(32000)
                .save(provider);

        DYSON_ENERGY_RECIPES.recipeBuilder(GregicalitySpace.id("run_dyson_sphere_space"))
                .addCondition(new DysonSphereCondition(true))
                .dimension(GcysDimensionTypes.SPACE_LEVEL.location())
                .duration(200).EUt(-V[UHV])
                .save(provider);

        DYSON_ENERGY_RECIPES.recipeBuilder(GregicalitySpace.id("run_dyson_sphere_not_space"))
                .addCondition(new DysonSphereCondition(true))
                .dimension(GcysDimensionTypes.SPACE_LEVEL.location(), true)
                .duration(200).EUt(-V[UV])
                .save(provider);
    }
}
