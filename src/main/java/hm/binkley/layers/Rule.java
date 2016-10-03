package hm.binkley.layers;

import java.util.function.BiFunction;

public interface Rule<T>
        extends BiFunction<Layers, T, T> {
    static <T> Rule<T> mostRecent(final Object key) {
        return (layers, value) -> layers.<T>valuesFor(key).
                findFirst().
                get();
    }

    static Rule<Integer> sumAll(final Object key) {
        return (layers, value) -> layers.<Integer>valuesFor(key).
                mapToInt(Integer::intValue).
                sum();
    }

    static Rule<Integer> doubling(final Object key) {
        return (layers, value) -> 2 * sumAll(key).apply(layers, value);
    }

    static <T> Rule<T> exactly() {
        return (layers, value) -> value;
    }
}
