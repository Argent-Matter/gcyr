package argent_matter.gcyr.common.item;

import argent_matter.gcyr.api.space.satellite.SatelliteType;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SatelliteItemBehaviour implements IItemComponent {
    public final SatelliteType<?> type;
}
