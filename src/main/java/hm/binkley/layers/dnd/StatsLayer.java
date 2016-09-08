package hm.binkley.layers.dnd;

import hm.binkley.layers.Field;
import hm.binkley.layers.Layers.Surface;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static hm.binkley.layers.Field.IntegerField.additiveIntegerField;
import static java.util.Collections.unmodifiableMap;

/**
 * {@code StatsLayer} <strong>needs documentation</strong>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation
 */
public final class StatsLayer
        extends AnnotatedLayer<StatsLayer> {
    public static final String STATS_STR = "stats:STR";
    public static final String STATS_DEX = "stats:DEX";
    public static final String STATS_CON = "stats:CON";
    public static final String STATS_INT = "stats:INT";
    public static final String STATS_WIS = "stats:WIS";
    public static final String STATS_CHA = "stats:CHA";

    public static final Map<String, Field> fields;

    static {
        final Map<String, Field> map = new HashMap<>();
        map.put(STATS_STR, additiveIntegerField());
        map.put(STATS_DEX, additiveIntegerField());
        map.put(STATS_CON, additiveIntegerField());
        map.put(STATS_INT, additiveIntegerField());
        map.put(STATS_WIS, additiveIntegerField());
        map.put(STATS_CHA, additiveIntegerField());
        fields = unmodifiableMap(map);
    }

    public static Function<Surface, StatsLayer> statsLayer(final String notes,
            final int STR, final int DEX, final int CON, final int INT,
            final int WIS, final int CHA) {
        return s -> new StatsLayer(s, notes, STR, DEX, CON, INT, WIS, CHA);
    }

    private StatsLayer(final Surface surface, final String notes,
            final int STR, final int DEX, final int CON, final int INT,
            final int WIS, final int CHA) {
        super(surface, "Stats", notes);
        put(STATS_STR, STR);
        put(STATS_DEX, DEX);
        put(STATS_CON, CON);
        put(STATS_INT, INT);
        put(STATS_WIS, WIS);
        put(STATS_CHA, CHA);
    }
}
