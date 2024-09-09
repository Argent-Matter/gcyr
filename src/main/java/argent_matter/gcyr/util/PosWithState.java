package argent_matter.gcyr.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record PosWithState(BlockPos pos, BlockState state, Optional<CompoundTag> entityTag) {

    public PosWithState(BlockPos pos, BlockState state) {
        this(pos, state, Optional.empty());
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, PosWithState> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, PosWithState::pos,
            ByteBufCodecs.fromCodec(BlockState.CODEC), PosWithState::state,
            ByteBufCodecs.OPTIONAL_COMPOUND_TAG, PosWithState::entityTag,
            PosWithState::new);

    public static final Codec<PosWithState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("pos").forGetter(PosWithState::pos),
            BlockState.CODEC.fieldOf("state").forGetter(PosWithState::state),
            CompoundTag.CODEC.optionalFieldOf("tag").forGetter(PosWithState::entityTag)
    ).apply(instance, PosWithState::new));

    public static PosWithState readFromTag(CompoundTag tag) {
        BlockPos pos = NbtUtils.readBlockPos(tag, "pos").orElse(BlockPos.ZERO);
        BlockState state = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound("state"));
        CompoundTag entityTag = tag.contains("nbt") ? tag.getCompound("nbt") : null;
        return new PosWithState(pos, state, Optional.ofNullable(entityTag));
    }

    public CompoundTag writeToTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("pos", NbtUtils.writeBlockPos(this.pos));
        tag.put("state", NbtUtils.writeBlockState(this.state));
        this.entityTag.ifPresent(compoundTag -> tag.put("nbt", compoundTag));
        return tag;
    }
}
