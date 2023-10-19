package argent_matter.gcys.client.renderer.machine;

import argent_matter.gcys.common.data.GcysParticles;
import argent_matter.gcys.common.machine.multiblock.electric.DysonSystemControllerMachine;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DysonSystemControllerRenderer extends WorkableCasingMachineRenderer {
    public DysonSystemControllerRenderer() {
        super(GTCEu.id("block/casings/gcym/atomic_casing"), GTCEu.id("block/multiblock/assembly_line"), false);
    }

    @Override
    public int getViewDistance() {
        return 128;
    }

    @Override
    public boolean isGlobalRenderer(BlockEntity blockEntity) {
        return true;
    }

    @Override
    public void render(BlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (blockEntity instanceof IMachineBlockEntity machineBlockEntity && machineBlockEntity.getMetaMachine() instanceof DysonSystemControllerMachine machine && machine.isActive()) {
            var level = machine.getLevel();

            Direction frontFacing = machine.getFrontFacing();
            Direction backFacing = frontFacing.getOpposite();
            Direction leftFacing = frontFacing.getCounterClockWise();
            BlockPos pos = blockEntity.getBlockPos().mutable().move(backFacing, 4).move(leftFacing, 1).immutable();

            double x = pos.getX() + 0.5;
            double z = pos.getZ() + 0.5;
            for (int y = pos.getY(); y < 512; y += 4) {
                level.addAlwaysVisibleParticle(GcysParticles.DYSON_BEAM, x, y, z, 0, 0, 0);
            }
        }
    }
}
