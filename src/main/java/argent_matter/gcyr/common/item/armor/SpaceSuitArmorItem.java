package argent_matter.gcyr.common.item.armor;

import argent_matter.gcyr.common.recipe.type.SmithingSpaceSuitRecipe;

import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import com.google.common.primitives.Ints;

import java.util.List;

import org.jetbrains.annotations.Nullable;

public class SpaceSuitArmorItem extends ArmorItem {

    public static final long CAPACITY = 16 * FluidType.BUCKET_VOLUME;

    public SpaceSuitArmorItem(ArmorItem.Type type, Properties properties) {
        super(GCYRArmorMaterials.SPACE, type, properties);
    }

    public static <T> LazyOptional<T> getCapability(final ItemStack stack, final Capability<T> cap) {
        return ForgeCapabilities.FLUID_HANDLER_ITEM.orEmpty(cap, LazyOptional
                .of(() -> new FluidHandlerItemStack(stack, Ints.saturatedCast(SpaceSuitArmorItem.CAPACITY)) {

                    @Override
                    public boolean canFillFluidType(FluidStack fluid) {
                        return fluid.getFluid().is(GTMaterials.Oxygen.getFluidTag());
                    }
                }));
    }

    public static boolean isSpaceSuitItem(ItemStack stack) {
        return stack.getItem() instanceof SpaceSuitArmorItem ||
                (stack.hasTag() && stack.getTag().getBoolean(SmithingSpaceSuitRecipe.SPACE_SUIT_ARMOR_KEY));
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - (float) oxygenAmount(stack) * 13.0F / (float) oxygenMax(stack));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xff84ebf5;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip,
                                TooltipFlag isAdvanced) {
        tooltip.add(Component.translatable("tooltip.gcyr.spacesuit"));
        if (stack.is(Tags.Items.ARMORS_CHESTPLATES)) {
            FluidUtil.getFluidHandler(stack)
                    .ifPresent(h -> tooltip.add(Component.translatable("tooltip.gcyr.spacesuit.stored",
                            h.getFluidInTank(0).getAmount(), h.getTankCapacity(0))));
        }
    }

    public static boolean hasFullSet(LivingEntity entity) {
        int slotCount = 0;
        int armorCount = 0;
        for (ItemStack stack : entity.getArmorSlots()) {
            slotCount++;
            if (isSpaceSuitItem(stack)) {
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
        if (isSpaceSuitItem(stack)) {
            return FluidUtil.getFluidHandler(stack).map(h -> h.getFluidInTank(0).getAmount()).orElse(0);
        }
        return 0;
    }

    public static long oxygenMax(LivingEntity entity) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        return oxygenMax(chest);
    }

    public static long oxygenMax(ItemStack stack) {
        if (isSpaceSuitItem(stack)) {
            return FluidUtil.getFluidHandler(stack).map(h -> h.getTankCapacity(0)).orElse(0);
        }
        return 0;
    }

    public static void consumeSpaceSuitOxygen(LivingEntity entity, int amount) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (isSpaceSuitItem(chest)) {
            FluidUtil.getFluidHandler(chest).ifPresent(h -> h.drain(amount, IFluidHandler.FluidAction.EXECUTE));
        }
    }

    public static UniformInt getTemperatureThreshold() {
        return UniformInt.of(60, 363);
    }
}
