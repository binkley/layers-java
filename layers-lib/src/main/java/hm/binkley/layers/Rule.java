package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Supplier;

import static java.lang.Integer.max;

@RequiredArgsConstructor
public abstract class Rule<T>
        implements RuleFunction<T> {
    private final String name;

    @Override
    public final String toString() {
        return "Rule: " + name;
    }

    @SuppressWarnings("WeakerAccess")
    public static <T> Rule<T> mostRecent(final Object key) {
        return new MostRecentRule<>(key);
    }

    @SuppressWarnings("WeakerAccess")
    public static Rule<Integer> sumAll(final Object key) {
        return new SumAllRule(key);
    }

    @SuppressWarnings("WeakerAccess")
    public static Rule<Integer> doubling(final Object key) {
        return new DoublingRule(key);
    }

    @SuppressWarnings("WeakerAccess")
    public static Rule<Integer> floor(final Object key) {
        return new FloorRule(key);
    }

    protected abstract static class KeyRule<T>
            extends Rule<T> {
        protected final Object key;

        protected KeyRule(final String name, final Object key) {
            super(name);
            this.key = key;
        }

        @Override
        public final boolean equals(final Object o) {
            if (this == o)
                return true;
            if (null == o || getClass() != o.getClass())
                return false;
            final KeyRule<?> that = (KeyRule<?>) o;
            return Objects.equals(key, that.key);
        }

        @Override
        public final int hashCode() {
            return Objects.hash(getClass(), key);
        }

        protected Supplier<NoSuchElementException> noValueForKey() {
            return () -> new NoSuchElementException(
                    "No value present for key: " + key);
        }
    }

    private static final class MostRecentRule<T>
            extends KeyRule<T> {
        private MostRecentRule(final Object key) {
            super("Most recent", key);
        }

        @Override
        public T apply(final Layers layers, final Layer layer,
                final T value) {
            return layers.<T>plainValuesFor(key).
                    findFirst().
                    orElseThrow(noValueForKey());
        }
    }

    private static final class SumAllRule
            extends KeyRule<Integer> {
        private SumAllRule(final Object key) {
            super("Sum all", key);
        }

        @Override
        public Integer apply(final Layers layers, final Layer layer,
                final Integer value) {
            return layers.<Integer>plainValuesFor(key).
                    mapToInt(Integer::intValue).
                    sum();
        }
    }

    private static final class DoublingRule
            extends KeyRule<Integer> {
        private DoublingRule(final Object key) {
            super("Doubling", key);
        }

        @Override
        public Integer apply(final Layers layers, final Layer layer,
                final Integer value) {
            return 2 * sumAll(key).apply(layers, layer, value);
        }
    }

    private static class FloorRule
            extends KeyRule<Integer> {
        private FloorRule(final Object key) {
            super("Floor", key);
        }

        @Override
        public Integer apply(final Layers layers, final Layer layer,
                final Integer value) {
            return max(value, layers.whatIfWithout(layer).get(key));
        }
    }
}
