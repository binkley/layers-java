package hm.binkley.layers.dnd.item;

public final class Weight
        extends Fraction<Weight> {
    public static final Weight WEIGHTLESS = inPounds(0);
    public static final Weight ONE_POUND = inPounds(1);

    public static Weight inPounds(final int pounds) {
        return asFraction(pounds, 1);
    }

    public static Weight asFraction(final int numerator,
            final int denominator) {
        return new Weight(numerator, denominator);
    }

    private Weight(final int numerator, final int denominator) {
        super(Weight::new, numerator, denominator);
    }

    @Override
    public String toString() {
        return super.toString() + "#";
    }
}
