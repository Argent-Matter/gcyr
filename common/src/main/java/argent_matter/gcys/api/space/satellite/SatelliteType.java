package argent_matter.gcys.api.space.satellite;

import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.api.space.satellite.data.SatelliteData;
import com.gregtechceu.gtceu.api.data.worldgen.generator.DimensionFilter;
import lombok.Getter;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.function.Supplier;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote SatelliteType
 */
public class SatelliteType<T extends Satellite> {
    @Getter
    private final Supplier<T> defaultInstance;
    @Getter
    private final SatelliteFactory<T> factory;

    public SatelliteType(SatelliteFactory<T> factory) {
        this.factory = factory;
        this.defaultInstance = () -> factory.create(this, SatelliteData.DEFAULT, BuiltinDimensionTypes.OVERWORLD);
    }

    public String toLangString() {
        ResourceLocation id = GcysRegistries.SATELLITES.getKey(this);
        return id.getNamespace() + ".satellite." + id.getPath();
    }

    @FunctionalInterface
    public interface SatelliteFactory<T extends Satellite> {
        T create(SatelliteType<?> type, SatelliteData data, ResourceKey<DimensionType> level);
    }

}
