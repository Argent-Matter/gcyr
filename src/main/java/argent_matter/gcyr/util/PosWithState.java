package argent_matter.gcyr.util;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record PosWithState(BlockPos pos, BlockState state, @Nullable CompoundTag entityTag) {

    public PosWithState(BlockPos pos, BlockState state) {
        this(pos, state, null);
    }

    public static PosWithState readFromTag(CompoundTag tag) {
        BlockPos pos = NbtUtils.readBlockPos(tag.getCompound("pos"));
        BlockState state = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound("state"));
        CompoundTag entityTag = tag.contains("nbt") ? tag.getCompound("nbt") : null;
        return new PosWithState(pos, state, entityTag);
    }

    public CompoundTag writeToTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("pos", NbtUtils.writeBlockPos(this.pos));
        tag.put("state", NbtUtils.writeBlockState(this.state));
        if (this.entityTag != null) {
            tag.put("nbt", this.entityTag);
        }
        return tag;
    }
}
