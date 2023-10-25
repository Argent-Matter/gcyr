package argent_matter.gcyr.common.data.fabric;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.worldgen.SpaceLevelSource;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import net.minecraft.core.Registry;

public class GCyRDimensionTypesImpl {
    public static void initGenerator() {
        GTRegistries.register(Registry.CHUNK_GENERATOR, GCyR.id("space"), SpaceLevelSource.CODEC);
    }
}
