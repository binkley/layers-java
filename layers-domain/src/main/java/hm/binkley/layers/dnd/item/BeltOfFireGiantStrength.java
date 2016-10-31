package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.Surface;

import static hm.binkley.layers.dnd.item.Rarity.VERY_RARE;

public final class BeltOfFireGiantStrength
        extends GiantBelt {
    public BeltOfFireGiantStrength(final Surface layers) {
        super(layers, "Fire", VERY_RARE, 25);
    }
}