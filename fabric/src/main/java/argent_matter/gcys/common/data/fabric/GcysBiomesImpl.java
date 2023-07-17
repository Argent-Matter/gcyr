package argent_matter.gcys.common.data.fabric;

import argent_matter.gcys.common.data.GcysBiomes;
import net.minecraft.data.BuiltinRegistries;

public class GcysBiomesImpl {
    public static void init() {
        BuiltinRegistries.register(BuiltinRegistries.BIOME, GcysBiomes.SPACE, GcysBiomes.space());
    }
}

