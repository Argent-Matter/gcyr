package screret.gcys.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import screret.gcys.common.worldgen.SpaceLevelSource;

import java.util.OptionalLong;

public class GcysDimensionTypes {
    public static final ResourceKey<DimensionType> SPACE_DIMENSION = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, GTCEu.id("space"));
    public static final ResourceKey<Level> SPACE_LEVEL = ResourceKey.create(Registry.DIMENSION_REGISTRY, GTCEu.id("space"));
    public static final ResourceLocation SPACE_EFFECTS = GTCEu.id("space");

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
                        CustomTags.INFINIBURN_SPACE,
                        SPACE_EFFECTS,
                        0.0F,
                        new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
        GTRegistries.register(Registry.CHUNK_GENERATOR, GTCEu.id("space"), SpaceLevelSource.CODEC);
    }
}
