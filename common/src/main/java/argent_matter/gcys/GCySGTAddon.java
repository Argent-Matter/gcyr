package argent_matter.gcys;

import argent_matter.gcys.common.data.*;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@GTAddon
public class GCySGTAddon implements IGTAddon {
    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return GCyS.MOD_ID;
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
        GCySMaterials.init();
    }

    @Override
    public void registerSounds() {
        GCySSoundEntries.init();
    }

    @Override
    public void registerCovers() {
        IGTAddon.super.registerCovers();
    }

    @Override
    public void registerRecipeTypes() {
        GCySRecipeTypes.init();
    }

    @Override
    public void registerMachines() {
        GCySMachines.init();
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
        GCySRecipes.init(provider);
    }
}
