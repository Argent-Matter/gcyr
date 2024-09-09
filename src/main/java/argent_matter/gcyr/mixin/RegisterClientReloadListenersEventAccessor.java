package argent_matter.gcyr.mixin;

import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = RegisterClientReloadListenersEvent.class, remap = false)
public interface RegisterClientReloadListenersEventAccessor {

    @Accessor
    ReloadableResourceManager getResourceManager();
}
