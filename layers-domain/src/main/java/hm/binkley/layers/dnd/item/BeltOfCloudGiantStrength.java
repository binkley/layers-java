package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.dnd.item.Rarity.LEGENDARY;

public final class BeltOfCloudGiantStrength
        extends GiantBelt<BeltOfCloudGiantStrength> {
    public BeltOfCloudGiantStrength(final LayerSurface layers) {
        super(layers, "Cloud", LEGENDARY, 27);
    }
}
