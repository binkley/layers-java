package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static hm.binkley.layers.Field.LAST;
import static java.lang.String.format;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toList;
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

    /**
     * Creates a new {@code Layers} with a first layer of <var>L</var> type
     * (waved in <var>layer</var>).
     *
     * @param next the provider for the first layer, never missing
     * @param layer the holder for the first layer, never missing
     * @param <L> the type of the first layer
     *
     * @return the new layers object, never missing
     *
     * @see #newLayers(Function, Consumer, Map)
     */
    public static <L extends Layer<L>> Layers newLayers(
            final Function<Surface, L> next, final Consumer<L> layer) {
        return newLayers(next, layer, emptyMap());
    }

    /**
     * Creates a new {@code Layers} with a first layer of <var>L</var> type
     * (saved in <var>layer</var>), adding any <var>fields</var>.
     *
     * @param first the provider for the first layer, never missing
     * @param layer the holder for the first layer, never missing
     * @param fields the map of fields, never missing
     * @param <L> the type of the first layer
     *
     * @return the new layers object, never missing
     *
     * @see #add(String, Field) Adding field definitions
     */
    public static <L extends Layer<L>> Layers newLayers(
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
     * Gets all values for <var>key</var> in accept order.
     *
     * @param key the key, never missing
     * @param <T> the value type
     *
     * @return the list of values for <var>key</var>, or empty list if none
     */
    public <T> List<T> getAll(final String key) {
        return layers.stream().
                map(Layer::changed).
                filter(m -> m.containsKey(key)).
                map(m -> (T) m.get(key)).
                collect(toList());
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
    public View<String, Object> accepted() {
        return View.of(cache);
    }

    /**
     * Gets a map view of the last accepted layer with <var>name</var>.
     *
     * @param name the layer name, never missing
     *
     * @return the layer or {@code null} if none
     */
    public View<String, Object> layer(final String name) {
        final Layer layer = names.get(name);
        return null == layer ? null : layer.changed();
    }

    /**
     * Creates a stream of layers in the same order as accepted.
     *
     * @param <L> the specialized type of layer
     *
     * @return the stream of layers, never missing
     *
     * @todo This is the wrong place to specialize type!  Layers is a
     * container, so &lt;L&rt; should be typed, not the accessor
     */
    public <L extends Layer<L>> Stream<L> history() {
        return layers.stream().
                map(l -> (L) l);
    }

    @Override
    public String toString() {
        return cache.toString();
    }

    private <L extends Layer> L newLayer(final Function<Surface, L> next) {
        return next.apply(new Surface());
    }

    @SuppressWarnings("unchecked")
    private <T> Field<T> fieldFor(final String key) {
        return fields.getOrDefault(key, LAST);
    }

    public final class Surface {
        @SuppressWarnings("unchecked")
        public void check(final String key, final Object value)
                throws ClassCastException {
            final Class<Object> type = fieldFor(key).type;
            if (!type.isAssignableFrom(value.getClass()))
                throw new ClassCastException(
                        format("Wrong type of value (%s vs %s) for '%s': %s",
                                type, value.getClass(), key, value));
        }

        public void accept(final String name, final Layer<?> layer) {
            layers.add(layer);
            names.put(name, layer);
            layer.changed().forEach(
                    (k, v) -> cache.put(k, fieldFor(k).apply(getAll(k))));
        }

        public View<String, Object> changed(final Layer<?> layer) {
            final Map<String, Object> changed = new HashMap<>(cache);
            layer.changed().forEach((k, v) -> {
                final List<Object> values = getAll(k);
                values.add(v);
                changed.put(k, fieldFor(k).apply(values));
            });
            return View.of(changed);
        }

        public Surface addAll(final Map<String, Field> fields) {
            fields.forEach(Layers.this::add);
            return this;
        }
    }
}
