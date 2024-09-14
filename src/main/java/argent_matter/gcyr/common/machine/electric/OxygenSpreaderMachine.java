package argent_matter.gcyr.common.machine.electric;

import argent_matter.gcyr.common.entity.data.EntityOxygenSystem;
import argent_matter.gcyr.util.FloodFiller3D;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.joml.Vector3f;

import java.util.Set;

public class OxygenSpreaderMachine extends SimpleTieredMachine {

    public static int tankScalingFunction(int tier) {
        return tier * 4 * FluidHelper.getBucket();
    }

    public OxygenSpreaderMachine(IMachineBlockEntity holder, int tier, Object... args) {
        super(holder, tier, OxygenSpreaderMachine::tankScalingFunction, args);
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        runAlgorithm();
    }

    public boolean canDistribute(int oxygenBlocks) {
        return ((SizedFluidIngredient)recipeLogic.getLastRecipe().getInputContents(FluidRecipeCapability.CAP).getFirst().content)
                .getFluids()[0].getAmount() / FluidHelper.getBucket() >= oxygenBlocks;
    }

    public void runAlgorithm() {
        Set<BlockPos> positions = FloodFiller3D.run(getLevel(), getPos(), getFrontFacing());

        if (this.canDistribute(positions.size())) {
            EntityOxygenSystem.setEntry(this.getLevel(), this.getPos(), positions);
        } else if (!getLevel().isClientSide()) {
            EntityOxygenSystem.removeEntry(this.getLevel(), this.getPos());
        }

        this.spawnParticles(positions);
    }

    // Spawn the bubble particles in each oxygenated position. The "show" button must be clicked in the oxygen distributor GUI in order to work.
    public void spawnParticles(Set<BlockPos> positions) {
        if (!getLevel().isClientSide()) {
            for (BlockPos pos : positions) {
                this.getLevel().addParticle(new DustParticleOptions(new Vector3f(1, 1, 1), 1), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.5, 0.5, 0.5);
            }
        }
    }
}
