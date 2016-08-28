package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;
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
    public abstract static class Field<T> {
        private static final Field<Object> LAST = new Field<Object>(
                Object.class, (a, b) -> b) {};
        public final Class<T> type;
        public final BiFunction<T, T, T> rule;
    }

    public static final class StringField
            extends Field<String> {
        public StringField() {
            super(String.class, (a, b) -> b);
        }
    }

    public static final class IntField
            extends Field<Integer> {
        public IntField(final BiFunction<Integer, Integer, Integer> rule) {
            super(Integer.class, rule);
        }
    }

    public static final class DoubleField
            extends Field<Double> {
        public DoubleField(final BiFunction<Double, Double, Double> rule) {
            super(Double.class, rule);
        }
    }

    public static final class EnumField<E extends Enum<E>>
            extends Field<E> {
        public EnumField(final Class<E> type) {
            super(type, (a, b) -> b);
        }
    }

    public static final class CollectionField<T>
            extends Field<Collection<T>> {
        public CollectionField(final Class<Collection<T>> type,
                final Supplier<? extends Collection<T>> ctor) {
            super(type, (a, b) -> {
                final Collection<T> all = ctor.get();
                all.addAll(a);
                all.addAll(b);
                return all;
            });
        }
    }

    private final Collection<Layer> layers = new ConcurrentLinkedQueue<>();
    private final Map<String, Field> fields = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> cache
            = new ConcurrentHashMap<>();

    public Map<String, Object> view() {
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

    public final class Layer {
        private final Map<String, Object> map = new ConcurrentHashMap<>();

        public Map<String, Object> changes() {
            return unmodifiableMap(map);
        }

        public Map<String, Object> whatIf() {
            final Map<String, Object> whatIf = new HashMap<>(cache);
            updateAll(whatIf);
            return unmodifiableMap(whatIf);
        }

        public Layer put(final String key, final Object value) {
            map.put(key, value);
            return this;
        }

        public Layer commit() {
            layers.add(this);
            updateAll(cache);
            return new Layer();
        }

        private void updateAll(final Map<String, Object> that) {
            map.entrySet().
                    forEach(e -> updateOne(that, e.getKey(), e.getValue()));
        }

        private BiFunction<Object, Object, Object> ruleFor(final String key) {
            return fields.getOrDefault(key, Field.LAST).rule;
        }

        private void updateOne(final Map<String, Object> map,
                final String key, final Object value) {
            map.merge(key, value, ruleFor(key));
        }
    }
}
