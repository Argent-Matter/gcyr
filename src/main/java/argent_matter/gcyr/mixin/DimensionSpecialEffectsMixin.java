package argent_matter.gcyr.mixin;

import argent_matter.gcyr.client.dimension.ClientModSkies;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionSpecialEffects.class)
public abstract class DimensionSpecialEffectsMixin {

    @Inject(method = "forType", at = @At("HEAD"), cancellable = true)
    private static void gcyr$forType(DimensionType type, CallbackInfoReturnable<DimensionSpecialEffects> cir) {
        if (ClientModSkies.DIMENSION_SPECIAL_EFFECTS.containsKey(type.effectsLocation())) {
            cir.setReturnValue(ClientModSkies.DIMENSION_SPECIAL_EFFECTS.get(type.effectsLocation()));
        }
    }
}
