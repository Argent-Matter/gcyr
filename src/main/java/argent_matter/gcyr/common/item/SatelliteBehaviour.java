package argent_matter.gcyr.common.item;

import argent_matter.gcyr.api.space.satellite.SatelliteType;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequiredArgsConstructor
public class SatelliteBehaviour implements IAddInformation {
    @Getter
    private final SatelliteType<?> satelliteType;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {

    }
}
