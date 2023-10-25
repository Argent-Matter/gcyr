package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.lowdragmc.lowdraglib.utils.LDLItemGroup;

public class GCyRCreativeModeTabs {
    public static LDLItemGroup GCYR = new LDLItemGroup(GCyR.MOD_ID, GCyR.MOD_ID, () -> GTItems.FLUID_CELL.asStack());
}
