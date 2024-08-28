package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.worldgen.SpaceLevelSource;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GCYRDimensionTypes {
    public static final ResourceKey<DimensionType> SPACE_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, GCYR.id("space"));

    private static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATOR_REGISTER = DeferredRegister.create(Registries.CHUNK_GENERATOR, GCYR.MOD_ID);

    public static void register(IEventBus bus) {
        CHUNK_GENERATOR_REGISTER.register(bus);
    }

    public static void init() {
        initGenerator();
    }

    public static void initGenerator() {
        CHUNK_GENERATOR_REGISTER.register("space", () -> SpaceLevelSource.CODEC);
    }
}
