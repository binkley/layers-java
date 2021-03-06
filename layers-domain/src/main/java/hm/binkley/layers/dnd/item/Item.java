package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.LayerSurface;

/** @todo Figure out how to use types for height/weight */
public class Item<L extends Item<L>>
        extends Layer<L> {
    public Item(final LayerSurface layers, final String name,
            final String description, final Type type, final Weight weight,
            final Volume volume, final String notes) {
        super(layers, name);
        putDetail("Description", description).
                putDetail(Type.class, type).
                put(Weight.class, weight).
                put(Volume.class, volume).
                putDetail("Notes", notes);
    }

    public Weight weight() {
        return get(Weight.class);
    }

    public Volume volume() {
        return get(Volume.class);
    }
}
