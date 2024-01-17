package argent_matter.gcyr.mixin;

import argent_matter.gcyr.api.capability.GCyRCapabilityHelper;
import argent_matter.gcyr.api.capability.IDysonSystem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Shadow @Final private ServerLevelData serverLevelData;

    @Inject(method = "setDayTime", at = @At("HEAD"), cancellable = true)
    public void gcyr$overrideDayTime(long time, CallbackInfo ci) {
        IDysonSystem dysonSystem = GCyRCapabilityHelper.getDysonSystem((ServerLevel) (Object) this);
        if (dysonSystem != null && dysonSystem.isDysonSphereActive() && !dysonSystem.activeDysonSphere().isCollapsed()) {
            long dayTime = this.serverLevelData.getDayTime();
            this.serverLevelData.setDayTime(dayTime + (24000L - (dayTime % 24000L) + 18000L) % 24000L);
            ci.cancel();
        }
    }
}
