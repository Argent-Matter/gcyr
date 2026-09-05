package argent_matter.gcyr.api.data;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import lombok.Getter;
import net.minecraft.resources.DelegatingOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

public class IdContextOps<T> extends DelegatingOps<T> {

    @Getter
    private final ResourceLocation id;

    protected IdContextOps(DynamicOps<T> delegate, ResourceLocation id) {
        super(delegate);
        this.id = id;
    }

    public static <T> IdContextOps<T> create(DynamicOps<T> delegate, ResourceLocation id) {
        return new IdContextOps<>(delegate, id);
    }

    public static MapCodec<ResourceLocation> retrieveId() {
        return ExtraCodecs.retrieveContext(ops -> {
            if (!(ops instanceof IdContextOps<?> idContextOps)) {
                return DataResult.error(() -> "Not an ID context ops");
            }

            return DataResult.success(idContextOps.id);
        });
    }
}
