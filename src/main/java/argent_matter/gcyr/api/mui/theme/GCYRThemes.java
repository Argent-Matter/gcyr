package argent_matter.gcyr.api.mui.theme;

import brachy.modularui.drawable.GuiTextures;
import brachy.modularui.theme.WidgetTheme;
import brachy.modularui.theme.WidgetThemeKey;

import org.jetbrains.annotations.ApiStatus;

import static brachy.modularui.api.IThemeApi.FALLBACK;
import static brachy.modularui.api.IThemeApi.get;

@ApiStatus.NonExtendable
public interface GCYRThemes {

    WidgetThemeKey<WidgetTheme> PANEL_LIGHT_TEXT = get().widgetThemeKeyBuilder("panel_light_text", WidgetTheme.class)
            .defaultTheme(WidgetTheme.whiteTextShadow(176, 166, GuiTextures.MC_BACKGROUND))
            .fieldsOf(FALLBACK)
            .register();

    static void init() {}
}
