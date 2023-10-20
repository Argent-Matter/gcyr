package argent_matter.gcys.common.data;

import argent_matter.gcys.GCyS;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.lowdragmc.lowdraglib.utils.LDLItemGroup;

public class GCySCreativeModeTabs {
    public static LDLItemGroup GCYS = new LDLItemGroup(GCyS.MOD_ID, "gcys", () -> GTItems.FLUID_CELL.asStack());
}
