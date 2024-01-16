package argent_matter.gcyr.common.item.armor;

import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import com.lowdragmc.lowdraglib.side.fluid.FluidTransferHelper;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SpaceSuitArmorItem extends ArmorItem {
    public static final long CAPACITY = (int) (16 * FluidHelper.getBucket());

    public SpaceSuitArmorItem(ArmorItem.Type type, Properties properties) {
        super(GCyRArmorMaterials.SPACE, type, properties);
    }

    @ExpectPlatform
    public static SpaceSuitArmorItem create(ArmorItem.Type slot, Item.Properties properties) {
        throw new AssertionError();
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

    public static void consumeSpaceSuitOxygen(LivingEntity entity, int amount) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        var storage = new ItemStackTransfer(chest);
        var fluid = FluidTransferHelper.getFluidTransfer(storage, 0);
        if (chest.getItem() instanceof SpaceSuitArmorItem) {
            fluid.drain(amount, false);
        }

    }

    public UniformInt getTemperatureThreshold() {
        return UniformInt.of(60, 363);
    }

}
