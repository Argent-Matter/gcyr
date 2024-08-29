package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.gui.widget.GCYRGuiTextures;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.sound.GTSoundEntries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.gregtechceu.gtceu.data.recipe.GTRecipeTypes.ELECTRIC;
import static com.gregtechceu.gtceu.data.recipe.GTRecipeTypes.MULTIBLOCK;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;

public class GCYRRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, GCYR.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, GCYR.MOD_ID);

    public static final GTRecipeType OXYGEN_SPREADER_RECIPES = register("oxygen_spreader", ELECTRIC).setMaxIOSize(1, 0, 1, 0).setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public static final GTRecipeType DYSON_ENERGY_RECIPES = register("dyson_controller", MULTIBLOCK)
            .setMaxIOSize(8, 0, 0, 0).setEUIO(IO.BOTH)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.PORTABLE_SCANNER);

    public static final GTRecipeType SPACE_ELEVATOR_RECIPES = register("space_elevator", MULTIBLOCK).setMaxIOSize(16, 1, 4, 0).setEUIO(IO.IN)
            .setProgressBar(GCYRGuiTextures.PROGRESS_BAR_ROCKET, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.PORTABLE_SCANNER);

    public static final GTRecipeType ROCKET_FUEL_RECIPES = register("rocket_fuel", MULTIBLOCK).setMaxIOSize(0, 0, 1, 0).setEUIO(IO.IN)
            .setProgressBar(GCYRGuiTextures.PROGRESS_BAR_ROCKET, LEFT_TO_RIGHT)
            .setSound(GCYRSoundEntries.ROCKET);

    public static GTRecipeType register(String name, String group, RecipeType<?>... proxyRecipes) {
        var recipeType = new GTRecipeType(GCYR.id(name), group, proxyRecipes);
        RECIPE_TYPES.register(name, () -> recipeType);
        GTRegistries.register(BuiltInRegistries.RECIPE_TYPE, recipeType.registryName, recipeType);
        GTRecipeSerializer serializer = new GTRecipeSerializer();
        RECIPE_SERIALIZERS.register(name, () -> serializer);
        //noinspection UnstableApiUsage
        recipeType.setSerializer(serializer);
        GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
        return recipeType;
    }

    public static void register(IEventBus modBus) {
        RECIPE_TYPES.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
    }
}
