package argent_matter.gcyr.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.OreProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.attribute.FluidAttributes;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_PLATE;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.SHINY;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

@SuppressWarnings("unused")
public class GCyRMaterials {

    public static void init() {
        // Add flags to base GT materials
        IronMagnetic.addFlags(GENERATE_FOIL);
        TitaniumTungstenCarbide.addFlags(GENERATE_ROD);
        Titanium.setProperty(PropertyKey.ORE, new OreProperty());
    }


    //region first degree mats
    public static final Material PotassiumChloride = new Material.Builder("potassium_chloride")
            .dust(1)
            .color(0xF6F1AE)
            .components(Potassium, 1, Chlorine, 1)
            .buildAndRegister();

    public static final Material PotassiumCarbonate = new Material.Builder("potassium_carbonate")
            .dust(1)
            .color(0xF6F2C1)
            .components(Potassium, 2,Carbon, 1, Oxygen, 3)
            .buildAndRegister();

    public static final Material PotassiumHydroxide = new Material.Builder("potassium_hydroxide")
            .dust(1)
            .color(0xFF084E)
            .components(Potassium, 1, Oxygen, 1, Hydrogen, 1)
            .buildAndRegister();

    public static final Material Bisalloy400 = new Material.Builder("bisalloy_400")
            .ingot(5).fluid()
            .color(0x0ca819)
            .components(Carbon, 3, Manganese, 4, Silicon, 2, Chromium, 3, Molybdenum, 1, Iron, 11)
            .flags(MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_ROD, MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.GENERATE_GEAR, MaterialFlags.GENERATE_FRAME)
            .toolStats(ToolProperty.Builder.of(70.0F, 2.25F, 1296, 5).build())
            .blastTemp(10800, BlastProperty.GasTier.HIGHER)
            .buildAndRegister();

    public static final Material ChromicAcid = new Material.Builder("chromic_acid")
            .fluid(FluidStorageKeys.LIQUID, new FluidBuilder().attribute(FluidAttributes.ACID))
            .color(0xE5D8F2)
            .components(Hydrogen, 2, Chromium, 1, Oxygen, 4)
            .buildAndRegister();

    public static Material Trinaquadalloy = new Material.Builder("trinaquadalloy")
            .ingot().fluid()
            .color(0x281832).iconSet(MaterialIconSet.BRIGHT)
            .flags(GENERATE_PLATE, GENERATE_DENSE)
            .components(Trinium, 6, Naquadah, 2, Carbon, 1)
            .blastTemp(8747, BlastProperty.GasTier.HIGHER, GTValues.VA[GTValues.ZPM], 1200)
            .buildAndRegister();

    public static Material Fluorite = new Material.Builder("fluorite")
            .gem().ore()
            .color(0x0c9949).iconSet(MaterialIconSet.DIAMOND)
            .components(Calcium, 1, Fluorine, 2)
            .buildAndRegister();


    //endregion

    //region second degree mats

    public static final Material FiberGlass = new Material.Builder("fiberglass")
            .polymer(1)
            .fluid(FluidStorageKeys.LIQUID, new FluidBuilder().temperature(1531))
            .color(0xC7D9D1).iconSet(SHINY)
            .flags(GENERATE_FINE_WIRE, GENERATE_PLATE, GENERATE_FOIL, NO_SMASHING, NO_WORKING)
            .components(Epoxy, 2, SiliconDioxide, 7)
            .buildAndRegister();

    //endregion


    //region organic chemistry materials
    public static final Material ChloroNitrobenzene = new Material.Builder("chloronitrobenzene")
            .gas()
            .color(0x796013)
            .flags(DISABLE_DECOMPOSITION)
            .components(Carbon, 6, Hydrogen, 4, Fluorine, 1, Nitrogen, 1, Oxygen, 2)
            .buildAndRegister();

    public static final Material PyromelliticDianhydride = new Material.Builder("pyrometillic_dianhydride")
            .dust()
            .fluid(FluidStorageKeys.LIQUID, new FluidBuilder().temperature(493))
            .color(0xB48C63)
            .components(Carbon, 10, Hydrogen, 2, Oxygen, 6)
            .buildAndRegister()
            .setFormula("C6H2(C2O3)2", true);

    public static final Material Durene = new Material.Builder("durene")
            .fluid(FluidStorageKeys.LIQUID, new FluidBuilder().temperature(493))
            .color(0xB48C63)
            .components(Carbon, 10, Hydrogen, 14)
            .buildAndRegister()
            .setFormula("C6H2(CH3)4", true);

    public static final Material Dimethylformamide = new Material.Builder("dimethylformamide")
            .fluid()
            .color(0x554469)
            .flags(DISABLE_DECOMPOSITION)
            .components(Carbon, 3, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
            .buildAndRegister()
            .setFormula("(CH3)2NC(O)H", true);

    public static final Material Oxydianiline = new Material.Builder("oxydianiline")
            .fluid(FluidStorageKeys.LIQUID, new FluidBuilder().temperature(493))
            .color(0xfAAEE0)
            .components(Carbon, 12, Hydrogen, 12, Nitrogen, 2, Oxygen, 1)
            .buildAndRegister();

    public static final Material OxydianilineSludge = new Material.Builder("oxydianiline_sludge")
            .fluid(FluidStorageKeys.LIQUID, new FluidBuilder().temperature(493))
            .color(0xD9CCBF)
            .components(Oxydianiline, 1, Dimethylformamide, 1)
            .buildAndRegister();

    public static final Material AminoPhenol = new Material.Builder("aminophenol")
            .fluid()
            .color(0x784421)
            .flags(DISABLE_DECOMPOSITION)
            .components(Carbon, 6, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
            .buildAndRegister()
            .setFormula("H2NC6H4OH", true);

    public static final Material KaptonK = new Material.Builder("kapton_k")
            .polymer(1)
            .fluid(FluidStorageKeys.LIQUID, new FluidBuilder().temperature(493))
            .color(0x915A23)
            .appendFlags(STD_METAL, GENERATE_FOIL)
            .components(PyromelliticDianhydride, 1, Oxydianiline, 1)
            .fluidPipeProperties(1500, 450, true, true, true, false)
            .buildAndRegister();


    // Kevlar
    public static final Material Cuminaldehyde = new Material.Builder("cuminaldehyde")
            .fluid()
            .color(0xD9BF1A)
            .components(Carbon, 10, Hydrogen, 12, Oxygen, 1)
            .buildAndRegister();

    public static final Material Cuminol = new Material.Builder("cuminol")
            .fluid()
            .color(0xD9BF1A)
            .components(Carbon, 10, Hydrogen, 14)
            .buildAndRegister();

    public static final Material Cymene = new Material.Builder("cymene")
            .fluid()
            .color(0x615A0C)
            .components(Carbon, 10, Hydrogen, 14)
            .buildAndRegister();

    public static final Material TerephthalicAcid = new Material.Builder("therephthalic_acid")
            .fluid(FluidStorageKeys.LIQUID, new FluidBuilder().attribute(FluidAttributes.ACID))
            .color(0xDB9374)
            .components(Carbon, 6, Hydrogen, 4, CarbonDioxide, 2, Hydrogen, 2)
            .buildAndRegister()
            .setFormula("C6H4(CO2H)2", true);

    public static final Material TerephthaloylChloride = new Material.Builder("terephthaloyl_chloride")
            .fluid()
            .color(0xB883DE)
            .components(Carbon, 8, Hydrogen, 4, Chlorine, 2, Oxygen, 2)
            .buildAndRegister();

    public static final Material ParaPhenylenediamine = new Material.Builder("para_phenylenediamine")
            .fluid()
            .color(0xC3DE83)
            .components(ChloroNitrobenzene, 1, Ammonia, 1)
            .buildAndRegister();

    public static final Material ParaAramid = new Material.Builder("para_aramid")
            .polymer(2)
            .fluid(FluidStorageKeys.LIQUID, new FluidBuilder().temperature(493))
            .color(0xE6ED7B)
            .appendFlags(STD_METAL, GENERATE_FOIL)
            .components(ParaPhenylenediamine, 1, TerephthaloylChloride, 1)
            .buildAndRegister();

    //endregion

}
