package argent_matter.gcys.common.data;

import com.gregtechceu.gtceu.api.data.chemical.fluid.FluidTypes;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.sun.jna.platform.mac.Carbon;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class GcysMaterials {

    public static void init() {

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
                .components(Potassium, 2,Carbon, 1,Oxygen, 3)
                .buildAndRegister();

    public static final Material PotassiumHydroxide = new Material.Builder("potassium_hydroxide")
            .dust(1)
                .color(0xFF084E)
                .components(Potassium, 1, Oxygen, 1, Hydrogen, 1)
                .buildAndRegister();

    //endregion


    //region organic chemistry materials
    public static final Material ChloroNitrobenzene = new Material.Builder("chloronitrobenzene")
            .fluid(FluidTypes.GAS)
                .color(0x796013)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 6, Hydrogen, 4, Fluorine, 1, Nitrogen, 1, Oxygen, 2)
                .buildAndRegister();

    public static final Material PolyOxydiphenylenePyromellitimide = new Material.Builder("poly_oxydiphenylene_pyromellitimide")
            .polymer(1)
                .color(0x915A23)
                .appendFlags(STD_METAL, GENERATE_FOIL)
                .components(Carbon, 6, Hydrogen, 11, Nitrogen, 1, Oxygen, 1)
                .fluidTemp(493)
                .buildAndRegister();

    public static final Material PyromelliticDianhydride = new Material.Builder("pyrometillic_dianhydride")
            .dust()
                .color(0xB48C63)
                .components(Carbon, 10, Hydrogen, 2, Oxygen, 6)
                .fluidTemp(493)
                .buildAndRegister()
                .setFormula("C6H2(C2O3)2", true);

    public static final Material Durene = new Material.Builder("durene")
            .fluid()
                .color(0xB48C63)
                .components(Carbon, 10, Hydrogen, 14)
                .fluidTemp(493)
                .buildAndRegister()
                .setFormula("C6H2(CH3)4", true);

    public static final Material FormicAcid = new Material.Builder("formic_acid")
            .fluid(FluidTypes.ACID)
                .color(0xA6A6A6)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 1, Hydrogen, 2, Oxygen, 2)
                .buildAndRegister();

    public static final Material Dimethylformamide = new Material.Builder("dimethylformamide")
            .fluid()
                .color(0x554469)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 3, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
                .buildAndRegister()
                .setFormula("(CH3)2NC(O)H", true);

    public static final Material Oxydianiline = new Material.Builder("oxydianiline")
            .dust()
                .color(0xfAAEE0)
                .components(Carbon, 12, Hydrogen, 12, Nitrogen, 2, Oxygen, 1)
                .fluidTemp(493)
                .buildAndRegister();

    public static final Material OxydianilineSludge = new Material.Builder("oxydianiline_sludge")
            .fluid()
                .color(0xD9CCBF)
                .components(Oxydianiline, 1, Dimethylformamide, 1)
                .fluidTemp(493)
                .buildAndRegister();

    public static final Material AminoPhenol = new Material.Builder("aminophenol")
            .fluid()
                .color(0x784421)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 6, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
                .buildAndRegister()
                .setFormula("H2NC6H4OH", true);


    //endregion

}
