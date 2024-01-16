package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionType;

public class GCyRDimensionTypes {
    public static final ResourceKey<DimensionType> SPACE_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, GCyR.id("space"));

    public static void init() {
        initGenerator();
    }

    @ExpectPlatform
    public static void initGenerator() {
        throw new AssertionError();
    }
}
