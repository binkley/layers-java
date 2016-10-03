package hm.binkley.layers;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Function;

@EqualsAndHashCode
@RequiredArgsConstructor
public final class Value<T>
        implements Function<Layers, T> {
    private final T value;
    private final Rule<T> rule;

    public static <T> Value<T> ofValue(final T value) {
        return new Value<>(value, null);
    }

    public static <T> Value<T> ofRule(final Rule<T> rule) {
        return new Value<>(null, rule);
    }

    public static <T> Value<T> ofBoth(final T value, final Rule<T> rule) {
        return new Value<>(value, rule);
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

    @Override
    public T apply(final Layers layers) {
        return rule.apply(layers, value);
    }

    public Optional<T> value() {
        return Optional.ofNullable(value);
    }

    public Optional<Rule<T>> rule() {
        return Optional.ofNullable(rule);
    }

    @Override
    public String toString() {
        if (null == rule)
            return value.toString();
        else if (null == value)
            return "{" + rule + "}";
        else
            return "{" + value + ", " + rule + "}";
    }
}
