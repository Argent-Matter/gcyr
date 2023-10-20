package argent_matter.gcys.data.recipe;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.common.data.GCySBlocks;
import argent_matter.gcys.common.data.GCySDimensionTypes;
import argent_matter.gcys.common.data.GCySItems;
import argent_matter.gcys.common.data.GCySMaterials;
import argent_matter.gcys.common.recipe.DysonSphereCondition;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcys.common.data.GCySRecipeTypes.DYSON_ENERGY_RECIPES;
import static argent_matter.gcys.common.data.GCySRecipeTypes.SPACE_ELEVATOR_RECIPES;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLY_LINE_RECIPES;

public class DysonSphereRecipeLoader {

    public static void init(Consumer<FinishedRecipe> provider) {
        SPACE_ELEVATOR_RECIPES.recipeBuilder(GCyS.id("dyson_sphere_casing"))
                .inputItems(TagPrefix.plate, GCySMaterials.Bisalloy400, 32)
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy, 12)
                .inputItems(TagPrefix.plateDense, GTMaterials.RhodiumPlatedPalladium, 48)
                .inputItems(TagPrefix.rod, GTMaterials.TitaniumTungstenCarbide, 12)
                .outputItems(GCySBlocks.CASING_DYSON_SPHERE.asStack(6))
                .EUt(VA[ZPM]).duration(8000)
                .save(provider);

        SPACE_ELEVATOR_RECIPES.recipeBuilder(GCyS.id("dyson_solar_cell"))
                .inputItems(GCySItems.PHOTOVOLTAIC_CELL.asStack(16))
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy, 12)
                .inputItems(TagPrefix.plateDense, GTMaterials.RhodiumPlatedPalladium, 8)
                .outputItems(GCySBlocks.CASING_DYSON_CELL.asStack(4))
                .EUt(VA[ZPM]).duration(8000)
                .save(provider);

        SPACE_ELEVATOR_RECIPES.recipeBuilder(GCyS.id("dyson_sphere_maintenance_port"))
                .inputItems(GTMachines.CLEANING_MAINTENANCE_HATCH.asStack(4))
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy, 6)
                .inputItems(TagPrefix.plateDense, GTMaterials.RhodiumPlatedPalladium, 4)
                .inputItems(TagPrefix.gear, GTMaterials.HSSS, 12)
                .outputItems(GCySBlocks.CASING_DYSON_PORT.asStack(2))
                .EUt(VA[ZPM]).duration(4000)
                .save(provider);

        ASSEMBLY_LINE_RECIPES.recipeBuilder(GCyS.id("dyson_construction_drone"))
                .inputItems(TagPrefix.rotor, GTMaterials.HSSS, 4)
                .inputItems(CustomTags.ZPM_CIRCUITS, 2)
                .inputItems(CustomTags.ZPM_BATTERIES, 4)
                .inputItems(TagPrefix.plateDense, GTMaterials.Darmstadtium, 4)
                .outputItems(GCySItems.DYSON_CONSTRUCTION_DRONE.asStack(1))
                .EUt(VA[ZPM]).duration(200)
                .save(provider);

        DYSON_ENERGY_RECIPES.recipeBuilder(GCyS.id("build_dyson_sphere"))
                .inputItems(GCySBlocks.CASING_DYSON_SPHERE.asStack(64), GCySBlocks.CASING_DYSON_SPHERE.asStack(64), GCySBlocks.CASING_DYSON_SPHERE.asStack(32))
                .inputItems(GCySBlocks.CASING_DYSON_CELL.asStack(48))
                .inputItems(GCySBlocks.CASING_DYSON_PORT.asStack(32))
                .inputItems(GCySItems.DYSON_CONSTRUCTION_DRONE.asStack(32))
                .dimension(GCySDimensionTypes.SPACE_LEVEL.location())
                .EUt(VA[UV]).duration(32000)
                .save(provider);

        DYSON_ENERGY_RECIPES.recipeBuilder(GCyS.id("run_dyson_sphere_space"))
                .addCondition(new DysonSphereCondition(true))
                .dimension(GCySDimensionTypes.SPACE_LEVEL.location())
                .duration(200).EUt(-V[UHV])
                .save(provider);

        DYSON_ENERGY_RECIPES.recipeBuilder(GCyS.id("run_dyson_sphere_not_space"))
                .addCondition(new DysonSphereCondition(true))
                .dimension(GCySDimensionTypes.SPACE_LEVEL.location(), true)
                .duration(200).EUt(-V[UV])
                .save(provider);
    }
}
