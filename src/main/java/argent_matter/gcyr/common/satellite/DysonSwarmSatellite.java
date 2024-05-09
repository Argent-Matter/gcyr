package argent_matter.gcyr.common.satellite;

import argent_matter.gcyr.api.space.satellite.Satellite;
import argent_matter.gcyr.api.space.satellite.data.SatelliteData;
import argent_matter.gcyr.common.data.GCyRSatellites;
import argent_matter.gcyr.common.machine.multiblock.electric.DysonSystemControllerMachine;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote DysonSwarmSatellite
 */
public class DysonSwarmSatellite extends Satellite {
    public static final Codec<DysonSwarmSatellite> CODEC = RecordCodecBuilder.create(instance -> Satellite.baseCodec(instance).apply(instance, DysonSwarmSatellite::new));

    @Setter
    private BlockPos swarmController;

    public DysonSwarmSatellite(SatelliteData data, ResourceKey<Level> level) {
        super(GCyRSatellites.DYSON_SWARM, data, level);
    }

    @Override
    public void tickSatellite(Level level) {
        if (isNonWorking()) return;
        if (swarmController == null) return;
        if (!level.isLoaded(swarmController)) return;
        BlockEntity controller = level.getBlockEntity(swarmController);
        if (controller instanceof IMachineBlockEntity machineBE) {
            if (machineBE.getMetaMachine() instanceof DysonSystemControllerMachine swarmMachine) {
                swarmMachine.getEnergyContainer().addEnergy(10000L);
            }
        }
    }

    @Override
    public boolean runSatelliteFunction(Level level) {
        return false;
    }

    @Nullable
    @Override
    public Tag serializeExtraData() {
        CompoundTag tag = new CompoundTag();
        tag.put("controllerPos", NbtUtils.writeBlockPos(this.swarmController));
        return tag;
    }

    @Override
    public void deserializeExtraData(Tag tag, Level level) {
        if (tag instanceof CompoundTag compoundTag) {
            this.swarmController = NbtUtils.readBlockPos(compoundTag.getCompound("controllerPos"));
        }
    }
}
