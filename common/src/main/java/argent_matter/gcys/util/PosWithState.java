package argent_matter.gcys.util;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record PosWithState(BlockPos pos, BlockState state) {

    public static PosWithState readFromTag(CompoundTag tag) {
        BlockPos pos = NbtUtils.readBlockPos(tag.getCompound("pos"));
        BlockState state = NbtUtils.readBlockState(tag.getCompound("state"));
        return new PosWithState(pos, state);
    }

    public CompoundTag writeToTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("pos", NbtUtils.writeBlockPos(this.pos));
        tag.put("state", NbtUtils.writeBlockState(this.state));
        return tag;
    }
}
