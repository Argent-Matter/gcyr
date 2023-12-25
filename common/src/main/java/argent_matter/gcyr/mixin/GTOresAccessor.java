package argent_matter.gcyr.mixin;

import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.common.data.GTOres;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = GTOres.class, remap = false) // TODO remove once a way to add ore gen to an addon without JSON is made
public interface GTOresAccessor {

    @Accessor
    static Map<ResourceLocation, GTOreDefinition> getToReRegister() {
        throw new AssertionError();
    }
}
