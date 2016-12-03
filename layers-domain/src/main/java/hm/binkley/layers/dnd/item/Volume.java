package hm.binkley.layers.dnd.item;

public final class Volume
        extends Fraction<Volume> {
    public static final Volume SPACELESS = inCuft(0);
    public static final Volume ONE_CUBIC_FOOT = inCuft(1);

    public static Volume inCuft(final int cuft) {
        return asFraction(cuft, 1);
    }

    public static Volume asFraction(final int numerator,
            final int denominator) {
        return new Volume(numerator, denominator);
    }

    private Volume(final int numerator, final int denominator) {
        super(Volume::new, numerator, denominator);
    }

    @Override
    public String toString() {
        return super.toString() + " cu.ft.";
    }
}
