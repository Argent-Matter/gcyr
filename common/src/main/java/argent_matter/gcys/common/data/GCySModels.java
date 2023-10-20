package argent_matter.gcys.common.data;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;

public class GCySModels {

    @ExpectPlatform
    public static void rocketMotorModel(DataGenContext<Block, RotatedPillarBlock> ctx, RegistrateBlockstateProvider prov) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void airlockDoorModel(DataGenContext<Block, DoorBlock> ctx, RegistrateBlockstateProvider prov) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void axisModel(DataGenContext<Block, RotatedPillarBlock> ctx, RegistrateBlockstateProvider prov) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void seatModel(DataGenContext<Block, CarpetBlock> ctx, RegistrateBlockstateProvider prov) {
        throw new AssertionError();
    }


}
