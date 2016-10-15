package hm.binkley.layers.dnd;

import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.values.Value;

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

/**
 * @todo Does description belong in {@code Layer}?
 * @todo Builder for shared details
 */
public final class MagicItems {
    private MagicItems() {}

    public enum Type {
        ARMOR("Armor"), WONDROUS_ITEM("Wondrous Item");

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

    public enum Attunement {
        UNATTUNED(""), ATTUNED("yes");

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
    public static MagicItem adamantineArmor(final Surface layers) {
        return new MagicItem(layers, "Adamantine Armor",
                "This suit of armor is reinforced with adamantine, one of "
                        + "the hardest substances in existence. While "
                        + "you're wearing it, any critical hit against you "
                        + "becomes a normal hit.", ARMOR, UNCOMMON, UNATTUNED,
                "medium or heavy, but not hide");
    }

    @SuppressWarnings("WeakerAccess")
    public static MagicItem amuletOfHealth(final Surface layers) {
        final MagicItem layer = new MagicItem(layers, "Amulet of Health",
                "Your Constitution score is 19 while you wear this amulet. "
                        + "It has no effect on you if your Constitution is "
                        + "already 19 or higher.", WONDROUS_ITEM, RARE,
                ATTUNED);
        layer.put(CON, Value.floor(CON, 19));
        return layer;
    }

    @SuppressWarnings("WeakerAccess")
    public static MagicItem beltOfHillGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Hill", RARE, 21);
    }

    @SuppressWarnings("WeakerAccess")
    public static MagicItem beltOfStoneGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Stone", VERY_RARE, 23);
    }

    @SuppressWarnings("WeakerAccess")
    public static MagicItem beltOfFrostGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Frost", VERY_RARE, 23);
    }

    @SuppressWarnings("WeakerAccess")
    public static MagicItem beltOfFireGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Fire", VERY_RARE, 25);
    }

    @SuppressWarnings("WeakerAccess")
    public static MagicItem beltOfCloudGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Cloud", LEGENDARY, 27);
    }

    @SuppressWarnings("WeakerAccess")
    public static MagicItem beltOfStormGiantStrength(final Surface layers) {
        return beltOfGiantStrength(layers, "Storm", LEGENDARY, 29);
    }

    private static MagicItem beltOfGiantStrength(final Surface layers,
            final String giantKind, final Rarity rarity, final int strength) {
        final MagicItem layer = new MagicItem(layers,
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
        layer.put(STR, Value.floor(STR, strength));
        return layer;
    }
}
