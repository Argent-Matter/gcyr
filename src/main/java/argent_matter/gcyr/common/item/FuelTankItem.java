package argent_matter.gcyr.common.item;

import argent_matter.gcyr.common.block.FuelTankBlock;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

import javax.annotation.Nullable;

public class FuelTankItem extends BlockItem {

    public FuelTankItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        FuelTankBlock tank = (FuelTankBlock) getBlock();
        tooltip.add(Component.translatable("tooltip.gcyr.tier",
                Component.literal(Integer.toString(tank.getTier())).withStyle(ChatFormatting.WHITE))
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.gcyr.fluid_capacity")
                .withStyle(ChatFormatting.BLUE)
                .append(Component.literal(" " + tank.getTankProperties().getFuelStorage() + " mB")
                        .withStyle(ChatFormatting.WHITE)));
    }
}
