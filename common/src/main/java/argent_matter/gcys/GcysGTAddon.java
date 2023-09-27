package argent_matter.gcys;

import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.common.data.*;
import argent_matter.gcys.data.GcysDatagen;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.GregTechDatagen;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@GTAddon
public class GcysGTAddon implements IGTAddon {
    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return GregicalitySpace.MOD_ID;
    }

    @Override
    public void registerTagPrefixes() {
        IGTAddon.super.registerTagPrefixes();
    }

    @Override
    public void registerElements() {
        IGTAddon.super.registerElements();
    }

    @Override
    public void registerMaterials() {
        GcysMaterials.init();
    }

    @Override
    public void registerSounds() {
        GcysSoundEntries.init();
    }

    @Override
    public void registerCovers() {
        IGTAddon.super.registerCovers();
    }

    @Override
    public void registerRecipeTypes() {
        GcysRecipeTypes.init();
    }

    @Override
    public void registerMachines() {
        GcysMachines.init();
    }

    @Override
    public void registerWorldgenLayers() {
        IGTAddon.super.registerWorldgenLayers();
    }

    @Override
    public void registerVeinGenerators() {
        IGTAddon.super.registerVeinGenerators();
    }

    @Override
    public void collectMaterialCasings(MaterialCasingCollectionEvent event) {
        IGTAddon.super.collectMaterialCasings(event);
    }

    @Override
    public void initializeRecipes(Consumer<FinishedRecipe> provider) {
        GcysRecipes.init(provider);
    }
}
