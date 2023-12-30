package argent_matter.gcyr.common.worldgen;

import argent_matter.gcyr.common.data.GCyRBiomes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SpaceLevelSource extends ChunkGenerator {
    public static final Codec<SpaceLevelSource> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(RegistryOps.retrieveElement(GCyRBiomes.SPACE)).apply(instance, instance.stable(SpaceLevelSource::new))
    );

    public static final int PLATFORM_HEIGHT = 63;

    public SpaceLevelSource(Holder.Reference<Biome> biome) {
        super(new FixedBiomeSource(biome));
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion level, long seed, RandomState random, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk, GenerationStep.Carving step) {

    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState random, ChunkAccess chunk) {

    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {

    }

    @Override
    public int getGenDepth() {
        return 0;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState random, StructureManager structureManager, ChunkAccess chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor level, RandomState random) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor height, RandomState random) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState random, BlockPos pos) {

    }

    @Override
    public void applyBiomeDecoration(WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        ChunkPos chunkPos = chunk.getPos();
        int chunkX = chunkPos.x;
        int chunkZ = chunkPos.z;

        for (int x1 = 0; x1 < 16; ++x1) {
            for (int z1 = 0; z1 < 16; ++z1) {
                int blockX = SectionPos.sectionToBlockCoord(chunkX, x1);
                int blockZ = SectionPos.sectionToBlockCoord(chunkZ, z1);
                level.setBlock(mutableBlockPos.set(blockX, 256, blockZ), Blocks.BARRIER.defaultBlockState(), 2);
            }
        }

        // TODO fix space station platform building, re-enabling this for now.
        /*if (chunkX % 16 == 0 && chunkZ % 16 == 0) {
            //GCyR.LOGGER.info("made platform");
            for(int x = -4; x < 4; ++x) {
                for(int z = -4; z < 4; ++z) {
                    int blockX = SectionPos.sectionToBlockCoord(chunkX, x);
                    int blockZ = SectionPos.sectionToBlockCoord(chunkZ, z);
                    level.setBlock(mutableBlockPos.set(blockX, PLATFORM_HEIGHT, blockZ), Blocks.GRAY_CONCRETE.defaultBlockState(), 2);
                }
            }
        } else */if ((chunkX % 16 != 8 && chunkZ % 16 == 8) || (chunkX % 16 != -8 && chunkZ % 16 == -8)) {
            //GCyR.LOGGER.info("made Z edge");
            for(int x = 0; x < 16; ++x) {
                int blockX = SectionPos.sectionToBlockCoord(chunkX, x);
                int blockZ = SectionPos.sectionToBlockCoord(chunkZ, 15);
                for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); ++y) {
                    level.setBlock(mutableBlockPos.set(blockX, y, blockZ), Blocks.BARRIER.defaultBlockState(), 2);
                }
            }
        } else if ((chunkX % 16 == 8 && chunkZ % 16 != 8) || (chunkX % 16 == -8 && chunkZ % 16 != -8)) {
            //GCyR.LOGGER.info("made X edge");
            for(int z = 0; z < 16; ++z) {
                int blockX = SectionPos.sectionToBlockCoord(chunkX, 15);
                int blockZ = SectionPos.sectionToBlockCoord(chunkZ, z);
                for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); ++y) {
                    level.setBlock(mutableBlockPos.set(blockX, y, blockZ), Blocks.BARRIER.defaultBlockState(), 2);
                }
            }
        } else if (chunkX % 16 == 8 && chunkZ % 16 == 8) {
            //GCyR.LOGGER.info("made corner positive");
            for(int z = 0; z < 16; ++z) {
                int blockX = SectionPos.sectionToBlockCoord(chunkX, 15);
                int blockZ = SectionPos.sectionToBlockCoord(chunkZ, z);
                for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); ++y) {
                    if (z == 15) {
                        for (int x = 0; x < 16; ++x) {
                            blockX = SectionPos.sectionToBlockCoord(chunkX, x);
                            level.setBlock(mutableBlockPos.set(blockX, y, blockZ), Blocks.BARRIER.defaultBlockState(), 2);
                        }
                    } else {
                        level.setBlock(mutableBlockPos.set(blockX, y, blockZ), Blocks.BARRIER.defaultBlockState(), 2);
                    }
                }
            }
        } else if (chunkX % 16 == -8 && chunkZ % 16 == -8) {
            //GCyR.LOGGER.info("made corner negative");
            for(int z = 0; z < 16; ++z) {
                int blockX = SectionPos.sectionToBlockCoord(chunkX, 15);
                int blockZ = SectionPos.sectionToBlockCoord(chunkZ, z);
                for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); ++y) {
                    if (z == 0) {
                        for (int x = 0; x < 16; ++x) {
                            blockX = SectionPos.sectionToBlockCoord(chunkX, x);
                            level.setBlock(mutableBlockPos.set(blockX, y, blockZ), Blocks.BARRIER.defaultBlockState(), 2);
                        }
                    } else {
                        level.setBlock(mutableBlockPos.set(blockX, y, blockZ), Blocks.BARRIER.defaultBlockState(), 2);
                    }
                }
            }
        }
    }
}
