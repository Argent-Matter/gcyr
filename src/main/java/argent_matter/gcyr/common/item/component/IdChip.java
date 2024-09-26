package argent_matter.gcyr.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record IdChip(int currentStation, @NotNull Optional<ResourceLocation> currentPlanet, @NotNull Optional<BlockPos> currentPos) {

    public static final IdChip EMPTY = new IdChip(Integer.MIN_VALUE, Optional.empty(), Optional.empty());

    public static final Codec<IdChip> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("current_station", -1).forGetter(IdChip::currentStation),
            ResourceLocation.CODEC.optionalFieldOf("current_planet").forGetter(IdChip::currentPlanet),
            BlockPos.CODEC.optionalFieldOf("current_pos").forGetter(IdChip::currentPos)
    ).apply(instance, IdChip::new));

    public static final StreamCodec<ByteBuf, IdChip> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, IdChip::currentStation,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), IdChip::currentPlanet,
            ByteBufCodecs.optional(BlockPos.STREAM_CODEC), IdChip::currentPos,
            IdChip::new);

    public boolean hasStation() {
        return currentStation != Integer.MIN_VALUE;
    }

    public IdChip updateStation(int currentStation) {
        return new IdChip(currentStation, currentPlanet, currentPos);
    }

    public IdChip updatePlanet(ResourceLocation currentPlanet) {
        return new IdChip(currentStation, Optional.ofNullable(currentPlanet), currentPos);
    }

    public IdChip updatePos(BlockPos currentPos) {
        return new IdChip(currentStation, currentPlanet, Optional.ofNullable(currentPos));
    }
}
