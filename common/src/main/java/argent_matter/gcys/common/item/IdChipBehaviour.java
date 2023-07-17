package argent_matter.gcys.common.item;

import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.common.data.GcysDimensionTypes;
import argent_matter.gcys.common.data.GcysItems;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.common.data.GTItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IdChipBehaviour implements IInteractionItem, IAddInformation {

    public static final int ID_MAX = 31, ID_EMPTY = -1;


    public static ItemStack stack(int configuration) {
        var stack = GcysItems.ID_CHIP.asStack();
        setCircuitId(stack, configuration);
        return stack;
    }

    public static void setCircuitId(ItemStack itemStack, int id) {
        if (id < 0 || id > ID_MAX)
            throw new IllegalArgumentException("Given id number is out of range!");
        var tagCompound = itemStack.getTag();
        if (tagCompound == null) {
            tagCompound = new CompoundTag();
            itemStack.setTag(tagCompound);
        }
        tagCompound.putInt("Id", id);
    }

    public static int getCircuitId(ItemStack stack) {
        if (stack != ItemStack.EMPTY && stack.hasTag() && stack.getTag().contains("Id", Tag.TAG_INT)) {
            return stack.getTag().getInt("Id");
        }

        return ID_EMPTY;
    }

    public static void assignId(Level level, ItemStack stack) {
        setCircuitId(stack, GcysCapabilityHelper.getSpaceStations((ServerLevel) level).getFreeStationId());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide && !itemStack.getOrCreateTag().contains("Id", Tag.TAG_INT) && player.isCrouching()) {
            assignId(level.getServer().getLevel(GcysDimensionTypes.SPACE_LEVEL), itemStack);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        int id = getCircuitId(stack);
        if (id != -1) {
            tooltipComponents.add(Component.translatable("metaitem.id_circuit.id", id));
            if (level != null && !level.isClientSide) {
                Vec2 pos = GcysCapabilityHelper.getSpaceStations(((ServerLevel) level).getServer().getLevel(GcysDimensionTypes.SPACE_LEVEL)).getStationPos(id);
                tooltipComponents.add(Component.translatable("metaitem.id_circuit.pos", pos.x, pos.y));
            }
        }
    }
}
