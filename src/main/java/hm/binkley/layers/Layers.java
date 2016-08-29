package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

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
    private final Collection<Layer> layers = new ArrayList<>();
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
        layers.fields.putAll(fields);
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

    public <T> Layers add(final String key, final Field<T> field) {
        fields.put(key, field);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> Field<T> fieldFor(final String key) {
        return fields.getOrDefault(key, Field.LAST);
    }

    public Map<String, Object> accepted() {
        return unmodifiableMap(cache);
    }

    public Stream<Map<String, Object>> history() {
        return layers.stream().
                map(Layer::changed);
    }

    private <L extends Layer> L newLayer(final Function<Surface, L> next) {
        return next.apply(new LayersSurface());
    }

    private final class LayersSurface
            implements Surface {
        @Override
        public void accept(final Layer layer) {
            layers.add(layer);
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
            Layers.this.fields.putAll(fields);
            return this;
        }
    }
}
