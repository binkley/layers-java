package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.Abilities.DEX;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.abilityScoreIncrease;

public final class Races {
    private Races() {}

    public static Layer plainHuman(final LayerSurface layers) {
        final Layer layer = new Layer(layers, "Human");
        Abilities.values().
                forEach(score -> layer.put(score, 1));
        return layer;
    }

    public static HumanVariant humanVariant() {
        return new HumanVariant();
    }

    public static final class HumanVariant {
        public WithSTR withSTR() {
            return new WithSTR();
        }

        public static final class WithSTR {
            public LayerMaker<Layer> withDEX() {
                return withDoubleAbilities(STR, DEX);
            }

            public LayerMaker<Layer> withCON() {
                return withDoubleAbilities(STR, CON);
            }

            private WithSTR() {}
        }

        private HumanVariant() {}
    }

    private static LayerMaker<Layer> withDoubleAbilities(
            final Abilities ability1, final Abilities ability2) {
        return layers -> {
            final Layer layer = new Layer(layers, "Variant Human");
            layer.blend(abilityScoreIncrease(ability1, ability2));
            return layer;
        };
    }
}
