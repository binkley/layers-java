package hm.binkley.layers;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public interface LayerView {
    String name();

    boolean isEmpty();

    int size();

    Set<Object> keys(); // TODO: Unmodifiable

    boolean containsKey(final Object key);

    <T> Value<T> get(final Object key);

    Stream<Map.Entry<Object, Object>> stream();

    Map<Object, Object> toMap(); // TODO: Unmodifiable

    Map<Object, Object> details(); // TODO: Unmodifiable
}
