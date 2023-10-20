package argent_matter.gcys.common.data;

import argent_matter.gcys.GCyS;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class GCySDimensionTypes {
    public static final ResourceKey<DimensionType> SPACE_DIMENSION = ResourceKey.create(Registries.DIMENSION_TYPE, GCyS.id("space"));
    public static final ResourceKey<Level> SPACE_LEVEL = ResourceKey.create(Registries.DIMENSION, GCyS.id("space"));

    public static void init() {
        initGenerator();
    }

    @ExpectPlatform
    public static void initGenerator() {
        throw new AssertionError();
    }
}
