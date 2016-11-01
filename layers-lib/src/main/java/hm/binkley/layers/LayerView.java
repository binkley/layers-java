package hm.binkley.layers;

import hm.binkley.layers.values.Value;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

public interface LayerView {
    String name();

    boolean isEmpty();

    int size();

    Set<Object> keys(); // TODO: Unmodifiable

    boolean containsKey(final Object key);

    <T, R> Value<T, R> get(final Object key);

    Stream<Entry<Object, Object>> stream();

    Map<Object, Object> toMap(); // TODO: Unmodifiable

    Map<Object, Object> details(); // TODO: Unmodifiable
}
