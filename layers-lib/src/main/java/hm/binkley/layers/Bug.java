package hm.binkley.layers;

public final class Bug
        extends RuntimeException {
    public Bug(final String message) {
        super("BUG: " + message);
    }
}
