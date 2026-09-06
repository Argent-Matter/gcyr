package argent_matter.gcyr.api.mui.drawable;

import argent_matter.gcyr.GCYR;

import brachy.modularui.drawable.UITexture;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface GCYRGuiTextures {

    UITexture PROGRESS_BAR_ROCKET = UITexture.builder()
            .location(GCYR.id("gui/progress_bar/progress_bar_rocket"))
            .imageSize(20, 40)
            .canApplyTheme()
            .build();
}
