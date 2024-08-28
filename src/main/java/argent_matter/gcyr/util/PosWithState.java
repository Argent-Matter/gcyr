package argent_matter.gcyr.util;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record PosWithState(BlockPos pos, BlockState state) {

    public static final StreamCodec<RegistryFriendlyByteBuf, PosWithState> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, PosWithState::pos,
            ByteBufCodecs.fromCodec(BlockState.CODEC), PosWithState::state,
            PosWithState::new);

    public static PosWithState readFromTag(CompoundTag tag) {
        BlockPos pos = NbtUtils.readBlockPos(tag, "pos").orElse(BlockPos.ZERO);
        BlockState state = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound("state"));
        return new PosWithState(pos, state);
    }

    public CompoundTag writeToTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("pos", NbtUtils.writeBlockPos(this.pos));
        tag.put("state", NbtUtils.writeBlockState(this.state));
        return tag;
    }
}
