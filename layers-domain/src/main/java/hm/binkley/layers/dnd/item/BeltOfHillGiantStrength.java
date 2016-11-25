package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.dnd.item.Rarity.RARE;

public final class BeltOfHillGiantStrength
        extends GiantBelt<BeltOfHillGiantStrength> {
    public BeltOfHillGiantStrength(final LayerSurface layers) {
        super(layers, "Hill", RARE, 21);
    }
}
