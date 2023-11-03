package argent_matter.gcyr.common.item;

import argent_matter.gcyr.common.data.GCyRItems;
import argent_matter.gcyr.util.PosWithState;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class StationContainerBehaviour implements IAddInformation {

    private static final String SATELLITE_BLOCKS_KEY = "gcyr:satellite_blocks";

    @Nullable
    public static Set<PosWithState> getSatelliteBlocks(ItemStack stack) {
        if (!GCyRItems.SPACE_STATION_PACKAGE.isIn(stack)) return null;
        if (!stack.hasTag() || !stack.getTag().contains(SATELLITE_BLOCKS_KEY, Tag.TAG_LIST)) return null;

        Set<PosWithState> states = new HashSet<>();
        ListTag blocks = stack.getOrCreateTag().getList(SATELLITE_BLOCKS_KEY, Tag.TAG_COMPOUND);
        for (int i = 0; i < blocks.size(); ++i) {
            states.add(PosWithState.readFromTag(blocks.getCompound(i)));
        }
        return states;
    }

    public static void setSatelliteBlocks(ItemStack stack, Set<PosWithState> blocks) {
        if (!GCyRItems.SPACE_STATION_PACKAGE.isIn(stack)) return;
        if (stack.hasTag() && stack.getTag().contains(SATELLITE_BLOCKS_KEY, Tag.TAG_LIST)) return;

        ListTag blockTag = new ListTag();
        for (PosWithState state : blocks) {
            blockTag.add(state.writeToTag());
        }
        stack.getOrCreateTag().put(SATELLITE_BLOCKS_KEY, blockTag);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (!GCyRItems.SPACE_STATION_PACKAGE.isIn(stack) || !stack.hasTag() || !stack.getTag().contains(SATELLITE_BLOCKS_KEY, Tag.TAG_LIST)) return;

        tooltipComponents.add(Component.translatable("metaitem.gcyr.satellite_package.has_satellite"));
    }
}
