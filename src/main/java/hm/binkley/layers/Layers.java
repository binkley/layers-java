package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static hm.binkley.layers.Field.LAST;
import static java.lang.String.format;
import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;
import static lombok.AccessLevel.PRIVATE;

/**
 * {@code Layers} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@RequiredArgsConstructor(access = PRIVATE)
@SuppressWarnings("WeakerAccess")
public final class Layers {
    private final List<Layer> layers = new ArrayList<>();
    private final Map<String, Layer> names = new HashMap<>();
    private final Map<String, Field> fields = new HashMap<>();
    private final Map<String, Object> cache = new HashMap<>();

    public static <L extends Layer> Layers newLayers(
            final Function<Surface, L> next, final Consumer<L> firstLayer) {
        return newLayers(next, firstLayer, emptyMap());
    }

    /**
     * Creates a new {@code Layers} with a first layer of <var>L</var> type
     * (saved in <var>layer</var>), adding any <var>fields</var>.
     *
     * @param first the constructor for the first layer, never missing
     * @param layer the holder for the constructed first layer, never missing
     * @param fields the map of fields, never missing
     * @param <L> the type of the first layer
     *
     * @return the new layers object, never missing
     *
     * @see #add(String, Field) Adding field definitions
     */
    public static <L extends Layer> Layers newLayers(
            final Function<Surface, L> first, final Consumer<L> layer,
            final Map<String, Field> fields) {
        final Layers layers = new Layers();
        fields.forEach(layers::add);
        layer.accept(layers.newLayer(first));
        return layers;
    }

    /**
     * Adds a new field definition for <var>key</var>.
     *
     * @param key the key, never missing
     * @param field the field definition, never missing
     *
     * @return this object (for chaining)
     */
    public Layers add(final String key, final Field field) {
        if (cache.containsKey(key))
            throw new IllegalStateException(
                    format("Cannot add field for pre-existing key: %s", key));
        fields.put(key, field);
        return this;
    }

    /**
     * Gets the current, computed value for <var>key</var> based on all layers
     * containing the key.
     *
     * @param key the key, never missing
     * @param <T> the value type
     *
     * @return the value for <var>key</var>, or {@code null} if none
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) cache.get(key);
    }

    /**
     * Checks if there are no entries.
     *
     * @return {@code true} if there are no key-value pairs
     */
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    /**
     * Gets the number of entries.
     *
     * @return the number of computed key-value pairs
     */
    public int size() {
        return cache.size();
    }

    /**
     * Creates a map view of the computed entries.
     *
     * @return a map of the computed key-value pairs, never missing
     */
    public Map<String, Object> accepted() {
        return unmodifiableMap(cache);
    }

    /**
     * Gets a map view of the last accepted layer with <var>name</var>.
     *
     * @param name the layer name, never missing
     *
     * @return the layer or {@code null} if none
     */
    public Map<String, Object> layer(final String name) {
        final Layer layer = names.get(name);
        return null == layer ? null : layer.changed();
    }

    /**
     * Creates a stream of blankLayer-layer map views of entries in the same
     * order as layers were accepted.
     *
     * @return the stream of blankLayer-layer computed key-value pairs, never
     * missing
     */
    public Stream<Entry<String, Map<String, Object>>> history() {
        return layers.stream().
                map(l -> new SimpleImmutableEntry<>(l.name(), l.changed()));
    }

    @Override
    public String toString() {
        return cache.toString();
    }

    private <L extends Layer> L newLayer(final Function<Surface, L> next) {
        return next.apply(new LayersSurface());
    }

    @SuppressWarnings("unchecked")
    private <T> Field<T> fieldFor(final String key) {
        return fields.getOrDefault(key, LAST);
    }

    private static <T> T last(final List<T> list) {
        return list.get(list.size() - 1);
    }

    private final class LayersSurface
            implements Surface {
        @SuppressWarnings("unchecked")
        @Override
        public void check(final String key, final Object value)
                throws ClassCastException {
            final Class<Object> type = fieldFor(key).type;
            if (!type.isAssignableFrom(value.getClass()))
                throw new ClassCastException(
                        format("Wrong type of value (%s vs %s) for '%s': %s",
                                type, value.getClass(), key, value));
        }

        @Override
        public void accept(final String name, final Layer layer) {
            layers.add(layer);
            names.put(name, layer);
            layer.changed().forEach((k, v) -> cache.merge(k, v, fieldFor(k)));
        }

        @Override
        public Map<String, Object> changed(final Layer layer) {
            final Map<String, Object> changed = new HashMap<>(cache);
            layer.changed().
                    forEach((k, v) -> changed.merge(k, v, fieldFor(k)));
            return unmodifiableMap(changed);
        }

        @Override
        public Surface addAll(final Map<String, Field> fields) {
            fields.forEach(Layers.this::add);
            return this;
        }
    }
}
