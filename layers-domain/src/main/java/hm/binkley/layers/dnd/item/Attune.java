package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.Surface;

import java.util.Set;

import static hm.binkley.layers.values.Value.ofValue;

public class Attune
        extends Layer {
    public static LayerMaker<Attune> attune(final MagicItem layer) {
        return layers -> new Attune(layers, layer);
    }

    public Attune(final Surface layers, final MagicItem magicItem) {
        super(layers, "Attune");
        put(Attunement.class, ofValue(magicItem));
    }

    @Override
    public String toString() {
        return name() + ": " + this.<Layer, Set<Layer>>get(Attunement.class).
                value().
                get().
                name();
    }
}
