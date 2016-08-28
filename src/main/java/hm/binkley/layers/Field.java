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
public abstract class Field<T> {
    public static final Field<Object> LAST = new Field<Object>(Object.class,
            (a, b) -> b) {};
    public final Class<T> type;
    public final BiFunction<T, T, T> rule;

    public static final class StringField
            extends Field<String> {
        public StringField() {
            super(String.class, (a, b) -> b);
        }
    }

    public static final class IntField
            extends Field<Integer> {
        public IntField(final BiFunction<Integer, Integer, Integer> rule) {
            super(Integer.class, rule);
        }
    }

    public static final class DoubleField
            extends Field<Double> {
        public DoubleField(final BiFunction<Double, Double, Double> rule) {
            super(Double.class, rule);
        }
    }

    public static final class EnumField<E extends Enum<E>>
            extends Field<E> {
        public EnumField(final Class<E> type) {
            super(type, (a, b) -> b);
        }
    }

    public static final class CollectionField<T>
            extends Field<Collection<T>> {
        public CollectionField(final Class<Collection<T>> type,
                final Supplier<? extends Collection<T>> ctor) {
            super(type, (a, b) -> {
                final Collection<T> all = ctor.get();
                all.addAll(a);
                all.addAll(b);
                return all;
            });
        }
    }
}
