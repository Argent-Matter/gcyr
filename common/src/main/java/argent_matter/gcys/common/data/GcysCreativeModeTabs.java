package argent_matter.gcys.common.data;

import argent_matter.gcys.GregicalitySpace;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.lowdragmc.lowdraglib.utils.LDLItemGroup;

public class GcysCreativeModeTabs {
    public static LDLItemGroup GCYS = new LDLItemGroup(GregicalitySpace.MOD_ID, "gcys", () -> GTItems.FLUID_CELL.asStack());
}
