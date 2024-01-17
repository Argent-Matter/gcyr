package argent_matter.gcyr.common.worldgen;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.data.GCyRBlocks;
import com.gregtechceu.gtceu.api.data.worldgen.IWorldGenLayer;
import com.gregtechceu.gtceu.api.data.worldgen.SimpleWorldGenLayer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.Set;

public class GCyRWorldGenLayers {

    public static IWorldGenLayer MOON = new SimpleWorldGenLayer("moon", () -> new BlockMatchTest(GCyRBlocks.MOON_STONE.get()), Set.of(GCyR.id("luna")));
    public static IWorldGenLayer MARS = new SimpleWorldGenLayer("mars", () -> new BlockMatchTest(GCyRBlocks.MARTIAN_ROCK.get()), Set.of(GCyR.id("mars")));
    public static IWorldGenLayer MERCURY = new SimpleWorldGenLayer("mercury", () -> new BlockMatchTest(GCyRBlocks.MERCURY_ROCK.get()), Set.of(GCyR.id("mercury")));
    public static IWorldGenLayer VENUS = new SimpleWorldGenLayer("venus", () -> new BlockMatchTest(GCyRBlocks.VENUS_ROCK.get()), Set.of(GCyR.id("venus")));

    public static void init() {

    }

}
