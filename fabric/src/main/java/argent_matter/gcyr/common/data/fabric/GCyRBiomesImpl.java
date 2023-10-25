package argent_matter.gcyr.common.data.fabric;

import argent_matter.gcyr.common.data.GCyRBiomes;
import net.minecraft.data.BuiltinRegistries;

public class GCyRBiomesImpl {
    public static void init() {
        BuiltinRegistries.register(BuiltinRegistries.BIOME, GCyRBiomes.SPACE, GCyRBiomes.space());
    }
}

