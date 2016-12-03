package hm.binkley.layers.dnd.item;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

import static java.lang.Math.abs;
import static java.math.RoundingMode.FLOOR;

@EqualsAndHashCode(of = {"numerator", "denominator"})
abstract class Fraction<F extends Fraction<F>> {
    private final FractionMaker<F> ctor;
    protected final int numerator;
    protected final int denominator;

    @FunctionalInterface
    interface FractionMaker<F extends Fraction<F>> {
        F apply(int numerator, int denominator);
    }

    protected Fraction(final FractionMaker<F> ctor, final int numerator,
            final int denominator) {
        if (numerator < 0 || denominator < 1)
            throw new IllegalArgumentException();
        final int gcm = gcm(numerator, denominator);
        this.ctor = ctor;
        this.numerator = abs(numerator / gcm);
        this.denominator = abs(denominator / gcm);
    }

    public F add(final F that) {
        final int numerator = this.numerator * that.denominator
                + that.numerator * this.denominator;
        final int denominator = this.denominator * that.denominator;
        return ctor.apply(numerator, denominator);
    }

    @Override
    public String toString() {
        return BigDecimal.valueOf(numerator).
                divide(BigDecimal.valueOf(denominator), 1, FLOOR).
                toString();
    }

    private static int gcm(final int numerator, final int denominator) {
        return 0 == denominator ? numerator
                : gcm(denominator, numerator % denominator);
    }
}
