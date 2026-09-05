package argent_matter.gcyr.common.data.client;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.block.IRocketMotorType;
import argent_matter.gcyr.common.block.RocketMotorBlock;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DoorBlock;

import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import static net.minecraftforge.client.model.generators.ModelProvider.BLOCK_FOLDER;

public class GCYRModels {

    public static void rocketMotorModel(DataGenContext<Block, RocketMotorBlock> ctx, RegistrateBlockstateProvider prov,
                                        IRocketMotorType type) {
        String name = type.getSerializedName();

        prov.simpleBlock(ctx.getEntry(), prov.models().cubeBottomTop("%s_rocket_motor".formatted(name),
                GCYR.id("block/casings/%s_rocket_motor/rocket_motor_side".formatted(name)),
                GCYR.id("block/casings/%s_rocket_motor/rocket_motor_bottom".formatted(name)),
                GCYR.id("block/casings/%s_rocket_motor/rocket_motor_top".formatted(name))));
    }

    public static void airlockDoorModel(DataGenContext<Block, DoorBlock> ctx, RegistrateBlockstateProvider prov) {
        prov.doorBlock(ctx.getEntry(), GCYR.id("block/airlock_door_bottom"), GCYR.id("block/airlock_door_top"));
    }

    public static void seatModel(DataGenContext<Block, CarpetBlock> ctx, RegistrateBlockstateProvider prov) {
        prov.simpleBlock(ctx.getEntry(), prov.models().carpet("seat", ResourceLocation.withDefaultNamespace("block/light_gray_wool")));
    }

    public static void randomRotatedModel(DataGenContext<Block, ? extends Block> ctx,
                                          RegistrateBlockstateProvider prov) {
        Block block = ctx.getEntry();
        ModelFile cubeAll = prov.cubeAll(block);
        ModelFile cubeMirroredAll = cubeMirroredAll(prov, ctx);
        prov.simpleBlock(block, addRandomRotatedModels(ConfiguredModel.builder(), cubeAll, cubeMirroredAll, 1).build());
    }

    public static void lunarSandModel(DataGenContext<Block, ? extends Block> ctx, RegistrateBlockstateProvider prov) {
        Block block = ctx.getEntry();
        ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();

        builder = addRandomRotatedModels(builder, prov.cubeAll(block), cubeMirroredAll(prov, ctx), 41);
        builder = addRandomYRotatedAltModels(builder, ctx, prov, "alt", 21);
        builder = addRandomYRotatedAltModels(builder, ctx, prov, "small_crater", 1);
        builder = addRandomYRotatedAltModels(builder, ctx, prov, "big_crater", 1);
        builder = addRandomYRotatedAltModels(builder, ctx, prov, "small_rock", 1);
        builder = addRandomYRotatedAltModels(builder, ctx, prov, "big_rock", 1);

        prov.simpleBlock(block, builder.build());
    }

    private static ConfiguredModel.Builder<?> addRandomRotatedModels(ConfiguredModel.Builder<?> builder,
                                                                     ModelFile model, ModelFile mirrored, int weight) {
        return builder.modelFile(model).rotationY(0).weight(weight).nextModel()
                .modelFile(mirrored).rotationY(0).weight(weight).nextModel()
                .modelFile(model).rotationY(180).weight(weight).nextModel()
                .modelFile(mirrored).rotationY(180).weight(weight).nextModel();
    }

    private static ConfiguredModel.Builder<?> addRandomYRotatedAltModels(ConfiguredModel.Builder<?> builder,
                                                                         DataGenContext<Block, ? extends Block> ctx,
                                                                         RegistrateBlockstateProvider prov,
                                                                         String suffix, int weight) {
        suffix = "_" + suffix;

        String modelName = ctx.getName() + suffix;
        ResourceLocation baseTexture = prov.blockTexture(ctx.getEntry());
        ResourceLocation altTexture = baseTexture.withSuffix(suffix);

        ModelFile normal = prov.models().cubeTop(modelName, baseTexture, altTexture);
        ModelFile mirrored = cubeTopMirrored(prov, modelName + "_mirrored", baseTexture, altTexture);

        return addRandomRotatedModels(builder, normal, mirrored, weight);
    }

    public static void crossModel(DataGenContext<Block, ? extends Block> ctx, RegistrateBlockstateProvider prov) {
        Block block = ctx.getEntry();
        ModelFile cross = prov.models().cross(ctx.getName(), prov.blockTexture(block));
        prov.simpleBlock(block, cross);
    }

    public static void blockTextureGeneratedModel(DataGenContext<Item, ? extends Item> ctx,
                                                  RegistrateItemModelProvider prov) {
        prov.generated(ctx::getEntry, prov.modLoc("block/" + ctx.getName()));
    }

    public static NonNullBiConsumer<DataGenContext<Block, Block>, RegistrateBlockstateProvider> cubeAllModel(String name,
                                                                                                             ResourceLocation texture) {
        return (ctx, prov) -> {
            prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll(name, texture));
        };
    }

    public static NonNullBiConsumer<DataGenContext<Block, Block>, RegistrateBlockstateProvider> grassBlockModel(String name,
                                                                                                                ResourceLocation bottomTexture) {
        return grassBlockModel(name,
                GCYR.id("block/" + name + "_side"),
                bottomTexture,
                GCYR.id("block/" + name + "_top"));
    }

    public static NonNullBiConsumer<DataGenContext<Block, Block>, RegistrateBlockstateProvider> grassBlockModel(String name,
                                                                                                                ResourceLocation sideTexture,
                                                                                                                ResourceLocation bottomTexture,
                                                                                                                ResourceLocation topTexture) {
        return (ctx, prov) -> {
            ModelFile base = prov.models().cubeBottomTop(name, sideTexture, bottomTexture, topTexture);
            ConfiguredModel modelY0 = new ConfiguredModel(base, 0, 0, false);
            ConfiguredModel modelY90 = new ConfiguredModel(base, 0, 90, false);
            ConfiguredModel modelY180 = new ConfiguredModel(base, 0, 180, false);
            ConfiguredModel modelY270 = new ConfiguredModel(base, 0, 270, false);
            prov.getVariantBuilder(ctx.getEntry())
                    .partialState()
                    .setModels(modelY0, modelY90, modelY180, modelY270);
        };
    }

    public static ModelFile cubeMirroredAll(RegistrateBlockstateProvider provider,
                                            String name, ResourceLocation texture) {
        return provider.models().singleTexture(name, provider.mcLoc(BLOCK_FOLDER + "/cube_mirrored_all"),
                "all", texture);
    }

    public static ModelFile cubeMirroredAll(RegistrateBlockstateProvider provider,
                                            DataGenContext<Block, ? extends Block> ctx) {
        return cubeMirroredAll(provider, ctx.getName() + "_mirrored", provider.blockTexture(ctx.getEntry()));
    }

    public static ModelFile cubeTopMirrored(RegistrateBlockstateProvider provider,
                                            String name, ResourceLocation side, ResourceLocation top) {
        return provider.models().withExistingParent(name, GCYR.id(BLOCK_FOLDER + "/cube_top_mirrored"))
                .texture("side", side)
                .texture("top", top);
    }
}
