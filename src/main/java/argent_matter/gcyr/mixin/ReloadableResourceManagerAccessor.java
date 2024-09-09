package argent_matter.gcyr.mixin;

import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ReloadableResourceManager.class)
public interface ReloadableResourceManagerAccessor {
    @Accessor
    List<PreparableReloadListener> getListeners();
}
