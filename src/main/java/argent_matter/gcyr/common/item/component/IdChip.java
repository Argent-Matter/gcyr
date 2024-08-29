package argent_matter.gcyr.common.item.component;

import argent_matter.gcyr.api.space.planet.Planet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.latvian.mods.kubejs.util.ID;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record IdChip(int currentStation, ResourceLocation currentPlanet) {

    public static final IdChip EMPTY = new IdChip(Integer.MIN_VALUE, null);

    public static final Codec<IdChip> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("current_station", -1).forGetter(IdChip::currentStation),
            ResourceLocation.CODEC.fieldOf("current_planet").forGetter(IdChip::currentPlanet)
    ).apply(instance, IdChip::new));

    public static final StreamCodec<ByteBuf, IdChip> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, IdChip::currentStation,
            ResourceLocation.STREAM_CODEC, IdChip::currentPlanet,
            IdChip::new);

    public boolean hasStation() {
        return currentStation != Integer.MIN_VALUE;
    }

    public IdChip updateStation(int stationId) {
        return new IdChip(stationId, this.currentPlanet);
    }
}
