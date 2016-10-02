package hm.binkley.layers.dnd;

import hm.binkley.layers.DoubleSumKey;
import hm.binkley.layers.Key;
import hm.binkley.layers.SumAllKey;

import java.util.HashMap;
import java.util.Map;

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

    public static Map<Key, Object> proficiencyBonus(
            final ProficiencyBonus proficiency, final int bonus) {
        final Map<Key, Object> layer = new HashMap<>();
        layer.put(new SumAllKey(proficiency), bonus);
        return layer;
    }

    public static Map<Key, Object> doubleProficiency(
            final ProficiencyBonus proficiency) {
        final Map<Key, Object> layer = new HashMap<>();
        layer.put(new DoubleSumKey(proficiency), 0);
        return layer;
    }
}
