package argent_matter.gcyr.common.data.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.block.IRocketMotorType;
import argent_matter.gcyr.common.block.FuelTankBlock;
import argent_matter.gcyr.common.block.RocketMotorBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;

public class GCyRModelsImpl {
    public static void rocketMotorModel(DataGenContext<Block, RocketMotorBlock> ctx, RegistrateBlockstateProvider prov, IRocketMotorType type) {
        prov.simpleBlock(ctx.getEntry(), prov.models().cubeBottomTop("%s_rocket_motor".formatted(type.getSerializedName()),
                GCyR.id("block/casings/%s_rocket_motor/rocket_motor_side".formatted(type.getSerializedName())), GCyR.id("block/casings/%s_rocket_motor/rocket_motor_bottom".formatted(type.getSerializedName())), GCyR.id("block/casings/%s_rocket_motor/rocket_motor_top".formatted(type.getSerializedName()))
        ));
    }

    public static void airlockDoorModel(DataGenContext<Block, DoorBlock> ctx, RegistrateBlockstateProvider prov) {
        prov.doorBlock(ctx.getEntry(), GCyR.id("block/airlock_door_bottom"), GCyR.id("block/airlock_door_top"));
    }

    public static void fuelTankModel(DataGenContext<Block, FuelTankBlock> ctx, RegistrateBlockstateProvider prov) {
        prov.axisBlock(ctx.getEntry());
    }

    public static void seatModel(DataGenContext<Block, CarpetBlock> ctx, RegistrateBlockstateProvider prov) {
        prov.simpleBlock(ctx.getEntry(), prov.models().carpet("seat", new ResourceLocation("block/light_gray_wool")));
    }

    public static void randomRotatedModel(DataGenContext<Block, ? extends Block> ctx, RegistrateBlockstateProvider prov) {
        Block block = ctx.getEntry();
        ModelFile cubeAll = prov.cubeAll(block);
        ModelFile cubeMirroredAll = prov.models().singleTexture(ctx.getName() + "_mirrored", prov.mcLoc(ModelProvider.BLOCK_FOLDER + "/cube_mirrored_all"), "all", prov.blockTexture(block));
        ConfiguredModel[] models = ConfiguredModel.builder()
                .modelFile(cubeAll)
                .rotationY(0)
                .nextModel()
                .modelFile(cubeAll)
                .rotationY(180)
                .nextModel()
                .modelFile(cubeMirroredAll)
                .rotationY(0)
                .nextModel()
                .modelFile(cubeMirroredAll)
                .rotationY(180)
                .build();
        prov.simpleBlock(block, models);
    }
}
