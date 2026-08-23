package argent_matter.gcyr.common.block;

import argent_matter.gcyr.api.block.IRocketPart;

import net.minecraft.world.level.block.Block;

public class LandingModuleBlock extends Block implements IRocketPart {

    public LandingModuleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getTier() {
        return 1;
    }
}
