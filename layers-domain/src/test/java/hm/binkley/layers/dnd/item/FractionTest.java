package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FractionTest {
    @Test
    void shouldDiplayNicely() {
        assertEquals("0.7", new EgFraction(2, 3).toString());
    }

    @Test
    void shouldDiplayWitoutFractions() {
        assertEquals("1", new EgFraction(1, 1).toString());
    }

    @Test
    void shouldAdd() {
        assertEquals(new EgFraction(3, 2), new EgFraction(5, 6).
                add(new EgFraction(2, 3)));
    }

    @Test
    void shouldCompareLessThan() {
        assertTrue(0 > new EgFraction(2, 3).compareTo(new EgFraction(4, 3)));
    }

    @Test
    void shouldCompareEqualTo() {
        assertEquals(new EgFraction(2, 3).compareTo(new EgFraction(2, 3)), 0);
    }

    @Test
    void shouldCompareGreaterThan() {
        assertTrue(0 < new EgFraction(4, 3).compareTo(new EgFraction(2, 3)));
    }

    private static final class EgFraction
            extends Fraction<EgFraction> {
        private EgFraction(final int numerator, final int denominator) {
            super(EgFraction::new, numerator, denominator);
        }
    }
}
