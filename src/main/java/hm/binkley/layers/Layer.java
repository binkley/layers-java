package hm.binkley.layers;

import hm.binkley.layers.Layers.Surface;
import lombok.AllArgsConstructor;

import java.util.LinkedHashMap;
import java.util.function.Function;

/**
 * @todo Trade-off between too many references (hard on GC) and ease of use
 */
@AllArgsConstructor
public class Layer
        extends LinkedHashMap<Object, Value> {
    private Surface layers;

    public Layer saveAndNext(final Function<Surface, Layer> ctor) {
        return layers.saveAndNext(this, ctor);
    }

    public void rollback() {
        layers.forget(this);
    }
}
