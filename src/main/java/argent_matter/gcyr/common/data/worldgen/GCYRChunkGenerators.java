package argent_matter.gcyr.common.data.worldgen;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.worldgen.SpaceLevelSource;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import com.mojang.serialization.Codec;

import org.jetbrains.annotations.ApiStatus;

public class GCYRChunkGenerators {

    // spotless:off
    private static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegister.create(Registries.CHUNK_GENERATOR, GCYR.MOD_ID);

    public static final RegistryObject<Codec<SpaceLevelSource>> SPACE = CHUNK_GENERATORS.register("space", () -> SpaceLevelSource.CODEC);
    // spotless:on

    @ApiStatus.Internal
    public static void register(IEventBus bus) {
        CHUNK_GENERATORS.register(bus);
    }
}
