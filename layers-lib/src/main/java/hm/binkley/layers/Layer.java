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
 */
@RequiredArgsConstructor
public class Layer<L extends Layer<L>>
        implements LayerView {
    private Map<Object, Object> values = new LinkedHashMap<>();
    private Map<Object, Object> details = new LinkedHashMap<>();
    protected final LayerSurface layers;
    private final String name;

    @SuppressWarnings("unchecked")
    public final L asThis() {
        return (L) this;
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public final int size() {
        return values.size();
    }

    @Override
    public final Set<Object> keys() {
        return values.keySet();
    }

    @Override
    public final boolean containsKey(final Object key) {
        return values.containsKey(key);
    }

    @Override
    @SuppressWarnings({"unchecked", "TypeParameterUnusedInFormals"})
    public final <T> T get(final Object key) {
        return (T) values.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> L put(final Object key, final T value) {
        values.put(key, value);
        return (L) this;
    }

    public final <R> L put(final Object key,
            final Function<Object, Rule<R>> ctor) {
        return put(key, ctor.apply(key));
    }

    public <K extends Layer<K>> L put(final Object key,
            final FullnessFunction<K> full) {
        return put(key, layerSet(full));
    }

    @SuppressWarnings({"WeakerAccess", "unchecked"})
    public final L blend(final Layer<?> that) {
        that.values.forEach(
                (key, value) -> values.compute(key, (k, existing) -> {
                    if (null == existing)
                        return value;
                    throw new IllegalStateException("Duplicate key " + k);
                }));
        return (L) this;
    }

    public final <K extends Layer<K>> L blend(final LayerMaker<K> that) {
        return blend(that.apply(layers));
    }

    @Override
    public final Stream<Entry<Object, Object>> stream() {
        return values.entrySet().stream().
                filter(pair -> !(pair.getValue() instanceof Rule)).
                map(pair -> new SimpleImmutableEntry<>(pair.getKey(),
                        pair.getValue()));
    }

    @Override
    public final Map<Object, Object> toMap() {
        return stream().
                collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        throwingMerger(), LinkedHashMap::new));
    }

    @Override
    public final Map<Object, Object> details() {
        return unmodifiableMap(details);
    }

    @SuppressWarnings("unchecked")
    public final L putDetail(final Object key, final Object value) {
        details.put(key, value);
        return (L) this;
    }

    public final <K extends Layer<K>> K saveAndNext(final LayerMaker<K> ctor) {
        values = unmodifiableMap(values);
        details = unmodifiableMap(details);
        return layers.saveAndNext(this, ctor);
    }

    @SuppressWarnings("WeakerAccess")
    public final LayerView view() {
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
