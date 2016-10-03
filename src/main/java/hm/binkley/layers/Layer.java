package hm.binkley.layers;

import hm.binkley.layers.Layers.Surface;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.function.Function;

/**
 * @todo Trade-off between too many references (hard on GC) and ease of use
 */
@RequiredArgsConstructor
public class Layer
        extends LinkedHashMap<Object, Value> {
    private final Surface layers;
    private final String name;

    public Layer saveAndNext(final Function<Surface, Layer> ctor) {
        return layers.saveAndNext(this, ctor);
    }

    public void forget() {
        layers.forget(this);
    }

    @Override
    public final String toString() {
        return name + ": " + super.toString();
    }
}
