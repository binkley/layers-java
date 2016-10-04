package hm.binkley.layers;

import hm.binkley.layers.Layers.Surface;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @todo Trade-off between too many references (hard on GC) and ease of use
 */
@RequiredArgsConstructor
public class Layer {
    private final Map<Object, Value> values = new LinkedHashMap<>();
    private final Surface layers;
    private final String name;

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public int size() {
        return values.size();
    }

    public Set<Object> keys() {
        return values.keySet();
    }

    public boolean containsKey(final Object key) {
        return values.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public <T> Value<T> get(final Object key) {
        return (Value<T>) values.get(key);
    }

    public <T> Layer put(final Object key, final Value<T> value) {
        values.put(key, value);
        return this;
    }

    public Layer saveAndNext(final LayerMaker ctor) {
        return layers.saveAndNext(this, ctor);
    }

    public void forget() {
        layers.forget(this);
    }

    public LayerView view() {
        return new LayerView();
    }

    @Override
    public final String toString() {
        return name + ": " + values;
    }

    public class LayerView {
        public boolean isEmpty() {
            return Layer.this.isEmpty();
        }

        public int size() {
            return Layer.this.size();
        }

        public Set<Object> keys() {
            return Collections.unmodifiableSet(Layer.this.keys());
        }

        public boolean containsKey(final Object key) {
            return Layer.this.containsKey(key);
        }

        public <T> Value<T> get(final Object key) {
            return Layer.this.get(key);
        }

        public void forget() {
            Layer.this.forget();
        }
    }
}
