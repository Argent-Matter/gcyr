package argent_matter.gcyr;

import argent_matter.gcyr.api.registries.GCyRRegistries;
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
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.Conditions.hasOreProperty;

@GTAddon
public class GCyRGTAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return GCyRRegistries.REGISTRATE;
    }

    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return GCyR.MOD_ID;
    }

    @Override
    public void registerTagPrefixes() {
        TagPrefix.oreTagPrefix("moon", BlockTags.MINEABLE_WITH_PICKAXE)
                .langValue("Moon %s Ore")
                .registerOre(() -> GCyRBlocks.MOON_STONE.orElse(Blocks.STONE).defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops().strength(3.0F, 3.0F), GCyR.id("block/moon_stone"));
        TagPrefix.oreTagPrefix("mars", BlockTags.MINEABLE_WITH_PICKAXE)
                .langValue("Mars %s Ore")
                .registerOre(() -> GCyRBlocks.MARTIAN_ROCK.orElse(Blocks.DEEPSLATE).defaultBlockState(), () -> GCyRMaterials.MartianRockDust, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops().strength(3.0F, 3.0F), GCyR.id("block/martian_rock"));
        TagPrefix.oreTagPrefix("venus", BlockTags.MINEABLE_WITH_PICKAXE)
                .langValue("Venus %s Ore")
                .registerOre(() -> GCyRBlocks.VENUS_ROCK.orElse(Blocks.DEEPSLATE).defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), GCyR.id("block/venus_rock"));
        TagPrefix.oreTagPrefix("mercury", BlockTags.MINEABLE_WITH_PICKAXE)
                .langValue("Mercury %s Ore")
                .registerOre(() -> GCyRBlocks.MERCURY_ROCK.orElse(Blocks.STONE).defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(3.0F, 3.0F), GCyR.id("block/moon_stone"));
    }

    @Override
    public void registerElements() {
        IGTAddon.super.registerElements();
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