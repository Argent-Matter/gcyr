package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
import com.gregtechceu.gtceu.api.sound.SoundEntry;


public class GCyRSoundEntries {
    public static final SoundEntry ROCKET = sound("rocket").build();

    public static SoundEntryBuilder sound(String name) {
        return new SoundEntryBuilder(GCyR.id(name));
    }

    public static void init() {

    }
}
