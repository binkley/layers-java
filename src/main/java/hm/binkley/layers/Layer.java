package hm.binkley.layers;

import lombok.AllArgsConstructor;

import java.util.LinkedHashMap;
import java.util.function.Function;

/**
 * @todo Trade-off between too many references (hard on GC) and ease of use
 */
@AllArgsConstructor
public class Layer
        extends LinkedHashMap<Object, Value> {
    private Layers layers;

    public Layer commit(final Function<Layers, Layer> ctor) {
        layers.add(this);
        final Layer next = layers.newLayer(ctor);
//        layers = null;
        return next;
    }

    public void rollback() {
        layers.remove(this); // TODO: Make sense?  Rename method?
//        layers = null;
    }
}
