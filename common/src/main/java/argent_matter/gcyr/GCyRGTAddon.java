package argent_matter.gcyr;

import argent_matter.gcyr.common.data.*;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconType;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.data.worldgen.SimpleWorldGenLayer;
import com.gregtechceu.gtceu.api.data.worldgen.WorldGeneratorUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.Set;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.Conditions.hasOreProperty;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.LoaderType.FABRIC;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.LoaderType.FORGE;

@GTAddon
public class GCyRGTAddon implements IGTAddon {
    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return GCyR.MOD_ID;
    }

    @Override
    public void registerTagPrefixes() {
        TagPrefix.oreTagPrefix("moon")
                .langValue("Moon %s Ore")
                .materialIconType(MaterialIconType.ore)
                .miningToolTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .unificationEnabled(true)
                .generationCondition(hasOreProperty)
                .registerOre(() -> GCyRBlocks.MOON_STONE.orElse(Blocks.STONE).defaultBlockState());
    }

    @Override
    public void registerElements() {
        IGTAddon.super.registerElements();
    }

    @Override
    public void registerMaterials() {
        GCyRMaterials.init();
    }

    @Override
    public void registerSounds() {
        GCyRSoundEntries.init();
    }

    @Override
    public void registerCovers() {
        IGTAddon.super.registerCovers();
    }

    @Override
    public void registerWorldgenLayers() {
        WorldGeneratorUtils.WORLD_GEN_LAYERS.put("moon", new SimpleWorldGenLayer("moon", () -> new BlockMatchTest(GCyRBlocks.MOON_STONE.get()), Set.of(GCyR.id("luna"))));
    }

    @Override
    public boolean requiresHighTier() {
        return true;
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
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        GCyRRecipes.init(provider);
    }
}
