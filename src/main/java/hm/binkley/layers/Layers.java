package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
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
    private final Collection<Layer> layers = new ConcurrentLinkedQueue<>();
    private final Map<String, Field> fields = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> cache
            = new ConcurrentHashMap<>();

    @RequiredArgsConstructor
    public static final class NewLayers<L extends Layer> {
        public final Layers layers;
        public final L firstLayer;
    }

    public static <L extends Layer> NewLayers<L> newLayers(
            final Function<Surface, L> ctor) {
        final Layers layers = new Layers();
        return new NewLayers<>(layers, layers.newLayer(ctor));
    }

    public <T> Layers addRule(final String key, final Field<T> field) {
        fields.put(key, field);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> Field<T> ruleFor(final String key) {
        return fields.getOrDefault(key, Field.LAST);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) cache.get(key);
    }

    public Map<String, Object> committed() {
        return unmodifiableMap(cache);
    }

    public Stream<Map<String, Object>> history() {
        return layers.stream().
                map(Layer::changes);
    }

    private <L extends Layer> L newLayer(final Function<Surface, L> ctor) {
        return ctor.apply(new LayersSurface());
    }

    private final class LayersSurface
            implements Surface {
        @Override
        public void commit(final Layer layer) {
            layers.add(layer);
            merge(cache, layer.changes());
        }

        @Override
        public Map<String, Object> changes(final Layer layer) {
            final Map<String, Object> whatIf = new HashMap<>(cache);
            merge(whatIf, layer.changes());
            return unmodifiableMap(whatIf);
        }

        private void merge(final Map<String, Object> that,
                final Map<String, Object> map) {
            map.entrySet().
                    forEach(e -> mergeOne(that, e.getKey(), e.getValue()));
        }

        private void mergeOne(final Map<String, Object> map, final String key,
                final Object value) {
            map.merge(key, value, ruleFor(key).rule);
        }
    }
}
