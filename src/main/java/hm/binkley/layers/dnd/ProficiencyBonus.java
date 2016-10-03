package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.Value;

import java.util.function.Function;

import static hm.binkley.layers.Value.ofValue;

public enum ProficiencyBonus {
    ACROBATICS("Acrobatics"),
    ATHLETICS("Athletics");

    private final String display;

    ProficiencyBonus(final String display) {
        this.display = display;
    }

    @Override
    public final String toString() {
        return display;
    }

    public static Function<Layers, Layer> proficiencyBonus(
            final ProficiencyBonus proficiency, final int bonus) {
        return layers -> {
            final Layer layer = new Layer(layers);
            layer.put(proficiency, ofValue(bonus));
            return layer;
        };
    }

    public static Function<Layers, Layer> doubleProficiency(
            final ProficiencyBonus proficiency) {
        return layers -> {
            final Layer layer = new Layer(layers);
            layer.put(proficiency, Value.doubling(proficiency));
            return layer;
        };
    }

    public static Layer baseRuleProficiencyBonuses(final Layers layers) {
        final Layer layer = layers.newLayer(Layer::new);
        for (final ProficiencyBonus proficiency : ProficiencyBonus.values())
            layer.put(proficiency, Value.sumAll(proficiency));
        return layer;
    }
}
