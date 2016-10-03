package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.Value;

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

    public static Layer proficiencyBonus(final Layers layers,
            final ProficiencyBonus proficiency, final int bonus) {
        final Layer layer = layers.newLayer(Layer::new);
        layer.put(proficiency, ofValue(bonus));
        return layer;
    }

    public static Layer doubleProficiency(final Layers layers,
            final ProficiencyBonus proficiency) {
        final Layer layer = layers.newLayer(Layer::new);
        layer.put(proficiency, Value.doubling(proficiency));
        return layer;
    }

    public static Layer defaultRuleProficiencyBonuses(final Layers layers) {
        final Layer layer = layers.newLayer(Layer::new);
        for (final ProficiencyBonus proficiency : ProficiencyBonus.values())
            layer.put(proficiency, Value.sumAll(proficiency));
        return layer;
    }
}
