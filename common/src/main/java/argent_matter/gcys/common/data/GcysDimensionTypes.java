package argent_matter.gcys.common.data;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.common.worldgen.SpaceLevelSource;
import argent_matter.gcys.data.recipe.GcysTags;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class GcysDimensionTypes {
    public static final ResourceKey<DimensionType> SPACE_DIMENSION = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, GregicalitySpace.id("space"));
    public static final ResourceKey<Level> SPACE_LEVEL = ResourceKey.create(Registry.DIMENSION_REGISTRY, GregicalitySpace.id("space"));
    public static final ResourceLocation SPACE_EFFECTS = GregicalitySpace.id("space");

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
                        GcysTags.INFINIBURN_SPACE,
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
