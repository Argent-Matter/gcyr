package argent_matter.gcyr.mixin;

import argent_matter.gcyr.GCyRClient;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Shadow @Final private ClientLevel.ClientLevelData clientLevelData;

    @Inject(method = "setDayTime", at = @At("HEAD"), cancellable = true)
    public void gcyr$overrideDayTime(long time, CallbackInfo ci) {
        if (GCyRClient.isDysonSphereActive) {
            long dayTime = this.clientLevelData.getDayTime();
            this.clientLevelData.setDayTime(dayTime + (24000L - (dayTime % 24000L) + 18000L) % 24000L);
            ci.cancel();
        }
    }
}
*/