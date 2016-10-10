package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.Value;

import java.util.Map;

import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.MagicItems.Attunement.ATTUNED;
import static hm.binkley.layers.dnd.MagicItems.Attunement.UNATTUNED;
import static hm.binkley.layers.dnd.MagicItems.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.MagicItems.Rarity.RARE;
import static hm.binkley.layers.dnd.MagicItems.Rarity.UNCOMMON;
import static hm.binkley.layers.dnd.MagicItems.Rarity.VERY_RARE;
import static hm.binkley.layers.dnd.MagicItems.Type.ARMOR;
import static hm.binkley.layers.dnd.MagicItems.Type.WONDROUS_ITEM;

/** @todo Does description belong in {@code Layer}? */
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

    @SuppressWarnings("WeakerAccess")
    public static Layer adamantineArmor(final Surface layers) {
        return new MagicItem(layers, "Adamantine Armor",
                "This suit of armor is reinforced with adamantine, one of "
                        + "the hardest substances in existence. While "
                        + "you're wearing it, any critical hit against you "
                        + "becomes a normal hit.", ARMOR, UNCOMMON, UNATTUNED,
                "medium or heavy, but not hide");
    }

    @SuppressWarnings("WeakerAccess")
    public static Layer amuletOfHealth(final Surface layers) {
        final Layer layer = new MagicItem(layers, "Amulet of Health",
                "Your Constitution score is 19 while you wear this amulet. It has no effect on you if your Constitution is already 19 or higher.",
                WONDROUS_ITEM, RARE, ATTUNED);
        layer.put(CON, Value.floor(CON, 19));
        return layer;
    }

    @SuppressWarnings("WeakerAccess")
    public static Layer beltOfHillGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Hill", RARE, 21);
    }

    @SuppressWarnings("WeakerAccess")
    public static Layer beltOfStoneGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Stone", VERY_RARE, 23);
    }

    @SuppressWarnings("WeakerAccess")
    public static Layer beltOfFrostGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Frost", VERY_RARE, 23);
    }

    @SuppressWarnings("WeakerAccess")
    public static Layer beltOfFireGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Fire", VERY_RARE, 25);
    }

    @SuppressWarnings("WeakerAccess")
    public static Layer beltOfCloudGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Cloud", LEGENDARY, 27);
    }

    @SuppressWarnings("WeakerAccess")
    public static Layer beltOfStormGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Storm", LEGENDARY, 29);
    }

    private static Layer beltOfGiantStrength(final Surface layers,
            final String giantKind, final Rarity rarity, final int strength) {
        final String name = "Belt of " + giantKind + " Giant Strength";
        final Layer layer = new MagicItem(layers, name,
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
        layer.put(STR, Value.floor(STR, strength));
        return layer;
    }
}
