package hm.binkley.layers.dnd;

import hm.binkley.layers.Field;
import hm.binkley.layers.Field.StringField;
import hm.binkley.layers.Layers.Surface;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.unmodifiableMap;

/**
 * {@code PlayerLayer} <strong>needs documentation</strong>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation
 */
public final class PlayerLayer
        extends AnnotatedLayer<PlayerLayer> {
    public static final String PLAYER_NAME = "player:name";
    public static final String PLAYER_CAMPAIGN = "player:campaign";

    public static final Map<String, Field> fields;

    static {
        final Map<String, Field> map = new HashMap<>();
        map.put(PLAYER_NAME, new StringField());
        map.put(PLAYER_CAMPAIGN, new StringField());
        fields = unmodifiableMap(map);
    }

    public static Function<Surface, PlayerLayer> playerLayer(
            final String notes, final String name, final String campaign) {
        return s -> new PlayerLayer(s, notes, name, campaign);
    }

    private PlayerLayer(final Surface surface, final String notes,
            final String name, final String campaign) {
        super(surface, "Player", notes);
        put(PLAYER_NAME, name);
        put(PLAYER_CAMPAIGN, campaign);
    }
}
