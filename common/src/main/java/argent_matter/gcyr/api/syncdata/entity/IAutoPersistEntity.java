package argent_matter.gcyr.api.syncdata.entity;

import com.google.common.base.Strings;
import com.lowdragmc.lowdraglib.syncdata.accessor.IManagedAccessor;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedKey;
import com.lowdragmc.lowdraglib.syncdata.managed.IRef;
import com.lowdragmc.lowdraglib.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public interface IAutoPersistEntity extends IManagedEntity {
    default void saveManagedPersistentData(CompoundTag tag) {
        IRef[] persistedFields = this.getRootStorage().getPersistedFields();
        IRef[] copy = persistedFields;
        int length = persistedFields.length;

        for(int i = 0; i < length; ++i) {
            IRef persistedField = copy[i];
            ManagedKey fieldKey = persistedField.getKey();

            String key = fieldKey.getPersistentKey();
            if (Strings.isNullOrEmpty(key)) {
                key = fieldKey.getName();
            }

            Tag nbt = fieldKey.readPersistedField(persistedField);
            if (nbt != null) {
                TagUtils.setTagExtended(tag, key, nbt);
            }
        }

        this.saveCustomPersistedData(tag);
    }

    default void loadManagedPersistentData(CompoundTag tag) {
        IRef[] refs = this.getRootStorage().getPersistedFields();
        IManagedAccessor.writePersistedFields(tag, refs);
        this.loadCustomPersistedData(tag);
    }

    default void saveCustomPersistedData(CompoundTag tag) {
    }

    default void loadCustomPersistedData(CompoundTag tag) {
    }
}

