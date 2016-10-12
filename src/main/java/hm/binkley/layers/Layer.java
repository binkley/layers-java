package hm.binkley.layers;

import hm.binkley.layers.Layers.Surface;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hm.binkley.layers.DisplayStyle.BRACES;
import static hm.binkley.layers.DisplayStyle.BRACKETS;
import static java.lang.String.format;

/**
 * @todo Trade-off between too many references (hard on GC) and ease of use
 * @todo Rethink immutable vs editable - not consistently expressed
 * @todo Consider immutable look-a-likes, ala Guava
 */
@RequiredArgsConstructor
public class Layer
        implements LayerView {
    private final Map<Object, Value<?>> values = new LinkedHashMap<>();
    private final Map<Object, Object> details = new LinkedHashMap<>();
    protected final Surface layers;
    private final String name;

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public Set<Object> keys() {
        return values.keySet();
    }

    @Override
    public boolean containsKey(final Object key) {
        return values.containsKey(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Value<T> get(final Object key) {
        return (Value<T>) values.get(key);
    }

    public <T> Layer put(final Object key, final Value<T> value) {
        values.put(key, value);
        return this;
    }

    public Layer blend(final Layer that) {
        that.values.forEach(
                (key, value) -> values.compute(key, (k, existing) -> {
                    if (null != existing)
                        throw new IllegalStateException("Duplicate key " + k);
                    return value;
                }));
        return this;
    }

    public Layer blend(final LayerMaker<? extends Layer> that) {
        return blend(that.apply(layers));
    }

    /** @todo Immutable? */
    @Override
    @SuppressWarnings("WeakerAccess")
    public Stream<Entry<Object, Object>> stream() {
        return values.entrySet().stream().
                filter(pair -> pair.getValue().value().isPresent()).
                map(pair -> new SimpleImmutableEntry<>(pair.getKey(),
                        pair.getValue().value().get()));
    }

    @Override
    @SuppressWarnings("WeakerAccess")
    public Map<Object, Object> toMap() {
        return stream().
                collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        throwingMerger(), LinkedHashMap::new));
    }

    @SuppressWarnings("WeakerAccess")
    public Layers whatIfWith() {
        return layers.whatIfWith(this);
    }

    @SuppressWarnings("WeakerAccess")
    public Layers whatIfWithout() {
        return layers.whatIfWithout(this);
    }

    @Override
    @SuppressWarnings("WeakerAccess")
    public Map<Object, Object> details() {
        return details;
    }

    public <L extends Layer> L saveAndNext(final LayerMaker<L> ctor) {
        return layers.saveAndNext(this, ctor);
    }

    /** @todo Rethink #discard on Layer: only makes sense if saved */
    @Override
    @SuppressWarnings("WeakerAccess")
    public void discard() {
        layers.discard(this);
    }

    @SuppressWarnings("WeakerAccess")
    public LayerView view() {
        return this;
    }

    @Override
    public final String toString() {
        final String toString = name + ": " + BRACES.display(values);

        if (details.isEmpty())
            return toString;

        return toString + " " + BRACKETS.display(details);
    }

    /** @todo Hidden away in {@link Collectors}. */
    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(format("Duplicate key %s", u));
        };
    }
}
