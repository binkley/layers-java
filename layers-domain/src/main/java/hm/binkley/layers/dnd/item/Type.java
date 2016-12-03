package hm.binkley.layers.dnd.item;

/** @todo Figure out the right organization */
public enum Type {
    EQUIPMENT("Equipment"),
    ARMOR("Armor"),
    WONDROUS_ITEM("Wondrous Item");

    private final String display;

    Type(final String display) {
        this.display = display;
    }

    @Override
    public final String toString() {
        return display;
    }
}
