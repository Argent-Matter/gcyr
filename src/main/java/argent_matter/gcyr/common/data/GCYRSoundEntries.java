package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
import com.gregtechceu.gtceu.api.sound.SoundEntry;

public class GCYRSoundEntries {
    public static final SoundEntry ROCKET = sound("rocket").build();

    public static SoundEntryBuilder sound(String name) {
        return new SoundEntryBuilder(GCYR.id(name));
    }

    public static void init() {

    }
}
