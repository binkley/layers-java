package hm.binkley.layers;

import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.Rule;
import hm.binkley.layers.set.FullnessFunction;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hm.binkley.layers.DisplayStyle.BRACES;
import static hm.binkley.layers.DisplayStyle.BRACKETS;
import static hm.binkley.layers.rules.Rule.layerSet;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;

/**
 * @todo Trade-off between too many references (hard on GC) and ease of use
 * @todo Rethink immutable vs editable - not consistently expressed
 * @todo Consider immutable look-a-likes, ala Guava
 */
@RequiredArgsConstructor
public class Layer
        implements LayerView {
    private final Map<Object, Object> values = new LinkedHashMap<>();
    private final Map<Object, Object> details = new LinkedHashMap<>();
    protected final LayerSurface layers;
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
    @SuppressWarnings({"unchecked", "TypeParameterUnusedInFormals"})
    public <T> T get(final Object key) {
        return (T) values.get(key);
    }

    public <T> Layer put(final Object key, final T value) {
        values.put(key, value);
        return this;
    }

    public <T, R> Layer put(final Object key,
            final Function<Object, Rule<T, R>> ctor) {
        return put(key, ctor.apply(key));
    }

    public <L extends Layer> Layer put(final Object key,
            final FullnessFunction<L> full) {
        return put(key, layerSet(full));
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

    @Override
    public Stream<Entry<Object, Object>> stream() {
        return values.entrySet().stream().
                filter(pair -> !(pair.getValue() instanceof Rule)).
                map(pair -> new SimpleImmutableEntry<>(pair.getKey(),
                        pair.getValue()));
    }

    @Override
    public Map<Object, Object> toMap() {
        return stream().
                collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        throwingMerger(), LinkedHashMap::new));
    }

    @Override
    public Map<Object, Object> details() {
        return unmodifiableMap(details);
    }

    public Layer putDetail(final Object key, final Object value) {
        details.put(key, value);
        return this;
    }

    public <L extends Layer> L saveAndNext(final LayerMaker<L> ctor) {
        return layers.saveAndNext(this, ctor);
    }

    @SuppressWarnings("WeakerAccess")
    public LayerView view() {
        return this;
    }

    @Override
    public String toString() {
        final String toString = name + ": " + BRACES.display(values);

        if (details.isEmpty())
            return toString;

        return toString + " " + BRACKETS.display(details);
    }

    /** Annoying: JDK hides this away in {@link Collectors}. */
    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(format("Duplicate key %s", u));
        };
    }
}
