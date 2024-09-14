package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.registries.GCYRRegistries;
import com.gregtechceu.gtceu.api.worldgen.DimensionMarker;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;

import static com.gregtechceu.gtceu.data.worldgen.GTDimensionMarkers.createAndRegister;

@SuppressWarnings("unused")
public class GCYRDimensionMarkers {

    static {
        GCYRRegistries.REGISTRATE.creativeModeTab(() -> null);
    }

    public static final BlockEntry<Block> LUNA_MARKER = createMarker("luna");
    public static final BlockEntry<Block> MARS_MARKER = createMarker("mars");
    public static final BlockEntry<Block> MERCURY_MARKER = createMarker("mercury");
    public static final BlockEntry<Block> VENUS_MARKER = createMarker("venus");

    public static final DimensionMarker LUNA = createAndRegister(GCYR.id("luna"), 1,
            () -> LUNA_MARKER, null);
    public static final DimensionMarker MARS = createAndRegister(GCYR.id("mars"), 2,
            () -> MARS_MARKER, null);
    public static final DimensionMarker MERCURY = createAndRegister(GCYR.id("mercury"), 2,
            () -> MERCURY_MARKER, null);
    public static final DimensionMarker VENUS = createAndRegister(GCYR.id("venus"), 2,
            () -> VENUS_MARKER, null);

    private static BlockEntry<Block> createMarker(String name) {
        return GCYRRegistries.REGISTRATE.block("%s_marker".formatted(name), Block::new)
                .lang(FormattingUtil.toEnglishName(name))
                .blockstate(NonNullBiConsumer.noop())
                // TODO textures
                //.blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().cube(ctx.getName(),
                //                prov.modLoc("block/dim_markers/%s/down".formatted(name)),
                //                prov.modLoc("block/dim_markers/%s/up".formatted(name)),
                //                prov.modLoc("block/dim_markers/%s/north".formatted(name)),
                //                prov.modLoc("block/dim_markers/%s/south".formatted(name)),
                //                prov.modLoc("block/dim_markers/%s/east".formatted(name)),
                //                prov.modLoc("block/dim_markers/%s/west".formatted(name)))
                //        .texture("particle", "#north")
                //        .guiLight(BlockModel.GuiLight.FRONT)))
                .item()
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
    }

    public static void init() {}
}
