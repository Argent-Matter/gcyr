package argent_matter.gcyr.common.data;

import argent_matter.gcyr.client.gui.screen.PlanetSelectionScreen;
import argent_matter.gcyr.common.gui.PlanetSelectionMenu;
import com.tterrag.registrate.util.entry.MenuEntry;

import static argent_matter.gcyr.api.registries.GCYRRegistries.REGISTRATE;

public class GCYRMenus {

    public static final MenuEntry<PlanetSelectionMenu> PLANET_SELECTION = REGISTRATE
            .menu("planet_selection", (type, windowId, inv, buf) -> new PlanetSelectionMenu(windowId, inv.player, buf), () -> PlanetSelectionScreen::new)
            .register();

    public static void init() {

    }
}
