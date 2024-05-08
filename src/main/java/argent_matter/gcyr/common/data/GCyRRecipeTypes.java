package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.gui.widget.GCyRGuiTextures;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ELECTRIC;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.MULTIBLOCK;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;

public class GCyRRecipeTypes {

    public static final GTRecipeType OXYGEN_SPREADER_RECIPES = register("oxygen_spreader", ELECTRIC).setMaxIOSize(1, 0, 1, 0).setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public static final GTRecipeType EVAPORATION_RECIPES = register("evaporation", ELECTRIC).setMaxIOSize(0, 0, 1, 6).setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public static final GTRecipeType HEAT_EXCHANGER_RECIPES = register("heat_exchanger", ELECTRIC).setMaxIOSize(2, 2, 2, 2).setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public static final GTRecipeType DYSON_ENERGY_RECIPES = register("dyson_controller", MULTIBLOCK)
            .setMaxIOSize(8, 0, 0, 0).setEUIO(IO.BOTH)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.PORTABLE_SCANNER);

    public static final GTRecipeType SPACE_ELEVATOR_RECIPES = register("space_elevator", MULTIBLOCK).setMaxIOSize(16, 1, 4, 0).setEUIO(IO.IN)
            .setProgressBar(GCyRGuiTextures.PROGRESS_BAR_ROCKET, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.PORTABLE_SCANNER);

    public static final GTRecipeType ROCKET_FUEL_RECIPES = register("rocket_fuel", MULTIBLOCK).setMaxIOSize(0, 0, 1, 0).setEUIO(IO.IN)
            .setProgressBar(GCyRGuiTextures.PROGRESS_BAR_ROCKET, LEFT_TO_RIGHT)
            .setSound(GCyRSoundEntries.ROCKET);

    public static GTRecipeType register(String name, String group, RecipeType<?>... proxyRecipes) {
        var recipeType = new GTRecipeType(GCyR.id(name), group, proxyRecipes);
        GTRegistries.register(BuiltInRegistries.RECIPE_TYPE, recipeType.registryName, recipeType);
        GTRegistries.register(BuiltInRegistries.RECIPE_SERIALIZER, recipeType.registryName, new GTRecipeSerializer());
        GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
        return recipeType;
    }

    public static void init() {

    }
}
