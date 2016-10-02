package hm.binkley.layers.dnd;

import hm.binkley.layers.DoubleSumKey;
import hm.binkley.layers.Layer;
import hm.binkley.layers.SumAllKey;

public enum ProficiencyBonus {
    ACROBATICS("Acrobatics"), ATHLETICS("Athletics");

    private final String display;

    ProficiencyBonus(final String display) {
        this.display = display;
    }

    @Override
    public final String toString() {
        return display;
    }

    public static Layer proficiencyBonus(final ProficiencyBonus proficiency,
            final int bonus) {
        final Layer layer = new Layer();
        layer.put(new SumAllKey(proficiency), bonus);
        return layer;
    }

    public static Layer doubleProficiency(
            final ProficiencyBonus proficiency) {
        final Layer layer = new Layer();
        layer.put(new DoubleSumKey(proficiency), 0);
        return layer;
    }
}
