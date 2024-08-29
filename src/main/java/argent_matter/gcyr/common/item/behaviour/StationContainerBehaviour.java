package argent_matter.gcyr.common.item.behaviour;

import argent_matter.gcyr.common.data.GCYRDataComponents;
import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.util.PosWithState;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.mojang.serialization.Codec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
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

    public static final Codec<List<PosWithState>> CODEC = PosWithState.CODEC.listOf();
    public static final StreamCodec<RegistryFriendlyByteBuf, List<PosWithState>> STREAM_CODEC = PosWithState.STREAM_CODEC.apply(ByteBufCodecs.list());

    private static final String SATELLITE_BLOCKS_KEY = "gcyr:satellite_blocks";

    @Nullable
    public static List<PosWithState> getStationBlocks(ItemStack stack) {
        return stack.get(GCYRDataComponents.SPACE_STATION_BLOCKS);
    }

    public static void setStationBlocks(ItemStack stack, List<PosWithState> blocks) {
        stack.set(GCYRDataComponents.SPACE_STATION_BLOCKS, blocks);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Item.TooltipContext level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (!stack.has(GCYRDataComponents.SPACE_STATION_BLOCKS)) return;

        tooltipComponents.add(Component.translatable("metaitem.gcyr.satellite_package.has_satellite"));
    }
}
