package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.XEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static hm.binkley.layers.rules.Rule.doubling;
import static hm.binkley.layers.values.Value.ofValue;
import static java.util.Collections.unmodifiableList;

public class Proficiencies
        extends XEnum<Proficiencies> {
    private static final AtomicInteger ordinal = new AtomicInteger();
    private static final List<Proficiencies> values = new ArrayList<>();
    public static final Proficiencies ACROBATICS = new Proficiencies(
            "ACROBATICS", "Acrobatics");
    public static final Proficiencies ATHLETICS = new Proficiencies(
            "ATHLETICS", "Athletics");

    public static List<Proficiencies> values() {
        return unmodifiableList(values);
    }

    private final String display;

    protected Proficiencies(final String name, final String display) {
        super(name, ordinal.getAndIncrement());
        this.display = display;
        values.add(this);
    }

    @Override
    public final String toString() {
        return display;
    }

    public static LayerMaker<Layer> proficiencyBonus(
            final Proficiencies proficiency, final int bonus) {
        return layers -> {
            final Layer layer = new Layer(layers, "Proficiency bonus(es)");
            layer.put(proficiency, ofValue(bonus));
            return layer;
        };
    }

    public static LayerMaker<Layer> doubleProficiency(
            final Proficiencies proficiency) {
        return layers -> {
            final Layer layer = new Layer(layers,
                    "Proficiency bonus doubling");
            layer.put(proficiency, doubling(proficiency));
            return layer;
        };
    }
}
