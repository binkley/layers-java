package hm.binkley.layers.dnd.item;

public enum Attunement {
    UNATTUNED(""),
    ATTUNED("yes");

    private final String display;

    Attunement(final String display) {
        this.display = display;
    }

    @Override
    public final String toString() {
        return display;
    }
}
