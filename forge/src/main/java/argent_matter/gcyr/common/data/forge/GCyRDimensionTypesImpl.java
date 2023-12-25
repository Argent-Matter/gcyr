package argent_matter.gcyr.common.data.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.data.GCyRBiomes;
import argent_matter.gcyr.common.worldgen.SpaceLevelSource;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class GCyRDimensionTypesImpl {

    private static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATOR_REGISTER = DeferredRegister.create(Registries.CHUNK_GENERATOR, GCyR.MOD_ID);

    public static void register(IEventBus bus) {
        CHUNK_GENERATOR_REGISTER.register(bus);
    }

    public static void initGenerator() {
        CHUNK_GENERATOR_REGISTER.register("space", () -> SpaceLevelSource.CODEC);
    }
}
