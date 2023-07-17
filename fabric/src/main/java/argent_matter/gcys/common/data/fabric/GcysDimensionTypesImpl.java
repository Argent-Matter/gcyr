package argent_matter.gcys.common.data.fabric;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.common.worldgen.SpaceLevelSource;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import net.minecraft.core.Registry;

public class GcysDimensionTypesImpl {
    public static void initGenerator() {
        GTRegistries.register(Registry.CHUNK_GENERATOR, GregicalitySpace.id("space"), SpaceLevelSource.CODEC);
    }
}
