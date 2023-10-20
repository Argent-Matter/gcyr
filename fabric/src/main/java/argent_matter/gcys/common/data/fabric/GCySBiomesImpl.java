package argent_matter.gcys.common.data.fabric;

import argent_matter.gcys.common.data.GCySBiomes;
import net.minecraft.data.BuiltinRegistries;

public class GCySBiomesImpl {
    public static void init() {
        BuiltinRegistries.register(BuiltinRegistries.BIOME, GCySBiomes.SPACE, GCySBiomes.space());
    }
}

