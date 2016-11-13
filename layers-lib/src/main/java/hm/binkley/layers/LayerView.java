package hm.binkley.layers;

import hm.binkley.layers.values.Value;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

/** @todo Unable to specifiy unmodifiable with plain JDK. */
public interface LayerView {
    String name();

    boolean isEmpty();

    int size();

    Set<Object> keys();

    boolean containsKey(final Object key);

    <T, R> Value<T, R> get(final Object key);

    Stream<Entry<Object, Object>> stream();

    Map<Object, Object> toMap();

    Map<Object, Object> details();
}
