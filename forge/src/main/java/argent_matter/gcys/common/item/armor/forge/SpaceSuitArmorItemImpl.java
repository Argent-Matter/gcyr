package argent_matter.gcys.common.item.armor.forge;

import argent_matter.gcys.common.item.armor.SpaceSuitArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;

public class SpaceSuitArmorItemImpl extends SpaceSuitArmorItem implements IForgeItem {
    public SpaceSuitArmorItemImpl(ArmorItem.Type slot, Properties properties) {
        super(slot, properties);
    }

    public static SpaceSuitArmorItem create(ArmorItem.Type slot, Item.Properties properties) {
        return new SpaceSuitArmorItemImpl(slot, properties);
    }

    public <T> LazyOptional<T> getCapability(@Nonnull final ItemStack itemStack, @Nonnull final Capability<T> cap) {
        if (cap == ForgeCapabilities.FLUID_HANDLER_ITEM) {
            return ForgeCapabilities.FLUID_HANDLER_ITEM.orEmpty(cap, LazyOptional.of(() -> new FluidHandlerItemStack(itemStack, Math.toIntExact(SpaceSuitArmorItem.CAPACITY))));
        }
        return LazyOptional.empty();
    }
}