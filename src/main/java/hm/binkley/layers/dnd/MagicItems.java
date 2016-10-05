package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.Rule;
import hm.binkley.layers.Value;

import java.util.Map;

import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.MagicItems.Attunement.ATTUNED;
import static hm.binkley.layers.dnd.MagicItems.Attunement.UNATTUNED;
import static hm.binkley.layers.dnd.MagicItems.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.MagicItems.Rarity.RARE;
import static hm.binkley.layers.dnd.MagicItems.Rarity.UNCOMMON;
import static hm.binkley.layers.dnd.MagicItems.Rarity.VERY_RARE;
import static hm.binkley.layers.dnd.MagicItems.Type.ARMOR;
import static hm.binkley.layers.dnd.MagicItems.Type.WONDROUS_ITEM;

/**
 * @todo Does description belong in {@code} Layer?
 * @todo Giant girdle broken: should only apply when STR not higher
 */
public final class MagicItems {
    private MagicItems() {}

    public static class MagicItem
            extends Layer {
        public MagicItem(final Surface layers, final String name,
                final String description, final Type type,
                final Rarity rarity, final Attunement attunement,
                final String notes) {
            super(layers, name);
            final Map<Object, Object> details = details();
            details.put("Description", description);
            details.put(Type.class.getSimpleName(), type);
            details.put(Rarity.class.getSimpleName(), rarity);
            details.put(Attunement.class.getSimpleName(), attunement);
            details.put("Notes", notes);
        }

        public MagicItem(final Surface layers, final String name,
                final String description, final Type type,
                final Rarity rarity, final Attunement attunement) {
            super(layers, name);
            final Map<Object, Object> details = details();
            details.put("Description", description);
            details.put(Type.class.getSimpleName(), type);
            details.put(Rarity.class.getSimpleName(), rarity);
            details.put(Attunement.class.getSimpleName(), attunement);
        }
    }

    public enum Type {
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

    public enum Rarity {
        UNCOMMON("Uncommon"),
        RARE("Rare"),
        VERY_RARE("Very rare"),
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

    public static Layer adamantineArmor(final Surface layers) {
        return new MagicItem(layers, "Adamantine Armor",
                "This suit of armor is reinforced with adamantine, one of "
                        + "the hardest substances in existence. While "
                        + "you're wearing it, any critical hit against you "
                        + "becomes a normal hit.", ARMOR, UNCOMMON, UNATTUNED,
                "medium or heavy, but not hide");
    }

    public static Layer beltOfHillGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Hill", 21, RARE);
    }

    public static Layer beltOfStoneGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Stone", 23, VERY_RARE);
    }

    public static Layer beltOfFrostGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Frost", 23, VERY_RARE);
    }

    public static Layer beltOfFireGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Fire", 25, VERY_RARE);
    }

    public static Layer beltOfCloudGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Cloud", 27, LEGENDARY);
    }

    public static Layer beltOfStormGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Storm", 29, LEGENDARY);
    }

    /** @todo Uses broken rule; see GitHub #6. */
    private static Layer beltOfGiantStrength(final Surface layers,
            final String giantKind, final int strength, final Rarity rarity) {
        final Layer layer = new MagicItem(layers,
                "Belt of " + giantKind + " Giant Strength",
                "While wearing this belt, your Strength score changes to a "
                        + "score granted by the belt. If your Strength is "
                        + "already equal to or greater than the belt's "
                        + "score, the item has no effect on you.\n"
                        + "Six varieties of this belt exist, corresponding "
                        + "with and having rarity according to the six "
                        + "kinds of true giants. The belt of stone giant "
                        + "strength and the belt of frost giant strength "
                        + "look different, but they have the same effect.",
                WONDROUS_ITEM, rarity, ATTUNED);
        layer.put(STR, Value.ofBoth(strength, Rule.exactly()));
        return layer;
    }
}
