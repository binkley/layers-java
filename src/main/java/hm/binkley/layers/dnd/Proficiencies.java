package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.Value;

import java.util.function.Function;

import static hm.binkley.layers.Value.ofValue;

public enum Proficiencies {
    ACROBATICS("Acrobatics"),
    ATHLETICS("Athletics");

    private final String display;

    Proficiencies(final String display) {
        this.display = display;
    }

    @Override
    public final String toString() {
        return display;
    }

    public static Function<Surface, Layer> proficiencyBonus(
            final Proficiencies proficiency, final int bonus) {
        return layers -> {
            final Layer layer = new Layer(layers, "Proficiency bonus(es)");
            layer.put(proficiency, ofValue(bonus));
            return layer;
        };
    }

    public static Function<Surface, Layer> doubleProficiency(
            final Proficiencies proficiency) {
        return layers -> {
            final Layer layer = new Layer(layers,
                    "Proficiency bonus doubling");
            layer.put(proficiency, Value.doubling(proficiency));
            return layer;
        };
    }

    public static Layer baseRuleProficiencyBonuses(final Surface layers) {
        final Layer layer = new Layer(layers, "Base proficiency bonus rules");
        for (final Proficiencies proficiency : Proficiencies.values())
            layer.put(proficiency, Value.sumAll(proficiency));
        return layer;
    }
}
