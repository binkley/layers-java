package hm.binkley.layers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;

/**
 * {@code Layers} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
public final class Layers {
    private final Collection<Layer> layers = new ConcurrentLinkedQueue<>();
    private final Map<String, Field> fields = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> cache
            = new ConcurrentHashMap<>();

    public Map<String, Object> committed() {
        return unmodifiableMap(cache);
    }

    public Stream<Map<String, Object>> history() {
        return layers.stream().
                map(Layer::changes);
    }

    public Layers add(final String key, final Field field) {
        fields.put(key, field);
        return this;
    }

    public <L extends Layer> L newLayer(final Function<Surface, L> ctor) {
        return ctor.apply(new Surface() {
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
        });
    }

    private BiFunction<Object, Object, Object> ruleFor(final String key) {
        return fields.getOrDefault(key, Field.LAST).rule;
    }

    private void merge(final Map<String, Object> that,
            final Map<String, Object> map) {
        map.entrySet().
                forEach(e -> mergeOne(that, e.getKey(), e.getValue()));
    }

    private void mergeOne(final Map<String, Object> map, final String key,
            final Object value) {
        map.merge(key, value, ruleFor(key));
    }
}
