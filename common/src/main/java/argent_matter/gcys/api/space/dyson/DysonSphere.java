package argent_matter.gcys.api.space.dyson;

import argent_matter.gcys.api.capability.IDysonSystem;
import com.gregtechceu.gtceu.api.GTValues;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;

public class DysonSphere {

    private final int MINIMUM_MAINTENANCE_TIME = 3456000; // 48 real-life hours = 3456000 ticks
    private final float BASE_COLLAPSE_CHANCE = 0.00005f;

    private final IDysonSystem system;

    @Getter
    private BlockPos controllerPos;
    @Getter @Setter
    private int timeActive = 0, timeNeededMaintenance = 0;
    @Getter
    private boolean needsMaintenance;
    @Getter @Setter
    private boolean collapsed;

    public DysonSphere(BlockPos controllerPos, IDysonSystem system) {
        this.controllerPos = controllerPos;
        this.system = system;
    }

    public void tick(ServerLevel level) {
        if (level.getGameTime() % 20 == 0) {
            if (this.isCollapsed()) return;
            if (this.isNeedsMaintenance()) {
                if (GTValues.RNG.nextFloat() <= BASE_COLLAPSE_CHANCE * timeNeededMaintenance) {
                    this.setCollapsed();
                }

                timeNeededMaintenance++;
                this.system.setChanged();
                return;
            }

            if (calculateTime(20)) {
                if (GTValues.RNG.nextFloat() - 0.75f >= 0) {
                    this.needsMaintenance();
                }
            }
        }
    }

    public float getCollapseChance() {
        return BASE_COLLAPSE_CHANCE * timeNeededMaintenance;
    }

    public void needsMaintenance() {
        this.needsMaintenance = true;
        this.timeNeededMaintenance++;
        this.system.setChanged();
    }

    public void fixMaintenance() {
        this.needsMaintenance = false;
        this.timeNeededMaintenance = 0;
        this.system.setChanged();
    }

    public void setCollapsed() {
        this.setCollapsed(true);
        this.system.disableDysonSphere(this.controllerPos);
    }

    private boolean calculateTime(int timeSinceLastTick) {
        setTimeActive(timeSinceLastTick + getTimeActive());
        this.system.setChanged();
        var value = getTimeActive() - MINIMUM_MAINTENANCE_TIME;
        if (value > 0) {
            setTimeActive(value);
            return true;
        }
        return false;
    }

    public void setControllerPos(BlockPos controllerPos) {
        this.controllerPos = controllerPos;
        this.system.setChanged();
    }

    public void save(CompoundTag tag) {
        if (controllerPos != null) {
            tag.put("controllerPos", NbtUtils.writeBlockPos(controllerPos));
        }
        tag.putBoolean("needsMaintenance", this.isNeedsMaintenance());
        tag.putBoolean("collapsed", this.isCollapsed());
        tag.putInt("timeActive", this.timeActive);
        tag.putInt("timeNeededMaintenance", this.timeNeededMaintenance);
    }

    public static DysonSphere load(CompoundTag tag, IDysonSystem system) {
        BlockPos controllerPos = tag.contains("controllerPos", Tag.TAG_COMPOUND) ? NbtUtils.readBlockPos(tag.getCompound("controllerPos")) : null;
        boolean needsMaintenance = tag.getBoolean("needsMaintenance");
        boolean collapsed = tag.getBoolean("collapsed");
        int timeActive = tag.getInt("timeActive");
        int timeNeededMaintenance = tag.getInt("timeNeededMaintenance");

        DysonSphere sphere = new DysonSphere(controllerPos, system);
        sphere.needsMaintenance = needsMaintenance;
        sphere.setCollapsed(collapsed);
        sphere.setTimeActive(timeActive);
        sphere.setTimeNeededMaintenance(timeNeededMaintenance);

        return sphere;
    }
}
