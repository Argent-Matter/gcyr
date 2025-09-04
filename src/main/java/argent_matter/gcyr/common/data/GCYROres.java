package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.worldgen.GCYRWorldGenLayers;
import argent_matter.gcyr.data.recipe.GCYRTags;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.worldgen.BiomeWeightModifier;
import com.gregtechceu.gtceu.api.worldgen.GTLayerPattern;
import com.gregtechceu.gtceu.api.worldgen.IWorldGenLayer;
import com.gregtechceu.gtceu.api.worldgen.OreVeinDefinition;
import com.gregtechceu.gtceu.api.worldgen.generator.indicators.SurfaceIndicatorGenerator;
import com.gregtechceu.gtceu.api.worldgen.generator.veins.DikeVeinGenerator;
import com.gregtechceu.gtceu.api.worldgen.generator.veins.NoopVeinGenerator;
import com.gregtechceu.gtceu.api.worldgen.generator.veins.VeinedVeinGenerator;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.ArrayList;
import java.util.Set;

import static com.gregtechceu.gtceu.data.material.GTMaterials.*;

@SuppressWarnings("unused")
public class GCYROres {
    public static ResourceKey<OreVeinDefinition> key(String name) {
        return ResourceKey.create(GTRegistries.ORE_VEIN_REGISTRY, GCYR.id(name));
    }
    
    public static final ResourceKey<OreVeinDefinition> BAUXITE_VEIN_MOON_KEY = key("bauxite_vein_moon");
    public static final ResourceKey<OreVeinDefinition> TUNGSTATE_VEIN_MARS_KEY = key("tungstate_vein_mars");
    public static final ResourceKey<OreVeinDefinition> IRON_VEIN_KEY = key("iron_vein");
    public static final ResourceKey<OreVeinDefinition> NICKEL_VEIN_MARS_KEY = key("nickel_vein_mars");
    public static final ResourceKey<OreVeinDefinition> RARE_EARTH_METAL_VEIN_MARS_KEY = key("rare_earth_metal_vein_mars");
    public static final ResourceKey<OreVeinDefinition> COPPER_VEIN_MARS_KEY = key("copper_vein_mars"); 
    public static final ResourceKey<OreVeinDefinition> ZINC_VEIN_MARS_KEY = key("zinc_vein_mars");
    public static final ResourceKey<OreVeinDefinition> TUNGSTOTITANATE_VEIN_VENUS_KEY = key("tungstotitanate_vein_venus");
    public static final ResourceKey<OreVeinDefinition> SULFUR_VEIN_VENUS_KEY = key("sulfur_vein_venus");
    public static final ResourceKey<OreVeinDefinition> COPPER_VEIN_VENUS_KEY = key("copper_vein_venus");
    public static final ResourceKey<OreVeinDefinition> IRON_VEIN_VENUS_KEY = key("iron_vein_venus");
    public static final ResourceKey<OreVeinDefinition> MERCURY_VEIN_MERCURY_KEY = key("mercury_vein_mercury");
    
    public static OreVeinDefinition blankVein(BootstrapContext<OreVeinDefinition> context) {
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);
        return new OreVeinDefinition(ConstantInt.ZERO, 0, 0, IWorldGenLayer.NOWHERE, Set.of(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(0)), 0, HolderSet.empty(), BiomeWeightModifier.EMPTY, NoopVeinGenerator.INSTANCE, new ArrayList<>(), biomeLookup);
    }

    public static void bootstrap(BootstrapContext<OreVeinDefinition> context) {
        RuleTest MOON_ORE_REPLACEABLES = new TagMatchTest(GCYRTags.MOON_ORE_REPLACEABLES);
        RuleTest[] MOON_RULES = new RuleTest[]{MOON_ORE_REPLACEABLES};

        // Moon
        OreVeinDefinition BAUXITE_VEIN_MOON = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.MOON)
                .heightRangeUniform(10, 80)
                .layeredVeinGenerator(generator -> generator
                        .withLayerPattern(() -> GTLayerPattern.builder(MOON_RULES)
                                .layer(l -> l.weight(2).mat(Bauxite).size(1, 4))
                                .layer(l -> l.weight(1).mat(Ilmenite).size(1, 2))
                                .layer(l -> l.weight(1).mat(Aluminium).size(1, 1))
                                .build())
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Bauxite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                )
                .biomes(GCYRTags.IS_MOON);

        // Mars
        RuleTest MARS_ORE_REPLACEABLES = new TagMatchTest(GCYRTags.MARS_ORE_REPLACEABLES);
        RuleTest[] MARS_RULES = new RuleTest[]{MARS_ORE_REPLACEABLES};

        OreVeinDefinition TUNGSTATE_VEIN_MARS = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.MARS)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_MARS)
                .dikeVeinGenerator(generator -> generator
                        .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Tungstate, 2, 18, 80))
                        .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Scheelite, 2, 15, 80))
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Tungstate)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );

        OreVeinDefinition IRON_VEIN = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.MARS)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_MARS)
                .veinedVeinGenerator(generator -> generator
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Iron, 4))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Hematite, 3))
                        .rareBlock(new VeinedVeinGenerator.VeinBlockDefinition(Gold, 1))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Pyrite, 3))
                        .rareBlockChance(0.33f)
                        .veininessThreshold(5.1f)
                        .maxRichnessThreshold(5.4f)
                        .minRichness(0.2f)
                        .maxRichness(0.5f)
                        .edgeRoundoffBegin(12)
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Hematite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );


        OreVeinDefinition NICKEL_VEIN_MARS = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.MARS)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_MARS)
                .layeredVeinGenerator(generator -> generator
                        .withLayerPattern(() -> GTLayerPattern.builder(MARS_RULES)
                                .layer(l -> l.weight(3).mat(Nickel).size(1, 4))
                                .layer(l -> l.weight(2).mat(Garnierite).size(1, 8))
                                .layer(l -> l.weight(2).mat(Pentlandite).size(1, 5))
                                .layer(l -> l.weight(2).mat(Cobaltite).size(1, 16))
                                .build())
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Garnierite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );


        OreVeinDefinition RARE_EARTH_METAL_VEIN_MARS = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.MARS)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_MARS)
                .dikeVeinGenerator(generator -> generator
                        .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Monazite, 2, 18, 80))
                        .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Molybdenite, 2, 16, 80))
                        .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Bastnasite, 2, 14, 80))
                        .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Neodymium, 1, 12, 80))
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Molybdenite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );

        OreVeinDefinition COPPER_VEIN_MARS = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.MARS)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_MARS)
                .veinedVeinGenerator(generator -> generator
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Copper, 4))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Tetrahedrite, 3))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Chalcopyrite, 2))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Pyrite, 1))
                        .rareBlockChance(0.33f)
                        .veininessThreshold(5.1f)
                        .maxRichnessThreshold(5.4f)
                        .minRichness(0.2f)
                        .maxRichness(0.5f)
                        .edgeRoundoffBegin(12)
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Chalcopyrite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );

        OreVeinDefinition ZINC_VEIN_MARS = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.MARS)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_MARS)
                .veinedVeinGenerator(generator -> generator
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Tetrahedrite, 4))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Copper, 3))
                        .rareBlock(new VeinedVeinGenerator.VeinBlockDefinition(Stibnite, 1))
                        .rareBlockChance(0.33f)
                        .veininessThreshold(5.1f)
                        .maxRichnessThreshold(5.4f)
                        .minRichness(0.2f)
                        .maxRichness(0.5f)
                        .edgeRoundoffBegin(12)
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Stibnite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );

        OreVeinDefinition TUNGSTOTITANATE_VEIN_VENUS = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.VENUS)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_VENUS)
                .dikeVeinGenerator(generator -> generator
                        .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Tungstate, 8, 18, 80))
                        .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Scheelite, 4, 15, 80))
                        .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Bauxite, 2, 15, 80))
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Tungstate)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );

        OreVeinDefinition SULFUR_VEIN_VENUS = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.VENUS)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_VENUS)
                .veinedVeinGenerator(generator -> generator
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Sulfur, 5))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Tetrahedrite, 1))
                        .rareBlock(new VeinedVeinGenerator.VeinBlockDefinition(Sphalerite, 1))
                        .rareBlockChance(0.33f)
                        .veininessThreshold(5.1f)
                        .maxRichnessThreshold(5.4f)
                        .minRichness(0.2f)
                        .maxRichness(0.5f)
                        .edgeRoundoffBegin(12)
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Sulfur)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );

        OreVeinDefinition COPPER_VEIN_VENUS = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.VENUS)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_VENUS)
                .veinedVeinGenerator(generator -> generator
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Copper, 4))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Tetrahedrite, 3))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Chalcopyrite, 2))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Pyrite, 1))
                        .rareBlockChance(0.33f)
                        .veininessThreshold(5.1f)
                        .maxRichnessThreshold(5.4f)
                        .minRichness(0.2f)
                        .maxRichness(0.5f)
                        .edgeRoundoffBegin(12)
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Chalcopyrite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );

        OreVeinDefinition IRON_VEIN_VENUS = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.VENUS)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_MARS)
                .veinedVeinGenerator(generator -> generator
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Iron, 4))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Hematite, 3))
                        .rareBlock(new VeinedVeinGenerator.VeinBlockDefinition(Gold, 1))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Pyrite, 3))
                        .rareBlockChance(0.33f)
                        .veininessThreshold(5.1f)
                        .maxRichnessThreshold(5.4f)
                        .minRichness(0.2f)
                        .maxRichness(0.5f)
                        .edgeRoundoffBegin(12)
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Hematite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );

        OreVeinDefinition MERCURY_VEIN_MERCURY = blankVein(context)
                .clusterSize(30).density(0.3f).weight(40)
                .layer(GCYRWorldGenLayers.MERCURY)
                .heightRangeUniform(10, 80)
                .biomes(GCYRTags.IS_MERCURY)
                .veinedVeinGenerator(generator -> generator
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Redstone, 4))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Cinnabar, 3))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Cinnabar, 2))
                        .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Ruby, 1))
                        .rareBlockChance(0.33f)
                        .veininessThreshold(5.1f)
                        .maxRichnessThreshold(5.4f)
                        .minRichness(0.2f)
                        .maxRichness(0.5f)
                        .edgeRoundoffBegin(12)
                )
                .surfaceIndicatorGenerator(indicator -> indicator
                        .surfaceRock(Cinnabar)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                );
        
        context.register(BAUXITE_VEIN_MOON_KEY, BAUXITE_VEIN_MOON);
        context.register(TUNGSTATE_VEIN_MARS_KEY, TUNGSTATE_VEIN_MARS);
        context.register(IRON_VEIN_KEY, IRON_VEIN);
        context.register(NICKEL_VEIN_MARS_KEY, NICKEL_VEIN_MARS);
        context.register(RARE_EARTH_METAL_VEIN_MARS_KEY, RARE_EARTH_METAL_VEIN_MARS);
        context.register(COPPER_VEIN_MARS_KEY, COPPER_VEIN_MARS);
        context.register(ZINC_VEIN_MARS_KEY, ZINC_VEIN_MARS);
        context.register(TUNGSTOTITANATE_VEIN_VENUS_KEY, TUNGSTOTITANATE_VEIN_VENUS);
        context.register(SULFUR_VEIN_VENUS_KEY, SULFUR_VEIN_VENUS);
        context.register(COPPER_VEIN_VENUS_KEY, COPPER_VEIN_VENUS);
        context.register(IRON_VEIN_VENUS_KEY, IRON_VEIN_VENUS);
        context.register(MERCURY_VEIN_MERCURY_KEY, MERCURY_VEIN_MERCURY);
    }
}
