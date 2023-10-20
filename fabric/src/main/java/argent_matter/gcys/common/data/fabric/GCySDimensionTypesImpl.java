package argent_matter.gcys.common.data.fabric;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.common.worldgen.SpaceLevelSource;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import net.minecraft.core.Registry;

public class GCySDimensionTypesImpl {
    public static void initGenerator() {
        GTRegistries.register(Registry.CHUNK_GENERATOR, GCyS.id("space"), SpaceLevelSource.CODEC);
    }
}
