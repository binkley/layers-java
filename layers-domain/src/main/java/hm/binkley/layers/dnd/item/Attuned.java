package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.Surface;

import java.util.Set;

import static hm.binkley.layers.values.Value.ofValue;

public class Attuned
        extends Layer {
    public static LayerMaker<Attuned> attune(final MagicItem layer) {
        return layers -> new Attuned(layers, layer);
    }

    public Attuned(final Surface layers, final MagicItem magicItem) {
        super(layers, "Attuned");
        put(Attuned.class, ofValue(magicItem));
    }

    @Override
    public String toString() {
        return name() + ": " + this.<Layer, Set<Layer>>get(Attuned.class).
                value().
                get().
                name();
    }
}
