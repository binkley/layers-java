package hm.binkley.layers.values;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.rules.LayerSetRule;
import hm.binkley.layers.rules.Rule;
import hm.binkley.layers.set.FullnessFunction;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

public interface Value<T, R>
        extends BiFunction<Layers, Layer, R> {
    static <T, R> Value<T, R> ofValue(final T value) {
        return new ValueOnly<>(value);
    }

    static <T, R> Value<T, R> ofRule(final Rule<T, R> rule) {
        return new RuleOnly<>(rule);
    }

    static <T, R> Value<T, R> ofBoth(final T value, final Rule<T, R> rule) {
        return new Both<>(value, rule);
    }

    static <T> Value<T, T> mostRecent(final Object key,
            final T initialValue) {
        return ofBoth(initialValue, Rule.mostRecent(key));
    }

    static Value<Integer, Integer> sumAll(final Object key) {
        return ofRule(Rule.sumAll(key));
    }

    static Value<Integer, Integer> sumAll(final Object key,
            final int initialValue) {
        return ofBoth(initialValue, Rule.sumAll(key));
    }

    static Value<Integer, Integer> doubling(final Object key) {
        return ofRule(Rule.doubling(key));
    }

    static Value<Integer, Integer> doubling(final Object key,
            final int initialValue) {
        return ofBoth(initialValue, Rule.doubling(key));
    }

    static Value<Integer, Integer> floor(final Object key,
            final Integer minimum) {
        return ofBoth(minimum, Rule.floor(key));
    }

    static <L extends Layer> Value<L, Set<L>> layerSet(final Object key,
            final FullnessFunction<L> full) {
        return ofRule(new LayerSetRule<>(key, full));
    }

    static <L extends Layer> Value<L, Set<L>> layerSet(final Object key,
            final L initialValue, final FullnessFunction<L> full) {
        return ofBoth(initialValue, new LayerSetRule<>(key, full));
    }

    Optional<T> value();

    Optional<Rule<T, R>> rule();

    @Override
    String toString();
}
