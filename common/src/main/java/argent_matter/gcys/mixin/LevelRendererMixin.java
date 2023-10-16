package argent_matter.gcys.mixin;

import argent_matter.gcys.GregicalitySpaceClient;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Shadow
    private ClientLevel level;

    @Shadow
    protected abstract boolean doesMobEffectBlockSky(Camera camera);

    @Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doesMobEffectBlockSky(Lnet/minecraft/client/Camera;)Z"))
    private boolean gcys$removeSun(LevelRenderer instance, Camera camera) {
        if (GregicalitySpaceClient.isDysonSphereActive) {
            return true;
        }
        return this.doesMobEffectBlockSky(camera);
    }
}
