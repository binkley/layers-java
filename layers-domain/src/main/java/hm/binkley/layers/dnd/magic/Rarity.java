package hm.binkley.layers.dnd.magic;

public enum Rarity {
    UNCOMMON("Uncommon"), RARE("Rare"), VERY_RARE("Very rare"),
    LEGENDARY("Legendary");

    private final String display;

    Rarity(final String display) {
        this.display = display;
    }

    @Override
    public final String toString() {
        return display;
    }
}
