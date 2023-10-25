package argent_matter.gcyr.common.data.fabric;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.worldgen.SpaceLevelSource;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class GCyRDimensionTypesImpl {
    public static void initGenerator() {
        GTRegistries.register(BuiltInRegistries.CHUNK_GENERATOR, GCyR.id("space"), SpaceLevelSource.CODEC);
    }
}
