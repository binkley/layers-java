package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.dnd.item.Rarity.VERY_RARE;

public final class BeltOfFireGiantStrength
        extends GiantBelt<BeltOfFireGiantStrength> {
    public BeltOfFireGiantStrength(final LayerSurface layers) {
        super(layers, "Fire", VERY_RARE, 25);
    }
}
