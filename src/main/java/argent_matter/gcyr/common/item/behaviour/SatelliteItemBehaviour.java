package argent_matter.gcyr.common.item.behaviour;

import argent_matter.gcyr.api.space.satellite.SatelliteType;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class SatelliteItemBehaviour implements IItemComponent {
    // Store supplier to avoid premature registry access during static init
    private final Supplier<SatelliteType<?>> typeSupplier;

    public SatelliteType<?> getType() {
        return typeSupplier.get();
    }
}
