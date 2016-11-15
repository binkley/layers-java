package hm.binkley.layers.values;

import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.rules.Rule;

import java.util.Optional;
import java.util.function.Function;

public interface Value<T, R>
        extends Function<RuleSurface<T>, R> {
    static <T, R> Value<T, R> ofValue(final T value) {
        return new ValueOnly<>(value);
    }

    static <T, R> Value<T, R> ofRule(final Rule<T, R> rule) {
        return new RuleOnly<>(rule);
    }

    /** @todo Get rid of {@code ofBoth}. */
    static <T, R> Value<T, R> ofBoth(final T value, final Rule<T, R> rule) {
        return new Both<>(value, rule);
    }

    Optional<T> value();

    Optional<Rule<T, R>> rule();

    @Override
    String toString();
}
