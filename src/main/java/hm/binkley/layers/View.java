package hm.binkley.layers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;

/**
 * {@code View} is an unmodifiable view of a {@code Map} without methods which
 * would otherwise need to throw {@code UnsupportedOperationException}, and
 * using fully utilizing generics rather than legacy {@code Object}.
 *
 * @author <a href="mailto:boxley@thoughtworks.com">Brian Oxley</a>
 * @todo Needs documentation
 * @todo Better name
 */
public interface View<K, V> {
    int size();

    default boolean isEmpty() {
        return 0 == size();
    }

    V get(final K key);

    boolean containsKey(final K key);

    boolean containsValue(final V value);

    Stream<Entry<K, V>> entries();

    void forEach(final BiConsumer<? super K, ? super V> action);

    static <K, V> View<K, V> of(final Map<K, V> map) {
        return new View<K, V>() {
            @Override
            public int size() {
                return map.size();
            }

            @Override
            public V get(final K key) {
                return map.get(key);
            }

            @Override
            public boolean containsKey(final K key) {
                return map.containsKey(key);
            }

            @Override
            public boolean containsValue(final V value) {
                return map.containsValue(value);
            }

            @Override
            public Stream<Entry<K, V>> entries() {
                return stream(map.entrySet().spliterator(), false);
            }

            public void forEach(
                    final BiConsumer<? super K, ? super V> action) {
                map.forEach(action);
            }
        };
    }
}
