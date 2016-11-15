package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.dnd.item.Rarity.VERY_RARE;

public final class BeltOfFrostGiantStrength
        extends GiantBelt {
    public BeltOfFrostGiantStrength(final LayerSurface layers) {
        super(layers, "Frost", VERY_RARE, 23);
    }
}
