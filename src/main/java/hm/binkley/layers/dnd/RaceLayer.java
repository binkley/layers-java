package hm.binkley.layers.dnd;

import hm.binkley.layers.Field;
import hm.binkley.layers.Field.StringField;
import hm.binkley.layers.Layers.Surface;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static hm.binkley.layers.Field.IntegerField.additiveIntegerField;
import static hm.binkley.layers.dnd.AbilityScore.values;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;

/**
 * {@code RaceLayer} <strong>needs documentation</strong>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation
 */
public abstract class RaceLayer<L extends RaceLayer<L>>
        extends AnnotatedLayer<L> {
    public static final Map<String, Field> fields;

    public static final String RACE_NAME = "race:name";
    public static final String RACE_STR_BONUS = "race:STR-bonus";
    public static final String RACE_DEX_BONUS = "race:DEX-bonus";
    public static final String RACE_CON_BONUS = "race:CON-bonus";
    public static final String RACE_INT_BONUS = "race:INT-bonus";
    public static final String RACE_WIS_BONUS = "race:WIS-bonus";
    public static final String RACE_CHA_BONUS = "race:CHA-bonus";

    static {
        final Map<String, Field> map = new HashMap<>();
        map.put(RACE_NAME, new StringField());
        map.put(RACE_STR_BONUS, additiveIntegerField());
        map.put(RACE_DEX_BONUS, additiveIntegerField());
        map.put(RACE_CON_BONUS, additiveIntegerField());
        map.put(RACE_INT_BONUS, additiveIntegerField());
        map.put(RACE_WIS_BONUS, additiveIntegerField());
        map.put(RACE_CHA_BONUS, additiveIntegerField());
        fields = unmodifiableMap(map);
    }

    protected RaceLayer(final Surface surface, final String notes,
            final String name,
            final EnumMap<AbilityScore, Integer> statBonuses) {
        super(surface, "Race", notes);
        put("race:name", name);
        for (final AbilityScore stat : values()) {
            final Integer score = statBonuses.getOrDefault(stat, 0);
            put(format("stats:%s", stat), score);
            put(format("race:%s-bonus", stat), score);
        }
    }
}
