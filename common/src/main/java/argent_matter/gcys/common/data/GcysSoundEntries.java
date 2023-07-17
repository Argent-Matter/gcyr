package argent_matter.gcys.common.data;

import argent_matter.gcys.GregicalitySpace;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
import com.gregtechceu.gtceu.api.sound.SoundEntry;

import static argent_matter.gcys.api.registries.GcysRegistries.REGISTRATE;


public class GcysSoundEntries {
    public static final SoundEntry ROCKET = sound("rocket").build();

    public static SoundEntryBuilder sound(String name) {
        return new SoundEntryBuilder(GregicalitySpace.id(name));
    }

    public static void init() {

    }
}
