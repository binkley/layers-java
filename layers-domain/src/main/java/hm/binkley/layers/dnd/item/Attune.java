package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.Surface;

import static hm.binkley.layers.DisplayStyle.BRACES;
import static hm.binkley.layers.values.Value.ofValue;
import static java.util.Collections.singleton;

public class Attune
        extends Layer {
    public static LayerMaker<Attune> attune(final MagicItem layer) {
        return layers -> new Attune(layers, layer);
    }

    public Attune(final Surface layers, final MagicItem magicItem) {
        super(layers, "Attune");
        put(Attunement.class, ofValue(singleton(magicItem)));
    }

    @Override
    public String toString() {
        return name() + ": " + BRACES.display(toMap());
    }
}
