package argent_matter.gcyr.util;

import com.gregtechceu.gtceu.api.codec.GTCodecUtils;

import net.minecraft.Util;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public record Vec2i(int x, int y) implements Comparable<Vec2i> {

    // spotless:off
    private static final Codec<Vec2i> RECORD_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("x").forGetter(Vec2i::x),
            Codec.INT.fieldOf("y").forGetter(Vec2i::y)
    ).apply(instance, Vec2i::new));
    private static final Codec<Vec2i> ARRAY_CODEC = Codec.INT.listOf().comapFlatMap(ls -> {
        return Util.fixedSize(ls, 2).map(list -> new Vec2i(list.get(0), list.get(1)));
    }, (vec) -> List.of(vec.x, vec.y));
    public static final Codec<Vec2i> CODEC = Codec.either(ARRAY_CODEC, RECORD_CODEC).xmap(GTCodecUtils::unboxEither, Either::left);
    // spotless:on

    public static final Vec2i ZERO = new Vec2i(0, 0);
    public static final Vec2i MAX_NEGATIVE = new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE);

    public int distanceToSqr(Vec2i other) {
        int f = other.x - this.x;
        int g = other.y - this.y;
        return f * f + g * g;
    }

    @Override
    public int compareTo(@NotNull Vec2i other) {
        return this.x == other.x ? this.y - other.y : this.x - other.x;
    }
}
