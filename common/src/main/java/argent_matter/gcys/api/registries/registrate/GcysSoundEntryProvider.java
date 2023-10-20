package argent_matter.gcys.api.registries.registrate;

import argent_matter.gcys.GCyS;
import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.sound.SoundEntry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class GcysSoundEntryProvider implements DataProvider {
    private final PackOutput output;

    public GcysSoundEntryProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return generate(output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(GCyS.MOD_ID), cache);
    }

    @Override
    public String getName() {
        return "GCyS's Custom Sounds";
    }

    public CompletableFuture<?> generate(Path path, CachedOutput cache) {
        JsonObject json = new JsonObject();
        try {
            for (SoundEntry sound : GTRegistries.SOUNDS) {
                if (sound.getId().getNamespace().equals(GCyS.MOD_ID)) sound.write(json);
            }
        } catch (Exception ignored) {
        }
        return DataProvider.saveStable(cache, json, path.resolve("sounds.json"));
    }

}