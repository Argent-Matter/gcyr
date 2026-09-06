package argent_matter.gcyr.common.data.dimension;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.tag.GCYRTags;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class GCYRDimensionTypes {

    // spotless:off
    public static final ResourceKey<DimensionType> SPACE = register("space");
    public static final ResourceKey<DimensionType> LUNA = register("luna");
    public static final ResourceKey<DimensionType> MARS = register("mars");
    public static final ResourceKey<DimensionType> MERCURY = register("mercury");
    public static final ResourceKey<DimensionType> PROXIMA_CENTAURI_B = register("proxima_centauri_b");
    public static final ResourceKey<DimensionType> VENUS = register("venus");

    public static final ResourceLocation SPACE_EFFECTS = GCYR.id("space");
    public static final ResourceLocation LUNA_EFFECTS = GCYR.id("luna");
    public static final ResourceLocation MARS_EFFECTS = GCYR.id("mars");
    public static final ResourceLocation MERCURY_EFFECTS = GCYR.id("mercury");
    public static final ResourceLocation PROXIMA_CENTAURI_B_EFFECTS = GCYR.id("proxima_centauri_b");
    public static final ResourceLocation VENUS_EFFECTS = GCYR.id("venus");


    public static void bootstrap(BootstapContext<DimensionType> ctx) {
        ctx.register(SPACE, new DimensionType(
                OptionalLong.of(18000L),
                true, false,
                false, false, 1.0,
                false, false,
                0, 256, 256,
                GCYRTags.Blocks.INFINIBURN_SPACE,
                SPACE_EFFECTS,
                0.1f,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)
        ));
        ctx.register(LUNA, new DimensionType(
                OptionalLong.empty(),
                true, false,
                true, true, 1.0,
                false, false,
                -64, 384, 384,
                GCYRTags.Blocks.INFINIBURN_LUNA,
                LUNA_EFFECTS,
                0.0f,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
        ctx.register(MARS, new DimensionType(
                OptionalLong.empty(),
                true, false,
                false, true, 1.0,
                false, false,
                -64, 384, 384,
                GCYRTags.Blocks.INFINIBURN_MARS,
                MARS_EFFECTS,
                0.0f,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)
        ));
        ctx.register(MERCURY, new DimensionType(
                OptionalLong.empty(),
                true, false,
                true, true, 1.0,
                false, false,
                -64, 384, 384,
                GCYRTags.Blocks.INFINIBURN_MERCURY,
                MERCURY_EFFECTS,
                0.0f,
                new DimensionType.MonsterSettings(true, false, UniformInt.of(0, 7), 0)
        ));
        ctx.register(VENUS, new DimensionType(
                OptionalLong.of(6000L),
                true, false,
                true, true, 1.0,
                false, false,
                -64, 384, 384,
                GCYRTags.Blocks.INFINIBURN_VENUS,
                VENUS_EFFECTS,
                0.0f,
                new DimensionType.MonsterSettings(true, false, UniformInt.of(0, 7), 0)
        ));
        ctx.register(PROXIMA_CENTAURI_B, new DimensionType(
                OptionalLong.of(18000L),
                true, false,
                false, true, 1.0,
                false, false,
                -64, 384, 384,
                GCYRTags.Blocks.INFINIBURN_PROXIMA_CENTAURI_B,
                PROXIMA_CENTAURI_B_EFFECTS,
                0.0f,
                new DimensionType.MonsterSettings(true, false, UniformInt.of(0, 7), 0)
        ));
    }
    // spotless:on

    private static ResourceKey<DimensionType> register(String path) {
        return ResourceKey.create(Registries.DIMENSION_TYPE, GCYR.id(path));
    }
}
