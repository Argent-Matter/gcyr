package argent_matter.gcyr.fabric.mixin;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.fabric.RegistryObject;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.EmptyByteBuf;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MenuEntry.class, remap = false)
public abstract class MenuEntryMixin<T extends AbstractContainerMenu> extends RegistryEntry<MenuType<T>> {

    public MenuEntryMixin(AbstractRegistrate<?> owner, RegistryObject<MenuType<T>> delegate) {
        super(owner, delegate);
    }

    /**
     * @author Screret
     * @reason Fabric requires this.
     */
    @Overwrite
    public T create(int windowId, Inventory playerInv) {
        return ((ExtendedScreenHandlerType<T>)get()).create(windowId, playerInv, new FriendlyByteBuf(new EmptyByteBuf(ByteBufAllocator.DEFAULT)));
    }
}
