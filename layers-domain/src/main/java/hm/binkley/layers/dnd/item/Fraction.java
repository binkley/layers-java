package hm.binkley.layers.dnd.item;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

import static java.lang.Math.abs;
import static java.math.RoundingMode.HALF_UP;

@EqualsAndHashCode(of = {"numerator", "denominator"})
abstract class Fraction<F extends Fraction<F>>
        implements Comparable<F> {
    private final FractionMaker<F> ctor;
    protected final int numerator;
    protected final int denominator;

    @FunctionalInterface
    interface FractionMaker<F extends Fraction<F>> {
        F apply(int numerator, int denominator);
    }

    protected Fraction(final FractionMaker<F> ctor, final int numerator,
            final int denominator) {
        if (0 > numerator || 1 > denominator)
            throw new IllegalArgumentException();
        final int gcm = gcm(numerator, denominator);
        this.ctor = ctor;
        this.numerator = abs(numerator / gcm);
        this.denominator = abs(denominator / gcm);
    }

    public final F add(final F that) {
        final int numerator = this.numerator * that.denominator
                + that.numerator * denominator;
        final int denominator = this.denominator * that.denominator;
        return ctor.apply(numerator, denominator);
    }

    @Override
    public final int compareTo(final F that) {
        return Integer.compare(numerator * that.denominator,
                that.numerator * denominator);
    }

    @Override
    public String toString() {
        return BigDecimal.valueOf(numerator).
                divide(BigDecimal.valueOf(denominator), 1, HALF_UP).
                stripTrailingZeros().
                toString();
    }

    private static int gcm(final int numerator, final int denominator) {
        return 0 == denominator ? numerator
                : gcm(denominator, numerator % denominator);
    }
}
