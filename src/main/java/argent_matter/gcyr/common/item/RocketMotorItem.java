package argent_matter.gcyr.common.item;

import argent_matter.gcyr.common.block.RocketMotorBlock;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

import javax.annotation.Nullable;

public class RocketMotorItem extends BlockItem {

    public RocketMotorItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        RocketMotorBlock motor = (RocketMotorBlock) getBlock();
        tooltip.add(Component.translatable("tooltip.gcyr.tier", motor.getTier()));
        tooltip.add(Component.translatable("tooltip.gcyr.thrust",
                formatThrust(motor.getMotorType().getThrust())));
    }

    private static String formatThrust(double thrust) {
        return thrust == Math.rint(thrust) ? Long.toString((long) thrust) : Double.toString(thrust);
    }
}
