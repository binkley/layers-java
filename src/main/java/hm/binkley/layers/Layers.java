package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;

/**
 * {@code Layers} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
public final class Layers {
    @RequiredArgsConstructor
    public static final class Field<T> {
        private static final Field<Object> LAST = new Field<>(Object.class,
                (a, b) -> b);
        public final Class<T> type;
        public final BiFunction<T, T, T> rule;
    }

    private final Collection<Layer> layers = new ConcurrentLinkedQueue<>();
    private final Map<String, Field> fields = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> cache
            = new ConcurrentHashMap<>();

    public Map<String, Object> view() {
        return unmodifiableMap(cache);
    }

    public Stream<Map<String, Object>> stream() {
        return layers.stream().map(Layer::view);
    }

    public Layers add(final String key, final Field field) {
        fields.put(key, field);
        return this;
    }

    public final class Layer {
        private final Map<String, Object> map = new ConcurrentHashMap<>();

        public Map<String, Object> view() {
            return unmodifiableMap(map);
        }

        public Layer put(final String key, final Object value) {
            map.put(key, value);
            return this;
        }

        public Layer commit() {
            layers.add(this);
            map.entrySet().forEach(e -> cache.merge(e.getKey(), e.getValue(),
                    fields.getOrDefault(e.getKey(), Field.LAST).rule));
            return new Layer();
        }

        public Layer rollback() {
            return new Layer();
        }
    }
}
