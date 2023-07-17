package argent_matter.gcys.common.satellite;

import argent_matter.gcys.api.space.satellite.Satellite;
import argent_matter.gcys.api.space.satellite.SatelliteType;
import argent_matter.gcys.api.space.satellite.data.SatelliteData;
import argent_matter.gcys.config.GcysConfig;
import com.gregtechceu.gtceu.api.data.damagesource.DamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

public class LaserSatellite extends Satellite {
    private int currentMinedY;
    private boolean isMining = false;

    public LaserSatellite(SatelliteType<?> type, SatelliteData data, ResourceKey<DimensionType> level) {
        super(type, data, level);
    }

    @Override
    public void tickSatellite(Level level) {
        if (isNonWorking()) return;
        if (isMining) {
            if (level.getGameTime() % GcysConfig.INSTANCE.satellites.laserSatelliteMiningTickStep == 0) {
                float x = this.data.locationInWorld().x;
                float z = this.data.locationInWorld().y;
                for (float i = x - 1; i < x + 1; ++x) {
                    for (float j = z - 1; j < z + 1; ++z) {
                        level.setBlock(new BlockPos(i, currentMinedY, j), Blocks.AIR.defaultBlockState(), 3);
                    }
                }

                var entities = level.getEntities(EntityTypeTest.forClass(LivingEntity.class), new AABB(x - 1, currentMinedY - 1, z - 1, x + 1, currentMinedY + 1, z + 1), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
                entities.forEach(entity -> entity.hurt(DamageSources.getRadioactiveDamage(), GcysConfig.INSTANCE.satellites.laserSatelliteDamagePerTickStep));
            }
        }

    }

    @Override
    public boolean runSatelliteFunction(Level level) {
        currentMinedY = level.getHeight(Heightmap.Types.WORLD_SURFACE, Mth.floor(this.data.locationInWorld().x), Mth.floor(this.data.locationInWorld().y));
        isMining = true;
        return true;
    }

    @Override
    public CompoundTag serializeExtraData() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("currentY", currentMinedY);
        tag.putBoolean("mining", isMining);
        return tag;
    }

    @Override
    public void deserializeExtraData(Tag tag, Level level) {
        CompoundTag compound = (CompoundTag) tag;
        this.isMining = compound.getBoolean("mining");
        this.currentMinedY = compound.getInt("currentY");
    }
}
