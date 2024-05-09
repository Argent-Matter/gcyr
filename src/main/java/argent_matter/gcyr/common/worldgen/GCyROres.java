package argent_matter.gcyr.common.worldgen;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.data.recipe.GCyRTags;
import com.gregtechceu.gtceu.api.data.worldgen.GTLayerPattern;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.generator.indicators.SurfaceIndicatorGenerator;
import com.gregtechceu.gtceu.api.data.worldgen.generator.veins.DikeVeinGenerator;
import com.gregtechceu.gtceu.api.data.worldgen.generator.veins.VeinedVeinGenerator;
import com.gregtechceu.gtceu.common.data.GTOres;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTOres.create;

@SuppressWarnings("unused")
public class GCyROres {

   // TODO Tweak Ore Generation... Maybe by community feedback
    public static final RuleTest MOON_ORE_REPLACEABLES = new TagMatchTest(GCyRTags.MOON_ORE_REPLACEABLES);
    public static RuleTest[] MOON_RULES = new RuleTest[]{MOON_ORE_REPLACEABLES};

    public static final GTOreDefinition BAUXITE_VEIN_MOON = create(GCyR.id("bauxite_vein_moon"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.MOON)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_MOON)
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
            ));

// mars ores
    public static final RuleTest MARS_ORE_REPLACEABLES = new TagMatchTest(GCyRTags.MARS_ORE_REPLACEABLES);
    public static RuleTest[] MARS_RULES = new RuleTest[]{MARS_ORE_REPLACEABLES};

    public static final GTOreDefinition TUNGSTATE_VEIN_MARS = create(GCyR.id("tungstate_vein_mars"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.MARS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_MARS)
            .dikeVeinGenerator(generator -> generator
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Tungstate, 2, 18, 80))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Scheelite, 2, 15, 80))
            )
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Tungstate)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
            ));

    public static final GTOreDefinition IRON_VEIN = create(GCyR.id("iron_vein_mars"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.MARS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_MARS)
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
            ));


    public static final GTOreDefinition NICKEL_VEIN_MARS = create(GCyR.id("nickel_vein_mars"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.MARS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_MARS)
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
            ));


    public static final GTOreDefinition RARE_EARTH_METAL_VEIN_MARS = create(GCyR.id("rare_earth_metal_vein_mars"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.MARS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_MARS)
            .dikeVeinGenerator(generator -> generator
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Monazite, 2, 18, 80))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Molybdenite, 2, 16, 80))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Bastnasite, 2, 14, 80))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Neodymium, 1, 12, 80))
            )
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Molybdenite)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
            ));

    public static final GTOreDefinition COPPER_VEIN_MARS = create(GCyR.id("copper_vein_mars"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.MARS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_MARS)
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
            ));

    public static final GTOreDefinition ZINC_VEIN_MARS = create(GCyR.id("zinc_vein_mars"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.MARS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_MARS)
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
            ));

    public static final GTOreDefinition TUNGSTOTITANATE_VEIN_VENUS = create(GCyR.id("tungstotitanate_vein_venus"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.VENUS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_VENUS)
            .dikeVeinGenerator(generator -> generator
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Tungstate, 8, 18, 80))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Scheelite, 4, 15, 80))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Bauxite, 2, 15, 80))
            )
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Tungstate)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
            ));

    public static final GTOreDefinition SULFUR_VEIN_VENUS = create(GCyR.id("sulfurc_vein_venus"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.VENUS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_VENUS)
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
            ));

    public static final GTOreDefinition COPPER_VEIN_VENUS = create(GCyR.id("copper_vein_venus"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.VENUS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_VENUS)
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
            ));

    public static final GTOreDefinition IRON_VEIN_VENUS = create(GCyR.id("iron_vein_venus"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.VENUS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_VENUS)
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
            ));

    public static final GTOreDefinition MERCURY_VEIN_MERCURY = GTOres.create(GCyR.id("mercury_vein_mercury"), vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.MERCURY)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_MERCURY)
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
            ));

    public static void init() {

    }
}