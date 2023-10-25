package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.data.recipe.GCyRTags;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class GCyRDimensionTypes {
    public static final ResourceKey<DimensionType> SPACE_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, GCyR.id("space"));

    public static final ResourceKey<DimensionType> SPACE_DIMENSION = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, GCyR.id("space"));
    public static final ResourceKey<Level> SPACE_LEVEL = ResourceKey.create(Registry.DIMENSION_REGISTRY, GCyR.id("space"));
    public static final ResourceLocation SPACE_EFFECTS = GCyR.id("space");

    public static void init() {
        BuiltinRegistries.register(BuiltinRegistries.DIMENSION_TYPE, SPACE_DIMENSION,
                new DimensionType(OptionalLong.of(18000L),
                        true,
                        false,
                        false,
                        false,
                        1.0,
                        false,
                        false,
                        0,
                        256,
                        256,
                        GCyRTags.INFINIBURN_SPACE,
                        SPACE_EFFECTS,
                        0.0F,
                        new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
        initGenerator();
    }

    @ExpectPlatform
    public static void initGenerator() {
        throw new AssertionError();
    }
}
