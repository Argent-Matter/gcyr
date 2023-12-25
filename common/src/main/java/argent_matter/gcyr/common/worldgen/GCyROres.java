package argent_matter.gcyr.common.worldgen;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.data.recipe.GCyRTags;
import argent_matter.gcyr.mixin.GTOresAccessor;
import com.gregtechceu.gtceu.api.data.worldgen.GTLayerPattern;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.generator.indicators.SurfaceIndicatorGenerator;
import com.gregtechceu.gtceu.common.data.GTOres;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

@SuppressWarnings("unused")
public class GCyROres {
    public static final RuleTest MOON_ORE_REPLACEABLES = new TagMatchTest(GCyRTags.MOON_ORE_REPLACEABLES);
    public static RuleTest[] MOON_RULES = new RuleTest[]{MOON_ORE_REPLACEABLES};

    public static final GTOreDefinition BAUXITE_VEIN_MOON = create("bauxite_vein_moon", vein -> vein
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


    public static final RuleTest MARS_ORE_REPLACEABLES = new TagMatchTest(GCyRTags.MARS_ORE_REPLACEABLES);
    public static RuleTest[] MARS_RULES = new RuleTest[]{MARS_ORE_REPLACEABLES};

    public static final GTOreDefinition TITANIUM_VEIN = create("titanium_vein", vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.MARS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_MARS)
            .layeredVeinGenerator(generator -> generator
                    .withLayerPattern(() -> GTLayerPattern.builder(MARS_RULES)
                            .layer(l -> l.weight(1).mat(Titanium).size(1, 1))
                            .layer(l -> l.weight(4).mat(Bauxite).size(1, 2))
                            .layer(l -> l.weight(3).mat(Ilmenite).size(1, 1))
                            .build())
            )
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Titanium)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
            ));

    public static final GTOreDefinition TUNGSTATE_VEIN = create("tungstate_vein", vein -> vein
            .clusterSize(30).density(0.3f).weight(40)
            .layer(GCyRWorldGenLayers.MARS)
            .heightRangeUniform(10, 80)
            .biomes(GCyRTags.IS_MARS)
            .layeredVeinGenerator(generator -> generator
                    .withLayerPattern(() -> GTLayerPattern.builder(MARS_RULES)
                            .layer(l -> l.weight(3).mat(Tungstate).size(1, 2))
                            .layer(l -> l.weight(2).mat(Scheelite).size(1, 2))
                            .build())
            )
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Tungstate)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
            ));


    private static GTOreDefinition create(String name, Consumer<GTOreDefinition> config) {
        GTOreDefinition def = GTOres.blankOreDefinition();
        config.accept(def);

        ResourceLocation id = GCyR.id(name);
        def.register(id);
        GTOresAccessor.getToReRegister().put(id, def);

        return def;
    }

    public static void init() {

    }
}