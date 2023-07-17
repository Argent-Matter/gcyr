package argent_matter.gcys.forge;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.common.item.armor.forge.SpaceSuitArmorItemImpl;
import com.gregtechceu.gtceu.GTCEu;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = GregicalitySpace.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEventListener {

    @SubscribeEvent
    public static void registerItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) {

        if (event.getObject().getItem() instanceof SpaceSuitArmorItemImpl spaceSuitItem) {
            final ItemStack itemStack = event.getObject();
            event.addCapability(GregicalitySpace.id("fluid"), new ICapabilityProvider() {
                @Override
                public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
                    return spaceSuitItem.getCapability(itemStack, capability);
                }
            });
        }
    }
}
