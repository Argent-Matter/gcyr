package argent_matter.gcyr.common.data;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.data.loader.PlanetData;
import argent_matter.gcyr.util.PosWithState;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GCyREntityDataSerializers {
    public static final EntityDataSerializer<Long> LONG = EntityDataSerializer.simple(FriendlyByteBuf::writeVarLong, FriendlyByteBuf::readVarLong);

    public static final EntityDataSerializer<List<BlockPos>> BLOCK_POS_LIST = new EntityDataSerializer<>() {
        @Override
        public void write(FriendlyByteBuf buffer, List<BlockPos> value) {
            buffer.writeCollection(value, EntityDataSerializers.BLOCK_POS::write);
        }

        @Override
        public List<BlockPos> read(FriendlyByteBuf buffer) {
            return buffer.readList(EntityDataSerializers.BLOCK_POS::read);
        }

        @Override
        public List<BlockPos> copy(List<BlockPos> value) {
            return new ArrayList<>(value);
        }
    };

    public static final EntityDataSerializer<PosWithState> POSITIONED_BLOCK_STATE = new EntityDataSerializer<>() {
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

        @Override
        public PosWithState copy(PosWithState value) {
            return new PosWithState(value.pos(), value.state());
        }
    };

    public static final EntityDataSerializer<List<PosWithState>> POSITIONED_BLOCK_STATE_LIST = new EntityDataSerializer<>() {
        @Override
        public void write(FriendlyByteBuf buffer, List<PosWithState> value) {
            buffer.writeCollection(value, POSITIONED_BLOCK_STATE::write);
        }

        @Override
        public List<PosWithState> read(FriendlyByteBuf buffer) {
            return buffer.readList(POSITIONED_BLOCK_STATE::read);
        }

        @Override
        public List<PosWithState> copy(List<PosWithState> value) {
            return new ArrayList<>(value);
        }
    };

    public static final EntityDataSerializer<Optional<Planet>> PLANET = new EntityDataSerializer.ForValueType<>() {
        @Override
        public void write(FriendlyByteBuf buffer, Optional<Planet> value) {
            if (value.isPresent()) {
                buffer.writeBoolean(true);
                buffer.writeResourceLocation(PlanetData.getPlanetId(value.get()));
            } else {
                buffer.writeBoolean(false);
            }
        }

        @Override
        public Optional<Planet> read(FriendlyByteBuf buffer) {
            return buffer.readBoolean() ? Optional.of(PlanetData.getPlanet(buffer.readResourceLocation())) : Optional.empty();
        }
    };


    public static void init() {
        EntityDataSerializers.registerSerializer(LONG);
        EntityDataSerializers.registerSerializer(BLOCK_POS_LIST);
        EntityDataSerializers.registerSerializer(POSITIONED_BLOCK_STATE);
        EntityDataSerializers.registerSerializer(POSITIONED_BLOCK_STATE_LIST);
        EntityDataSerializers.registerSerializer(PLANET);
    }
}
