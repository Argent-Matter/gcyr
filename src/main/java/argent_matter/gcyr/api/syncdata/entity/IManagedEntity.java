package argent_matter.gcyr.api.syncdata.entity;

import com.lowdragmc.lowdraglib.syncdata.IManagedStorage;
import com.lowdragmc.lowdraglib.syncdata.managed.IRef;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public interface IManagedEntity {
    default EntityType<?> getEntityType() {
        return this.getSelf().getType();
    }

    default Vec3 getCurrentPos() {
        return this.getSelf().position();
    }

    default Entity getSelf() {
        return (Entity)this;
    }

    default IRef[] getNonLazyFields() {
        return this.getRootStorage().getNonLazyFields();
    }

    IManagedStorage getRootStorage();
}
