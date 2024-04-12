package argent_matter.gcyr.mixin;

import argent_matter.gcyr.GCyR;
import com.gregtechceu.gtceu.api.registry.GTRegistry;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraftforge.fml.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = GTRegistry.class, remap = false)
public class GTRegistryMixin {

    @ModifyReturnValue(method = "checkActiveModContainerIsGregtech", at = @At("RETURN"))
    private static boolean gcyr$modifyIfCheck(boolean original, @Local ModContainer container) {
        return original || container.getModId().equals(GCyR.MOD_ID);
    }
}
