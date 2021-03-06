package hm.binkley.layers;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static hm.binkley.layers.XEnumTest.Eg.A;
import static hm.binkley.layers.XEnumTest.Eg.B;
import static hm.binkley.layers.XEnumTest.Other.X;
import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XEnumTest {
    @Test
    void shouldBeNamedAfterThemselves() {
        assertAll(() -> assertEquals("A", A.name()),
                () -> assertEquals("B", B.name()));
    }

    @Test
    void shouldOrdinateStepwiseFromZero() {
        assertAll(() -> assertEquals(0, A.ordinal()),
                () -> assertEquals(1, B.ordinal()));
    }

    @Test
    void shouldIterateInOrdinalOrder() {
        assertEquals(asList(A, B), Eg.values());
    }

    @Test
    void shouldCompareInDeclarationOrder() {
        final List<Eg> values = asList(B, A);
        sort(values);

        assertEquals(Eg.values(), values);
    }

    @Test
    void shouldHaveRightDeclaringClass() {
        assertEquals(Eg.class, A.getDeclaringClass());
    }

    @SuppressWarnings("unchecked")
    @Test
    void shouldWhineWhenComparingDifferentEnumTypes() {
        assertThrows(ClassCastException.class,
                () -> ((XEnum) A).compareTo(X));
    }

    @Test
    void shouldDefaultToStringToName() {
        assertEquals(A.name(), A.toString());
    }

    @Test
    void shouldEschewClone() {
        assertThrows(CloneNotSupportedException.class, A::clone);
    }

    @Test
    void shouldDelegateToSystemHashCode() {
        assertEquals(System.identityHashCode(A), A.hashCode());
    }

    static final class Eg
            extends XEnum<Eg> {
        private static final AtomicInteger ordinal = new AtomicInteger();
        private static final List<Eg> values = new ArrayList<>();
        static final Eg A = new Eg("A");
        static final Eg B = new Eg("B");

        static List<Eg> values() {
            return unmodifiableList(values);
        }

        private Eg(final String name) {
            super(name, ordinal.getAndIncrement());
            values.add(this);
        }
    }

    static final class Other
            extends XEnum<Other> {
        static final Other X = new Other("X", 0);

        private Other(final String name, final int ordinal) {
            super(name, ordinal);
        }
    }
}
