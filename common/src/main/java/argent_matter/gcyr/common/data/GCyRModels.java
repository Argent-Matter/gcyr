package argent_matter.gcyr.common.data;

import argent_matter.gcyr.api.block.IRocketMotorType;
import argent_matter.gcyr.common.block.FuelTankBlock;
import argent_matter.gcyr.common.block.RocketMotorBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DoorBlock;

public class GCyRModels {

    @ExpectPlatform
    public static void rocketMotorModel(DataGenContext<Block, RocketMotorBlock> ctx, RegistrateBlockstateProvider prov, IRocketMotorType type) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void airlockDoorModel(DataGenContext<Block, DoorBlock> ctx, RegistrateBlockstateProvider prov) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void fuelTankModel(DataGenContext<Block, FuelTankBlock> ctx, RegistrateBlockstateProvider prov) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void seatModel(DataGenContext<Block, CarpetBlock> ctx, RegistrateBlockstateProvider prov) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void randomRotatedModel(DataGenContext<Block, ? extends Block> ctx, RegistrateBlockstateProvider prov) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void crossModel(DataGenContext<Block, ? extends Block> ctx, RegistrateBlockstateProvider prov) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void blockTextureGeneratedModel(DataGenContext<Item, ? extends Item> ctx, RegistrateItemModelProvider prov) {
        throw new AssertionError();
    }
}
