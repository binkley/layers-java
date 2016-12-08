package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.XEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static hm.binkley.layers.rules.Rule.doubling;
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

    public static Function<LayerSurface, ProficienciesLayer> proficiencyBonus(
            final Proficiencies proficiency, final int bonus) {
        return layers -> new ProficienciesLayer(layers,
                "Proficiency bonus(es)").
                put(proficiency, bonus);
    }

    public static Function<LayerSurface, ProficienciesLayer>
    doubleProficiency(
            final Proficiencies proficiency) {
        return layers -> new ProficienciesLayer(layers,
                "Proficiency bonus doubling").
                put(proficiency, doubling());
    }

    public static final class ProficienciesLayer
            extends Layer<ProficienciesLayer> {
        private ProficienciesLayer(final LayerSurface layers,
                final String name) {
            super(layers, name);
        }
    }
}
