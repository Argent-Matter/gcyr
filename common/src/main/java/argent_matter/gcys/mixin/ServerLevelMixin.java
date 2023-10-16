package argent_matter.gcys.mixin;

import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.IDysonSystem;
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
    public void gcys$overrideDayTime(long time, CallbackInfo ci) {
        IDysonSystem dysonSystem = GcysCapabilityHelper.getDysonSystem((ServerLevel) (Object) this);
        if (dysonSystem != null && dysonSystem.isDysonSphereActive()) {
            this.serverLevelData.setDayTime(18000L);
            ci.cancel();
        }
    }
}
