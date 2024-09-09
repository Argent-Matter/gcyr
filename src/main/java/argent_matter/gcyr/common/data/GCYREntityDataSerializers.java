package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.data.loader.PlanetData;
import argent_matter.gcyr.util.PosWithState;
import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GCYREntityDataSerializers {

    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, GCYR.MOD_ID);

    public static final EntityDataSerializer<Long> LONG = EntityDataSerializer.forValueType(ByteBufCodecs.VAR_LONG);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<List<BlockPos>>> BLOCK_POS_LIST = ENTITY_DATA_SERIALIZERS.register("block_pos_list", () -> new EntityDataSerializer<>() {
        public static final StreamCodec<ByteBuf, List<BlockPos>> STREAM_CODEC = BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list());

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, List<BlockPos>> codec() {
            return STREAM_CODEC;
        }

        @Override
        public List<BlockPos> copy(List<BlockPos> value) {
            return new ArrayList<>(value);
        }
    });

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<PosWithState>> POSITIONED_BLOCK_STATE = ENTITY_DATA_SERIALIZERS.register("positioned_block_state", () -> new EntityDataSerializer<>() {

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, PosWithState> codec() {
            return PosWithState.STREAM_CODEC;
        }

        @Override
        public PosWithState copy(PosWithState value) {
            return new PosWithState(value.pos(), value.state());
        }
    });

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<List<PosWithState>>> POSITIONED_BLOCK_STATE_LIST = ENTITY_DATA_SERIALIZERS.register("positioned_block_state_list", () -> new EntityDataSerializer<>() {
        public static final StreamCodec<RegistryFriendlyByteBuf, List<PosWithState>> STREAM_CODEC = PosWithState.STREAM_CODEC.apply(ByteBufCodecs.list());

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, List<PosWithState>> codec() {
            return STREAM_CODEC;
        }

        @Override
        public List<PosWithState> copy(List<PosWithState> value) {
            return new ArrayList<>(value);
        }
    });

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Optional<Planet>>> PLANET = ENTITY_DATA_SERIALIZERS.register("planet", () -> new EntityDataSerializer.ForValueType<>() {
        public static final StreamCodec<ByteBuf, Optional<Planet>> STREAM_CODEC = StreamCodec.of((buffer, value) -> {
            if (value.isPresent()) {
                buffer.writeBoolean(true);
                ResourceLocation.STREAM_CODEC.encode(buffer, PlanetData.getPlanetId(value.get()));
            } else {
                buffer.writeBoolean(false);
            }

        }, buffer -> buffer.readBoolean() ? Optional.of(PlanetData.getPlanet(ResourceLocation.STREAM_CODEC.decode(buffer))) : Optional.<Planet>empty());

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, Optional<Planet>> codec() {
            return STREAM_CODEC;
        }
    });


    public static void register(IEventBus modBus) {
        ENTITY_DATA_SERIALIZERS.register(modBus);
    }
}
