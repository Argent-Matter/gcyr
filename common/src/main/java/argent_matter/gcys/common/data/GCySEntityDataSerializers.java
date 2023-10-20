package argent_matter.gcys.common.data;

import argent_matter.gcys.util.PosWithState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class GCySEntityDataSerializers {
    public static final EntityDataSerializer<Long> LONG = EntityDataSerializer.simple(FriendlyByteBuf::writeVarLong, FriendlyByteBuf::readVarLong);

    public static final EntityDataSerializer<List<BlockPos>> BLOCK_POS_LIST = new EntityDataSerializer.ForValueType<>() {
        @Override
        public void write(FriendlyByteBuf buffer, List< BlockPos > value) {
            buffer.writeCollection(value, EntityDataSerializers.BLOCK_POS::write);
        }

        @Override
        public List<BlockPos> read(FriendlyByteBuf buffer) {
            return buffer.readList(EntityDataSerializers.BLOCK_POS::read);
        }
    };

    public static final EntityDataSerializer<PosWithState> POSITIONED_BLOCK_STATE = new EntityDataSerializer.ForValueType<>() {
        @Override
        public void write(FriendlyByteBuf friendlyByteBuf, PosWithState posState) {
            EntityDataSerializers.BLOCK_POS.write(friendlyByteBuf, posState.pos());
            EntityDataSerializers.BLOCK_STATE.write(friendlyByteBuf, posState.state());
        }

        @Override
        public PosWithState read(FriendlyByteBuf friendlyByteBuf) {
            BlockPos pos = EntityDataSerializers.BLOCK_POS.read(friendlyByteBuf);
            BlockState state = EntityDataSerializers.BLOCK_STATE.read(friendlyByteBuf);
            return new PosWithState(pos, state);
        }
    };

    public static final EntityDataSerializer<List<PosWithState>> POSITIONED_BLOCK_STATE_LIST = new EntityDataSerializer.ForValueType<>() {
        @Override
        public void write(FriendlyByteBuf buffer, List<PosWithState> value) {
            buffer.writeCollection(value, POSITIONED_BLOCK_STATE::write);
        }

        @Override
        public List<PosWithState> read(FriendlyByteBuf buffer) {
            return buffer.readList(POSITIONED_BLOCK_STATE::read);
        }
    };


    public static void init() {
        EntityDataSerializers.registerSerializer(LONG);
        EntityDataSerializers.registerSerializer(BLOCK_POS_LIST);
        EntityDataSerializers.registerSerializer(POSITIONED_BLOCK_STATE);
        EntityDataSerializers.registerSerializer(POSITIONED_BLOCK_STATE_LIST);
    }
}
