package argent_matter.gcyr.common.worldgen;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.GCYRBlocks;
import com.gregtechceu.gtceu.api.data.worldgen.IWorldGenLayer;
import com.gregtechceu.gtceu.api.data.worldgen.SimpleWorldGenLayer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.Set;

public class GCYRWorldGenLayers {

    public static IWorldGenLayer MOON = new SimpleWorldGenLayer("moon", () -> new BlockMatchTest(GCYRBlocks.MOON_STONE.get()), Set.of(GCYR.id("luna")));
    public static IWorldGenLayer MARS = new SimpleWorldGenLayer("mars", () -> new BlockMatchTest(GCYRBlocks.MARTIAN_ROCK.get()), Set.of(GCYR.id("mars")));
    public static IWorldGenLayer MERCURY = new SimpleWorldGenLayer("mercury", () -> new BlockMatchTest(GCYRBlocks.MERCURY_ROCK.get()), Set.of(GCYR.id("mercury")));
    public static IWorldGenLayer VENUS = new SimpleWorldGenLayer("venus", () -> new BlockMatchTest(GCYRBlocks.VENUS_ROCK.get()), Set.of(GCYR.id("venus")));

    public static void init() {

    }

}
