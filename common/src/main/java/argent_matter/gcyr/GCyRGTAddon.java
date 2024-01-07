package argent_matter.gcyr;

import argent_matter.gcyr.common.data.GCyRBlocks;
import argent_matter.gcyr.common.data.GCyRMaterials;
import argent_matter.gcyr.common.data.GCyRRecipes;
import argent_matter.gcyr.common.data.GCyRSoundEntries;
import argent_matter.gcyr.common.worldgen.GCyRWorldGenLayers;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconType;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.Conditions.hasOreProperty;

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
        TagPrefix.oreTagPrefix("mars")
                .langValue("Mars %s Ore")
                .materialIconType(MaterialIconType.ore)
                .miningToolTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .unificationEnabled(true)
                .generationCondition(hasOreProperty)
                .registerOre(() -> GCyRBlocks.MARTIAN_ROCK.orElse(Blocks.DEEPSLATE).defaultBlockState());
        TagPrefix.oreTagPrefix("venus")
                .langValue("Venus %s Ore")
                .materialIconType(MaterialIconType.ore)
                .miningToolTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .unificationEnabled(true)
                .generationCondition(hasOreProperty)
                .registerOre(() -> GCyRBlocks.VENUS_ROCK.orElse(Blocks.DEEPSLATE).defaultBlockState());
        TagPrefix.oreTagPrefix("mercury")
                .langValue("Mercury %s Ore")
                .materialIconType(MaterialIconType.ore)
                .miningToolTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .unificationEnabled(true)
                .generationCondition(hasOreProperty)
                .registerOre(() -> GCyRBlocks.MERCURY_ROCK.orElse(Blocks.STONE).defaultBlockState());
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
        GCyRWorldGenLayers.init();
    }

    @Override
    public boolean requiresHighTier() {
        return false;
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