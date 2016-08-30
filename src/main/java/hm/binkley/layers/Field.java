package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * {@code Field} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@RequiredArgsConstructor
@SuppressWarnings("WeakerAccess")
public class Field<T>
        implements BiFunction<T, T, T> {
    public static <T> T last(final T a, final T b) {
        return b;
    }

    public static final Field<Object> LAST = new Field<>(Object.class,
            Field::last);
    public final Class<T> type;
    private final BiFunction<T, T, T> rule;

    @Override
    public final T apply(final T oldValue, final T newValue) {
        return rule.apply(oldValue, newValue);
    }

    public static final class StringField
            extends Field<String> {
        public StringField() {
            super(String.class, Field::last);
        }
    }

    public static final class IntegerField
            extends Field<Integer> {
        private static final IntegerField ADDITATIVE = new IntegerField(
                (a, b) -> a + b);

        public static IntegerField additiveIntegerField() {
            return ADDITATIVE;
        }

        public IntegerField(
                final BiFunction<Integer, Integer, Integer> rule) {
            super(Integer.class, rule);
        }
    }

    public static final class DoubleField
            extends Field<Double> {
        public DoubleField(final BiFunction<Double, Double, Double> rule) {
            super(Double.class, rule);
        }
    }

    public static final class CollectionField
            extends Field<Collection> {
        public CollectionField(final Supplier<Collection> merged) {
            super(Collection.class, (a, b) -> {
                final Collection all = merged.get();
                all.addAll(a);
                all.addAll(b);
                return all;
            });
        }
    }
}
