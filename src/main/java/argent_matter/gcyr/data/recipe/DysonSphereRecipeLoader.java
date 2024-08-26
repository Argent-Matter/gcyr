package argent_matter.gcyr.data.recipe;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.GCYRBlocks;
import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.common.data.GCYRMaterials;
import argent_matter.gcyr.common.recipe.condition.DysonSphereCondition;
import argent_matter.gcyr.common.recipe.condition.OrbitCondition;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcyr.common.data.GCYRRecipeTypes.DYSON_ENERGY_RECIPES;
import static argent_matter.gcyr.common.data.GCYRRecipeTypes.SPACE_ELEVATOR_RECIPES;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLY_LINE_RECIPES;

public class DysonSphereRecipeLoader {

    public static void init(Consumer<FinishedRecipe> provider) {
        SPACE_ELEVATOR_RECIPES.recipeBuilder(GCYR.id("dyson_sphere_casing"))
                .inputItems(TagPrefix.plate, GCYRMaterials.Bisalloy400, 32)
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy, 12)
                .inputItems(TagPrefix.plateDense, GTMaterials.RhodiumPlatedPalladium, 48)
                .inputItems(TagPrefix.rod, GTMaterials.TitaniumTungstenCarbide, 12)
                .outputItems(GCYRBlocks.CASING_DYSON_SPHERE.asStack(6))
                .EUt(VA[ZPM]).duration(8000)
                .save(provider);

        SPACE_ELEVATOR_RECIPES.recipeBuilder(GCYR.id("dyson_solar_cell"))
                .inputItems(GCYRItems.PHOTOVOLTAIC_CELL.asStack(16))
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy, 12)
                .inputItems(TagPrefix.plateDense, GTMaterials.RhodiumPlatedPalladium, 8)
                .outputItems(GCYRBlocks.CASING_DYSON_CELL.asStack(4))
                .EUt(VA[ZPM]).duration(8000)
                .save(provider);

        SPACE_ELEVATOR_RECIPES.recipeBuilder(GCYR.id("dyson_sphere_maintenance_port"))
                .inputItems(GTMachines.CLEANING_MAINTENANCE_HATCH.asStack(4))
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy, 6)
                .inputItems(TagPrefix.plateDense, GTMaterials.RhodiumPlatedPalladium, 4)
                .inputItems(TagPrefix.gear, GTMaterials.HSSS, 12)
                .outputItems(GCYRBlocks.CASING_DYSON_PORT.asStack(2))
                .EUt(VA[ZPM]).duration(4000)
                .save(provider);

        ASSEMBLY_LINE_RECIPES.recipeBuilder(GCYR.id("dyson_construction_drone"))
                .inputItems(TagPrefix.rotor, GTMaterials.HSSS, 4)
                .inputItems(CustomTags.ZPM_CIRCUITS, 2)
                .inputItems(CustomTags.ZPM_BATTERIES, 4)
                .inputItems(TagPrefix.plateDense, GTMaterials.Darmstadtium, 4)
                .outputItems(GCYRItems.DYSON_CONSTRUCTION_DRONE.asStack(1))
                .EUt(VA[ZPM]).duration(200)
                .save(provider);

        DYSON_ENERGY_RECIPES.recipeBuilder(GCYR.id("build_dyson_sphere"))
                .inputItems(GCYRBlocks.CASING_DYSON_SPHERE.asStack(64), GCYRBlocks.CASING_DYSON_SPHERE.asStack(64), GCYRBlocks.CASING_DYSON_SPHERE.asStack(32))
                .inputItems(GCYRBlocks.CASING_DYSON_CELL.asStack(48))
                .inputItems(GCYRBlocks.CASING_DYSON_PORT.asStack(32))
                .inputItems(GCYRItems.DYSON_CONSTRUCTION_DRONE.asStack(32))
                .addCondition(new OrbitCondition())
                .addCondition(new DysonSphereCondition().setReverse(true))
                .addData("gcyr:launch_dyson_sphere", true)
                .EUt(VA[UV]).duration(32000)
                .save(provider);

        DYSON_ENERGY_RECIPES.recipeBuilder(GCYR.id("repair_dyson_sphere"))
                .inputItems(GCYRBlocks.CASING_DYSON_SPHERE.asStack(16))
                .inputItems(GCYRBlocks.CASING_DYSON_CELL.asStack(8))
                .inputItems(GCYRItems.DYSON_CONSTRUCTION_DRONE.asStack(16))
                .addCondition(new OrbitCondition())
                .addCondition(new DysonSphereCondition())
                .addData("gcyr:repair_dyson_sphere", true)
                .EUt(VA[UV]).duration(32000)
                .save(provider);

        DYSON_ENERGY_RECIPES.recipeBuilder(GCYR.id("run_dyson_sphere_space"))
                .addCondition(new DysonSphereCondition())
                .addCondition(new OrbitCondition())
                .duration(200).EUt(GTCEuAPI.isHighTier() ? -V[UIV] : -V[UV])
                .save(provider);

        DYSON_ENERGY_RECIPES.recipeBuilder(GCYR.id("run_dyson_sphere_not_space"))
                .addCondition(new DysonSphereCondition())
                .addCondition(new OrbitCondition().setReverse(true))
                .duration(200).EUt(GTCEuAPI.isHighTier() ? -V[UHV] : -V[ZPM])
                .save(provider);
    }
}
