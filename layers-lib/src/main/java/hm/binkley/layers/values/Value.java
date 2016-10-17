package hm.binkley.layers.values;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.rules.Rule;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import static hm.binkley.layers.LayerSet.singleton;

public interface Value<T>
        extends BiFunction<Layers, Layer, T> {
    static Value<Set<Layer>> ofValue(final Layer value) {
        return new ValueOnly<>(singleton(value));
    }

    static <T> Value<T> ofValue(final T value) {
        return new ValueOnly<>(value);
    }

    static <T> Value<T> ofRule(final Rule<T> rule) {
        return new RuleOnly<>(rule);
    }

    static <T> Value<T> ofBoth(final T value, final Rule<T> rule) {
        return new Both<>(value, rule);
    }

    static Value<Set<Layer>> ofBoth(final Layer value,
            final Rule<Set<Layer>> rule) {
        return new Both<>(singleton(value), rule);
    }

    static <T> Value<T> mostRecent(final Object key, final T defaultValue) {
        return ofBoth(defaultValue, Rule.mostRecent(key));
    }

    static Value<Integer> sumAll(final Object key) {
        return ofRule(Rule.sumAll(key));
    }

    static Value<Integer> doubling(final Object key) {
        return ofRule(Rule.doubling(key));
    }

    static Value<Integer> floor(final Object key, final Integer minimum) {
        return ofBoth(minimum, Rule.floor(key));
    }

    Optional<T> value();

    Optional<Rule<T>> rule();

    @Override
    String toString();
}
