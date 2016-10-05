package hm.binkley.layers;

import hm.binkley.layers.Layers.Surface;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

/**
 * @todo Trade-off between too many references (hard on GC) and ease of use
 */
@RequiredArgsConstructor
public class Layer {
    private final Map<Object, Value<?>> values = new LinkedHashMap<>();
    private final Map<Object, Object> details = new LinkedHashMap<>();
    private final Surface layers;
    private final String name;

    public String name() {
        return name;
    }

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

    public Layer blend(final Layer that) {
        values.putAll(that.values);
        return this;
    }

    public Layer blend(final LayerMaker that) {
        values.putAll(that.apply(layers).values);
        return this;
    }

    public Stream<Entry<Object, Object>> stream() {
        return values.entrySet().stream().
                filter(pair -> pair.getValue().value().isPresent()).
                map(pair -> new SimpleImmutableEntry<>(pair.getKey(),
                        pair.getValue().value().get()));
    }

    public Map<Object, Object> toMap() {
        return unmodifiableMap(stream().
                collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
    }

    public Map<Object, Object> details() {
        return details;
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
        return details.isEmpty() ? name + ": " + values
                : name + ": " + values + " [" + details + "]";
    }

    public class LayerView {
        public String name() {
            return Layer.this.name();
        }

        public boolean isEmpty() {
            return Layer.this.isEmpty();
        }

        public int size() {
            return Layer.this.size();
        }

        public Set<Object> keys() {
            return unmodifiableSet(Layer.this.keys());
        }

        public boolean containsKey(final Object key) {
            return Layer.this.containsKey(key);
        }

        public <T> Value<T> get(final Object key) {
            return Layer.this.get(key);
        }

        public Stream<Entry<Object, Object>> stream() {
            return Layer.this.stream();
        }

        public Map<Object, Object> toMap() {
            return Layer.this.toMap();
        }

        public Map<Object, Object> details() {
            return unmodifiableMap(Layer.this.details());
        }

        public void forget() {
            Layer.this.forget();
        }
    }
}
