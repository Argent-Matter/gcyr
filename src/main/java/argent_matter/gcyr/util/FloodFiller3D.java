package argent_matter.gcyr.util;

import argent_matter.gcyr.config.GCYRConfig;
import argent_matter.gcyr.common.data.tag.GCYRTags;

import com.google.common.math.DoubleMath;
import com.gregtechceu.gtceu.utils.GTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.mojang.datafixers.util.Pair;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * FloodFiller3D borrowed from Ad Astra.
 * <a href=
 * "https://github.com/terrarium-earth/Ad-Astra/blob/1.19/common/src/main/java/earth/terrarium/ad_astra/common/util/algorithm/FloodFiller3D.java">
 * github link</a>
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
            Direction targetDir = pair.getSecond();
            BlockPos pos = pair.getFirst().relative(targetDir);
            iterator.remove();

            BlockState state = level.getBlockState(pos);

            if (state.is(GCYRTags.Blocks.BLOCKS_FLOOD_FILL)) continue;

            if (!state.isAir() && !state.is(GCYRTags.Blocks.PASSES_FLOOD_FILL)) {
                VoxelShape collisionShape = state.getCollisionShape(level, pos);
                if (!collisionShape.isEmpty()) {
                    if (isFaceSolid(collisionShape, targetDir)) continue;
                    if (isFaceSolid(collisionShape, targetDir.getOpposite())) continue;
                }
            }

            positions.add(pos);

            for (Direction dir : GTUtil.DIRECTIONS) {
                // skip this iteration's source direction, we know it's already checked
                if (dir == targetDir.getOpposite()) continue;
                if (!positions.contains(pos.relative(dir))) {
                    queue.add(Pair.of(pos, dir));
                }
            }
        }

        return positions;
    }

    private static boolean isFaceSolid(VoxelShape collisionShape, Direction dir) {
        VoxelShape faceShape = collisionShape.getFaceShape(dir);
        if (faceShape.isEmpty()) return false;
        return checkBounds(faceShape, dir.getAxis());
    }

    private static boolean checkBounds(VoxelShape shape, Direction.Axis axis) {
        return switch (axis) {
            case X -> isFull(shape, Direction.Axis.Y) && isFull(shape, Direction.Axis.Z);
            case Y -> isFull(shape, Direction.Axis.X) && isFull(shape, Direction.Axis.Z);
            case Z -> isFull(shape, Direction.Axis.X) && isFull(shape, Direction.Axis.Y);
        };
    }

    private static boolean isFull(VoxelShape shape, Direction.Axis face) {
        return DoubleMath.fuzzyEquals(shape.min(face), 0.0D, 1.0E-7D) &&
                DoubleMath.fuzzyEquals(shape.max(face), 1.0D, 1.0E-7D);
    }
}
