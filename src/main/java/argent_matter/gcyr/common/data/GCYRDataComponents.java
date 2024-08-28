package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.item.component.IdChip;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GCYRDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(GCYR.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<IdChip>> ID_CHIP = DATA_COMPONENTS.registerComponentType("id_chip",
            builder -> builder.persistent(IdChip.CODEC).networkSynchronized(IdChip.STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> SPACE_SUIT = DATA_COMPONENTS.registerComponentType("space_suit",
            builder -> builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC));

    public static void register(IEventBus modBus) {
        DATA_COMPONENTS.register(modBus);
    }
}
