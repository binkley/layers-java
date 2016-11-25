package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.dnd.item.Rarity.VERY_RARE;

public final class BeltOfStoneGiantStrength
        extends GiantBelt<BeltOfStoneGiantStrength> {
    public BeltOfStoneGiantStrength(final LayerSurface layers) {
        super(layers, "Stone", VERY_RARE, 23);
    }
}
