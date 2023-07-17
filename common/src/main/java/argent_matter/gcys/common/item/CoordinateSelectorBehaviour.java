package argent_matter.gcys.common.item;

import com.gregtechceu.gtceu.api.cover.filter.FluidFilter;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IItemUIFactory;
import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoordinateSelectorBehaviour implements IItemUIFactory, IAddInformation {
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {

    }

    @Override
    public ModularUI createUI(HeldItemUIFactory.HeldItemHolder holder, Player entityPlayer) {
        var held = holder.getHeld();
        return new ModularUI(176, 157, holder, entityPlayer)
                .background(GuiTextures.BACKGROUND)
                .widget(new LabelWidget(5, 3, held.getDescriptionId()))
                .widget(FluidFilter.loadFilter(held).openConfigurator((176 - 80) / 2, (60 - 55) / 2 + 15));
    }
}
