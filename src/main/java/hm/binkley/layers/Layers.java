package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;
import static lombok.AccessLevel.PRIVATE;

/**
 * {@code Layers} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@RequiredArgsConstructor(access = PRIVATE)
public final class Layers {
    private final Collection<Layer> layers = new ArrayList<>();
    private final Map<String, Field> fields = new HashMap<>();
    private final Map<String, Object> cache = new HashMap<>();

    public static <L extends Layer> Layers newLayers(
            final Function<Surface, L> next, final Consumer<L> firstLayer) {
        return newLayers(next, firstLayer, Stream.empty());
    }

    public static <L extends Layer> Layers newLayers(
            final Function<Surface, L> next, final Consumer<L> firstLayer,
            final Stream<Entry<String, Field>> fields) {
        final Layers layers = new Layers();
        fields.forEach(e -> layers.fields.put(e.getKey(), e.getValue()));
        firstLayer.accept(layers.newLayer(next));
        return layers;
    }

    public <T> Layers add(final String key, final Field<T> field) {
        fields.put(key, field);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> Field<T> fieldFor(final String key) {
        return fields.getOrDefault(key, Field.LAST);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) cache.get(key);
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
            merge(cache, layer.changed());
        }

        @Override
        public Map<String, Object> changed(final Layer layer) {
            final Map<String, Object> changed = new HashMap<>(cache);
            merge(changed, layer.changed());
            return unmodifiableMap(changed);
        }

        @Override
        public Surface add(final String key, final Field field) {
            fields.put(key, field);
            return this;
        }

        private void merge(final Map<String, Object> accepted,
                final Map<String, Object> changed) {
            changed.entrySet().
                    forEach(e -> mergeOne(accepted, e.getKey(),
                            e.getValue()));
        }

        private void mergeOne(final Map<String, Object> map, final String key,
                final Object value) {
            map.merge(key, value, fieldFor(key));
        }
    }
}
