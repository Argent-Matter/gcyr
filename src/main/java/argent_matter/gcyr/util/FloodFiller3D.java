package argent_matter.gcyr.util;

import argent_matter.gcyr.config.GCYRConfig;
import argent_matter.gcyr.data.recipe.GCYRTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * FloodFiller3D borrowed from Ad Astra.
 * <a href="https://github.com/terrarium-earth/Ad-Astra/blob/1.19/common/src/main/java/earth/terrarium/ad_astra/common/util/algorithm/FloodFiller3D.java">github link</a>
 */
public class FloodFiller3D {
    public static Set<BlockPos> run(Level level, BlockPos start, Direction startFacing) {
        Set<BlockPos> positions = new LinkedHashSet<>();
        Set<Pair<BlockPos, Direction>> queue = new LinkedHashSet<>();
        queue.add(Pair.of(start, startFacing));

        while (!queue.isEmpty()) {
            if (positions.size() >= GCYRConfig.INSTANCE.server.maxOxygenatedBlockChecks) break;

            var iterator = queue.iterator();
            var pair = iterator.next();
            BlockPos pos = pair.getFirst().relative(pair.getSecond());
            iterator.remove();

            BlockState state = level.getBlockState(pos);

            /*if (runAdditionalChecks(level, state, pos)) {
                continue;
            } else */{
                if (state.is(GCYRTags.BLOCKS_FLOOD_FILL)) continue;
                VoxelShape collisionShape = state.getCollisionShape(level, pos);
                if (!state.isAir() && !state.is(GCYRTags.PASSES_FLOOD_FILL) && !collisionShape.isEmpty() && isSideSolid(collisionShape, pair.getSecond(), state) && (isFaceSturdy(collisionShape, pair.getSecond(), state) || isFaceSturdy(collisionShape, pair.getSecond().getOpposite(), state))) {
                    continue;
                }
            }

            positions.add(pos);

            for (Direction dir : Direction.values()) {
                if (!positions.contains(pos.relative(dir))) {
                    queue.add(Pair.of(pos, dir));
                }
            }
        }

        return positions;
    }

    /*private static boolean runAdditionalChecks(Level level, BlockState state, BlockPos pos) {
        Block block = state.getBlock();
        if (block instanceof SlidingDoorBlock door) {
            BlockState blockState = level.getBlockState(door.getMainPos(state, pos));
            Optional<Boolean> open = blockState.getOptionalValue(SlidingDoorBlock.OPEN);
            Optional<Boolean> powered = blockState.getOptionalValue(SlidingDoorBlock.POWERED);
            return (open.isPresent() && !open.get()) && (powered.isPresent() && !powered.get());
        }
        return false;
    }*/

    private static boolean isSideSolid(VoxelShape collisionShape, Direction dir, BlockState state) {
        return checkBounds(collisionShape.bounds(), dir.getAxis());
    }

    private static boolean isFaceSturdy(VoxelShape collisionShape, Direction dir, BlockState state) {
        VoxelShape faceShape = collisionShape.getFaceShape(dir);
        if (faceShape.isEmpty()) return true;
        return checkBounds(faceShape.toAabbs().get(0), dir.getAxis());
    }

    private static boolean checkBounds(AABB bounds, Direction.Axis axis) {
        return switch (axis) {
            case X -> bounds.minY <= 0 && bounds.maxY >= 1 && bounds.minZ <= 0 && bounds.maxZ >= 1;
            case Y -> bounds.minX <= 0 && bounds.maxX >= 1 && bounds.minZ <= 0 && bounds.maxZ >= 1;
            case Z -> bounds.minX <= 0 && bounds.maxX >= 1 && bounds.minY <= 0 && bounds.maxY >= 1;
        };
    }
}
