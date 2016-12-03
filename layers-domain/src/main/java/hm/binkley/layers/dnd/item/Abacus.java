package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.dnd.item.Type.EQUIPMENT;
import static hm.binkley.layers.dnd.item.Volume.SPACELESS;

public final class Abacus
        extends Item<Abacus> {
    public Abacus(final LayerSurface layers) {
        super(layers, "Abacus", "", EQUIPMENT, Weight.inPounds(2), SPACELESS,
                "");
    }
}
