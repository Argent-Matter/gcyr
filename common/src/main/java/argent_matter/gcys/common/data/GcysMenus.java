package argent_matter.gcys.common.data;

import argent_matter.gcys.client.gui.screen.PlanetSelectionScreen;
import argent_matter.gcys.common.gui.PlanetSelectionMenu;
import com.tterrag.registrate.util.entry.MenuEntry;

import static argent_matter.gcys.api.registries.GcysRegistries.REGISTRATE;

public class GcysMenus {

    public static final MenuEntry<PlanetSelectionMenu> PLANET_SELECTION = REGISTRATE
            .menu((type, windowId, inv, buf) -> new PlanetSelectionMenu(windowId, inv.player, buf), () -> PlanetSelectionScreen::new)
            .register();
}
