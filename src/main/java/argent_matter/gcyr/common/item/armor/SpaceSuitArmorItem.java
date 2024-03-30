package argent_matter.gcyr.common.item.armor;

import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import com.lowdragmc.lowdraglib.side.fluid.FluidTransferHelper;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;

public class SpaceSuitArmorItem extends ArmorItem {
    public static final long CAPACITY = (int) (16 * FluidHelper.getBucket());

    public SpaceSuitArmorItem(ArmorItem.Type type, Properties properties) {
        super(GCyRArmorMaterials.SPACE, type, properties);
    }

    public <T> LazyOptional<T> getCapability(@Nonnull final ItemStack itemStack, @Nonnull final Capability<T> cap) {
        if (cap == ForgeCapabilities.FLUID_HANDLER_ITEM) {
            return ForgeCapabilities.FLUID_HANDLER_ITEM.orEmpty(cap, LazyOptional.of(() -> new FluidHandlerItemStack(itemStack, Math.toIntExact(SpaceSuitArmorItem.CAPACITY))));
        }
        return LazyOptional.empty();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - (float)oxygenAmount(stack) * 13.0F / (float)oxygenMax(stack));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xff84ebf5;
    }

    public static boolean hasFullSet(LivingEntity entity) {
        int slotCount = 0;
        int armorCount = 0;
        for (ItemStack stack : entity.getArmorSlots()) {
            slotCount++;
            if (stack.getItem() instanceof SpaceSuitArmorItem) {
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
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        var storage = new ItemStackTransfer(chest);
        var fluid = FluidTransferHelper.getFluidTransfer(storage, 0);
        if (chest.getItem() instanceof SpaceSuitArmorItem && fluid != null) {
            return fluid.getFluidInTank(0).getAmount() > 0;
        }

        return false;
    }

    public static long oxygenAmount(LivingEntity entity) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        return oxygenAmount(chest);
    }

    public static long oxygenAmount(ItemStack stack) {
        if (stack.getItem() instanceof SpaceSuitArmorItem) {
            var storage = new ItemStackTransfer(stack);
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
        if (stack.getItem() instanceof SpaceSuitArmorItem) {
            var fluid = FluidTransferHelper.getFluidTransfer(new ItemStackTransfer(stack), 0);
            if (fluid != null) {
                return fluid.getTankCapacity(0);
            }
        }
        return 0;
    }

    public static void consumeSpaceSuitOxygen(LivingEntity entity, int amount) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof SpaceSuitArmorItem) {
            var storage = new ItemStackTransfer(chest);
            var fluid = FluidTransferHelper.getFluidTransfer(storage, 0);
            fluid.drain(FluidStack.create(fluid.getFluidInTank(0), amount), false, true);
        }

    }

    public UniformInt getTemperatureThreshold() {
        return UniformInt.of(60, 363);
    }

}
