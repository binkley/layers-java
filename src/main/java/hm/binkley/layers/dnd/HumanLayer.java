package hm.binkley.layers.dnd;

import hm.binkley.layers.Layers.Surface;

import java.util.EnumMap;
import java.util.function.Function;

import static hm.binkley.layers.dnd.AbilityScore.values;

/**
 * {@code HumanLayer} <strong>needs documentation</strong>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation
 */
public final class HumanLayer
        extends RaceLayer<HumanLayer> {
    private static final EnumMap<AbilityScore, Integer> statBonuses
            = new EnumMap<>(AbilityScore.class);

    static {
        for (final AbilityScore stat : values())
            statBonuses.put(stat, 1);
    }

    public static Function<Surface, HumanLayer> humanLayer(
            final String notes) {
        return s -> new HumanLayer(s, notes);
    }

    private HumanLayer(final Surface surface, final String notes) {
        super(surface, notes, "Human", statBonuses);
    }
}
