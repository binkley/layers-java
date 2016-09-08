package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * {@code View} is an unmodifiable view of a {@code Map} without methods which
 * would otherwise throw {@code UnsupportedOperationException}, and using
 * fully utilizing generics rather than legacy {@code Object}.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation
 */
@RequiredArgsConstructor(staticName = "of")
public final class MapView<K, V> {
    private final Map<K, V> map;

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public int size() {
        return map.size();
    }

    public V get(final K key) {
        return map.get(key);
    }

    public boolean containsKey(final K key) {
        return map.containsKey(key);
    }

    public boolean containsValue(final V value) {
        return map.containsValue(value);
    }

    public Stream<Entry<K, V>> entries() {
        return map.entrySet().stream();
    }

    public void forEach(final BiConsumer<? super K, ? super V> action) {
        map.forEach(action);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
