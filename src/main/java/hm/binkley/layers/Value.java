package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Function;

/** @todo Replace with specialized types based on ctor; make this interface */
@RequiredArgsConstructor
public final class Value<T>
        implements Function<Layers, T> {
    private final T value;
    private final Rule<T> rule;

    @Override
    public T apply(final Layers layers) {
        return rule.apply(layers, value);
    }

    public static Value<Integer> sumAll(final Object key) {
        return new Value<>(null, Rule.sumAll(key));
    }

    public static Value<Integer> doubling(final Object key) {
        return new Value<>(null, Rule.doubling(key));
    }

    public static <T> Value<T> mostRecent(final Object key) {
        return new Value<>(null, Rule.mostRecent(key));
    }

    public Optional<T> value() {
        return Optional.ofNullable(value);
    }

    public Optional<Rule<T>> rule() {
        return Optional.ofNullable(rule);
    }

    public static <T> Value<T> ofValue(final T value) {
        return new Value<>(value, null);
    }

    public static <T> Value<T> ofRule(final Rule<T> rule) {
        return new Value<>(null, rule);
    }

    public static <T> Value<T> ofBoth(final T value, final Rule<T> rule) {
        return new Value<>(value, rule);
    }
}
