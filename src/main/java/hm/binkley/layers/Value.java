package hm.binkley.layers;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Function;

public interface Value<T>
        extends Function<Layers, T> {
    static <T> Value<T> ofValue(final T value) {
        return new ValueOnly<>(value);
    }

    static <T> Value<T> ofRule(final Rule<T> rule) {
        return new RuleOnly<>(rule);
    }

    static <T> Value<T> ofBoth(final T value, final Rule<T> rule) {
        return new Both<>(value, rule);
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

    static Value<Integer> floor(final Layer layer, final Object key,
            final Integer minimum) {
        return ofBoth(minimum, Rule.floor(layer, key));
    }

    Optional<T> value();

    Optional<Rule<T>> rule();

    @Override
    String toString();

    @EqualsAndHashCode
    @RequiredArgsConstructor
    final class ValueOnly<T>
            implements Value<T> {
        private final T value;

        @Override
        public Optional<T> value() {
            return Optional.of(value);
        }

        @Override
        public Optional<Rule<T>> rule() {
            return Optional.empty();
        }

        @Override
        public T apply(final Layers layers) {
            throw new NullPointerException("Missing rule for value: " + this);
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor
    final class RuleOnly<T>
            implements Value<T> {
        private final Rule<T> rule;

        @Override
        public Optional<T> value() {
            return Optional.empty();
        }

        @Override
        public Optional<Rule<T>> rule() {
            return Optional.of(rule);
        }

        /** @todo Rethink rule-only apply with {@code null} */
        @Override
        public T apply(final Layers layers) {
            return rule.apply(layers, null);
        }

        @Override
        public String toString() {
            return "{" + rule + "}";
        }
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor
    final class Both<T>
            implements Value<T> {
        private final T value;
        private final Rule<T> rule;

        @Override
        public Optional<T> value() {
            return Optional.of(value);
        }

        @Override
        public Optional<Rule<T>> rule() {
            return Optional.of(rule);
        }

        @Override
        public T apply(final Layers layers) {
            return rule.apply(layers, value);
        }

        @Override
        public String toString() {
            return "{" + value + ", " + rule + "}";
        }
    }
}
