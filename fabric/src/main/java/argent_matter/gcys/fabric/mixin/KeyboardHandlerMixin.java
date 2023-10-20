package argent_matter.gcys.fabric.mixin;

import argent_matter.gcys.GCyS;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KeyboardHandler.class, priority = 999)
public class KeyboardHandlerMixin {
    @Shadow @Final
    private Minecraft minecraft;

    @Inject(method = "keyPress", at = @At("RETURN"))
    public void onRawKey(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo info) {
        if (windowPointer == this.minecraft.getWindow().getWindow()) {
            GCyS.onKeyPressed(key, action, modifiers);
        }
    }
}
