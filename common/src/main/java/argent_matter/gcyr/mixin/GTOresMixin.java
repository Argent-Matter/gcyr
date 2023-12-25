package argent_matter.gcyr.mixin;

import argent_matter.gcyr.common.worldgen.GCyROres;
import com.gregtechceu.gtceu.common.data.GTOres;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GTOres.class, remap = false) // TODO remove once a way to add ore gen to an addon without JSON is made
public class GTOresMixin {

    @Inject(method = "init", at = @At("HEAD"))
    private static void gcyr$loadAddonOres(CallbackInfo ci) {
        GCyROres.init();
    }
}
