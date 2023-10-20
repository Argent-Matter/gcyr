package argent_matter.gcys.common.data;

import argent_matter.gcys.GCyS;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
import com.gregtechceu.gtceu.api.sound.SoundEntry;


public class GCySSoundEntries {
    public static final SoundEntry ROCKET = sound("rocket").build();

    public static SoundEntryBuilder sound(String name) {
        return new SoundEntryBuilder(GCyS.id(name));
    }

    public static void init() {

    }
}
