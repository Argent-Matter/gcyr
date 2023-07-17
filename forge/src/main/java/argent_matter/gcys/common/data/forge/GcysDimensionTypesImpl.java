package argent_matter.gcys.common.data.forge;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.common.data.GcysBiomes;
import argent_matter.gcys.common.worldgen.SpaceLevelSource;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class GcysDimensionTypesImpl {

    private static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATOR_REGISTER = DeferredRegister.create(Registry.CHUNK_GENERATOR_REGISTRY, GregicalitySpace.MOD_ID);

    public static void register(IEventBus bus) {
        CHUNK_GENERATOR_REGISTER.register(bus);
    }

    public static void initGenerator() {
        CHUNK_GENERATOR_REGISTER.register(GcysBiomes.SPACE.location().getPath(), () -> SpaceLevelSource.CODEC);
    }
}
