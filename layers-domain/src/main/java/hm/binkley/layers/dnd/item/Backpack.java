package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.dnd.item.Type.EQUIPMENT;
import static hm.binkley.layers.dnd.item.Volume.ONE_CUBIC_FOOT;
import static hm.binkley.layers.dnd.item.Weight.inPounds;

public final class Backpack
        extends Item<Backpack> {
    public Backpack(final LayerSurface layers) {
        super(layers, "Backpack",
                "A backpack can hold one cubic foot or 30 pounds of gear. "
                        + "You can also strap items, such as a Bedroll or a"
                        + " coil of rope, to the outside of a backpack.",
                EQUIPMENT, inPounds(5), ONE_CUBIC_FOOT, "");
    }
}
