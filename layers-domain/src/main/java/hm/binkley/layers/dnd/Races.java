package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.LayerSurface;

import java.util.function.Function;

import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.Abilities.DEX;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.abilityScoreIncrease;

public final class Races {
    private Races() {}

    public static HumanLayer plainHuman(final LayerSurface layers) {
        final HumanLayer layer = new HumanLayer(layers, "Human");
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
            public Function<LayerSurface, HumanLayer> withDEX() {
                return withDoubleAbilities(STR, DEX);
            }

            public Function<LayerSurface, HumanLayer> withCON() {
                return withDoubleAbilities(STR, CON);
            }

            private WithSTR() {}
        }

        private HumanVariant() {}
    }

    private static Function<LayerSurface, HumanLayer> withDoubleAbilities(
            final Abilities ability1, final Abilities ability2) {
        return layers -> {
            final HumanLayer layer = new HumanLayer(layers, "Variant Human");
            layer.blend(abilityScoreIncrease(ability1, ability2));
            return layer;
        };
    }

    public static final class HumanLayer
            extends RaceLayer<HumanLayer> {
        private HumanLayer(final LayerSurface layers, final String name) {
            super(layers, name);
        }
    }

    public static abstract class RaceLayer<L extends RaceLayer<L>>
            extends Layer<L> {
        protected RaceLayer(final LayerSurface layers, final String name) {
            super(layers, name);
        }
    }
}
