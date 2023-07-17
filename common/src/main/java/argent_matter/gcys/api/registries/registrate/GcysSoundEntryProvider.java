package argent_matter.gcys.api.registries.registrate;

import argent_matter.gcys.GregicalitySpace;
import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.sound.SoundEntry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

import java.io.IOException;
import java.nio.file.Path;

public class GcysSoundEntryProvider implements DataProvider {
    private final DataGenerator generator;

    public GcysSoundEntryProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(CachedOutput cache) {
        generate(generator.getOutputFolder(), cache);
    }

    @Override
    public String getName() {
        return "GcyS's Custom Sounds";
    }

    public void generate(Path path, CachedOutput cache) {
        path = path.resolve("assets/" + GregicalitySpace.MOD_ID);

        try {
            JsonObject json = new JsonObject();
            for (SoundEntry sound : GTRegistries.SOUNDS) {
                if (sound.getId().getNamespace().equals(GregicalitySpace.MOD_ID)) sound.write(json);
            }
            DataProvider.saveStable(cache, json, path.resolve("sounds.json"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
