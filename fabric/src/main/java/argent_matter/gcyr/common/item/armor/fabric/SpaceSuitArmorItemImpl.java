package argent_matter.gcyr.common.item.armor.fabric;

import argent_matter.gcyr.common.item.armor.SpaceSuitArmorItem;
import com.gregtechceu.gtceu.api.misc.fabric.FluidHandlerItemStack;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class SpaceSuitArmorItemImpl extends SpaceSuitArmorItem implements FabricItem {
    public SpaceSuitArmorItemImpl(ArmorItem.Type type, Properties properties) {
        super(type, properties);
        FluidStorage.ITEM.registerForItems((itemStack, context) -> new FluidHandlerItemStack(context, SpaceSuitArmorItem.CAPACITY), this);
    }

    public static SpaceSuitArmorItem create(ArmorItem.Type slot, Item.Properties properties) {
        return new SpaceSuitArmorItemImpl(slot, properties);
    }
}
