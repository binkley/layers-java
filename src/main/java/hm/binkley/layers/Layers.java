package hm.binkley.layers;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
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
    private final ListMultimap<String, Layer> layers = LinkedListMultimap
            .create();
    private final Map<String, Field> fields = new HashMap<>();
    private final Map<String, Object> cache = new HashMap<>();

    public static <L extends Layer> Layers newLayers(
            final Function<Surface, L> next, final Consumer<L> firstLayer) {
        return newLayers(next, firstLayer, emptyMap());
    }

    public static <L extends Layer> Layers newLayers(
            final Function<Surface, L> next, final Consumer<L> firstLayer,
            final Map<String, Field> fields) {
        final Layers layers = new Layers();
        fields.forEach(layers::add);
        firstLayer.accept(layers.newLayer(next));
        return layers;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) cache.get(key);
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public int size() {
        return cache.size();
    }

    public Layers add(final String key, final Field field) {
        if (cache.containsKey(key))
            throw new IllegalStateException(
                    format("Cannot add field for pre-existing key: %s", key));
        fields.put(key, field);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> Field<T> fieldFor(final String key) {
        return fields.getOrDefault(key, LAST);
    }

    public Map<String, Object> accepted() {
        return unmodifiableMap(cache);
    }

    public Stream<Entry<String, Map<String, Object>>> history() {
        return layers.entries().stream().
                map(e -> new SimpleImmutableEntry<>(e.getKey(),
                        e.getValue().changed()));
    }

    @Override
    public String toString() {
        return cache.toString();
    }

    private <L extends Layer> L newLayer(final Function<Surface, L> next) {
        return next.apply(new LayersSurface());
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
            layers.put(name, layer);
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
