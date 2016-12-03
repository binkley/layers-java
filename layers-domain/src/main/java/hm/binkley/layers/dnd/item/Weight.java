package hm.binkley.layers.dnd.item;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import static java.lang.Math.abs;
import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public final class Weight {
    public static final Weight WEIGHTLESS = inPounds(0);
    public static final Weight ONE_POUND = inPounds(1);

    private final int numerator;
    private final int denominator;

    public static Weight inPounds(final int pounds) {
        return asFraction(pounds, 1);
    }

    public static Weight asFraction(final int numerator,
            final int denominator) {
        if (numerator < 0 || denominator < 1)
            throw new IllegalArgumentException();
        final int gcm = gcm(numerator, denominator);
        return new Weight(abs(numerator / gcm), abs(denominator / gcm));
    }

    public Weight add(final Weight that) {
        final int numerator = this.numerator * that.denominator
                + that.numerator * this.denominator;
        final int denominator = this.denominator * that.denominator;
        return asFraction(numerator, denominator);
    }

    @Override
    public String toString() {
        return 1 == denominator ? (numerator + "#")
                : (numerator + "/" + denominator + "#");
    }

    private static int gcm(final int numerator, final int denominator) {
        return 0 == denominator ? numerator
                : gcm(denominator, numerator % denominator);
    }
}
