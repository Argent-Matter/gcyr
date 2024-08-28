package argent_matter.gcyr.common.item.armor;

import argent_matter.gcyr.common.data.GCYRDataComponents;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import com.lowdragmc.lowdraglib.side.fluid.FluidTransferHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.List;

public class SpaceSuitArmorItem extends ArmorItem {
    public static final int CAPACITY = 16 * FluidHelper.getBucket();

    public SpaceSuitArmorItem(ArmorItem.Type type, Properties properties) {
        super(GCYRArmorMaterials.SPACE, type, properties);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - (float)oxygenAmount(stack) * 13.0F / (float)oxygenMax(stack));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xff84ebf5;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("tooltip.gcyr.spacesuit"));
        if (stack.is(ItemTags.CHEST_ARMOR)) {
            IFluidHandler transfer = FluidTransferHelper.getFluidTransfer(new CustomItemStackHandler(stack), 0);
            if (transfer != null) {
                tooltip.add(Component.translatable("tooltip.gcyr.spacesuit.stored", transfer.getFluidInTank(0).getAmount(), transfer.getTankCapacity(0)));
            }
        }
    }

    public static boolean hasFullSet(LivingEntity entity) {
        int slotCount = 0;
        int armorCount = 0;
        for (ItemStack stack : entity.getArmorSlots()) {
            slotCount++;
            if (stack.getItem() instanceof SpaceSuitArmorItem || stack.has(GCYRDataComponents.SPACE_SUIT)) {
                armorCount++;
            }
        }
        return slotCount > 0 && armorCount == slotCount;
    }

    /**
     * Checks if the entity is wearing a space suit and if that space suit has oxygen.
     *
     * @param entity The entity wearing the space suit
     * @return Whether the entity has oxygen or not
     */
    public static boolean hasOxygenatedSpaceSuit(LivingEntity entity) {
        return oxygenAmount(entity) > 0;
    }

    public static long oxygenAmount(LivingEntity entity) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        return oxygenAmount(chest);
    }

    public static long oxygenAmount(ItemStack stack) {
        if (stack.getItem() instanceof SpaceSuitArmorItem || stack.has(GCYRDataComponents.SPACE_SUIT)) {
            var storage = new CustomItemStackHandler(stack);
            var fluid = FluidTransferHelper.getFluidTransfer(storage, 0);
            if (fluid != null) {
                return fluid.getFluidInTank(0).getAmount();
            }
        }
        return 0;
    }

    public static long oxygenMax(LivingEntity entity) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        return oxygenMax(chest);
    }

    public static long oxygenMax(ItemStack stack) {
        if (stack.getItem() instanceof SpaceSuitArmorItem || stack.has(GCYRDataComponents.SPACE_SUIT)) {
            var fluid = FluidTransferHelper.getFluidTransfer(new CustomItemStackHandler(stack), 0);
            if (fluid != null) {
                return fluid.getTankCapacity(0);
            }
        }
        return 0;
    }

    public static void consumeSpaceSuitOxygen(LivingEntity entity, int amount) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof SpaceSuitArmorItem || chest.has(GCYRDataComponents.SPACE_SUIT)) {
            var storage = new CustomItemStackHandler(chest);
            var fluid = FluidTransferHelper.getFluidTransfer(storage, 0);
            fluid.drain(new FluidStack(fluid.getFluidInTank(0).getFluid(), amount), IFluidHandler.FluidAction.EXECUTE);
        }

    }

    public static UniformInt getTemperatureThreshold() {
        return UniformInt.of(60, 363);
    }

}
