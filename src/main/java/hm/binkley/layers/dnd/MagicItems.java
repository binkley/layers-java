package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.Rule;
import hm.binkley.layers.Value;

import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.MagicItems.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.MagicItems.Rarity.RARE;
import static hm.binkley.layers.dnd.MagicItems.Rarity.VERY_RARE;

public final class MagicItems {
    private MagicItems() {}

    public enum Rarity {
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

    private static Layer beltOfGiantStrength(final Surface layers,
            final String giantKind, final int strength, final Rarity rarity) {
        final Layer layer = new Layer(layers,
                "Belt of " + giantKind + " Giant Strength");
        layer.put(STR, Value.ofBoth(strength, Rule.exactly()));
        layer.put(Rarity.class.getSimpleName(), Value.ofValue(rarity));
        return layer;
    }
}
