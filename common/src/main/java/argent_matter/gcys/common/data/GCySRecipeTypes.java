package argent_matter.gcys.common.data;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.api.gui.widget.GcysGuiTextures;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ELECTRIC;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.MULTIBLOCK;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;

public class GCySRecipeTypes {

    public final static GTRecipeType OXYGEN_SPREADER_RECIPES = register("oxygen_spreader", ELECTRIC).setMaxIOSize(1, 0, 1, 0).setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public static final GTRecipeType DYSON_ENERGY_RECIPES = register("dyson_controller", MULTIBLOCK)
            .setMaxIOSize(8, 0, 0, 0).setEUIO(IO.BOTH)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TRICORDER_TOOL);

    public static final GTRecipeType SPACE_ELEVATOR_RECIPES = register("space_elevator", MULTIBLOCK).setMaxIOSize(16, 1, 4, 0).setEUIO(IO.IN)
            .setProgressBar(GcysGuiTextures.PROGRESS_BAR_ROCKET, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TRICORDER_TOOL);

    public static final GTRecipeType SPACE_SHUTTLE_RECIPES = register("space_shuttle", MULTIBLOCK).setMaxIOSize(4, 0, 2, 0).setEUIO(IO.IN)
            .setProgressBar(GcysGuiTextures.PROGRESS_BAR_ROCKET, LEFT_TO_RIGHT)
            .setSound(GCySSoundEntries.ROCKET);

    public static GTRecipeType register(String name, String group, RecipeType<?>... proxyRecipes) {
        var recipeType = new GTRecipeType(GCyS.id(name), group, proxyRecipes);
        GTRegistries.register(Registry.RECIPE_TYPE, recipeType.registryName, recipeType);
        GTRegistries.register(Registry.RECIPE_SERIALIZER, recipeType.registryName, new GTRecipeSerializer());
        GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
        return recipeType;
    }

    public static void init() {

    }
}
