package hm.binkley.layers;

import hm.binkley.layers.Field.StringField;

import java.util.HashMap;
import java.util.Map;

import static hm.binkley.layers.Layers.newLayers;

/**
 * {@code Character} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
public final class Character {
    private final Layers layers;
    private Layer current;

    public Character(final String player, final String name,
            final String raceName) {
        layers = newLayers(surface -> new BaseLayer(surface, player, name),
                l -> current = l, BaseLayer.fields.entrySet().stream());
        current = current.accept(s -> new RaceLayer(s, raceName),
                RaceLayer.fields.entrySet().stream());
    }

    public static final class BaseLayer
            extends BlankLayer {
        private static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put("player", new StringField());
            fields.put("name", new StringField());
        }

        public BaseLayer(final Surface surface, final String player,
                final String name) {
            super(surface);
            put("player", player);
            put("name", name);
        }
    }

    public static final class RaceLayer
            extends BlankLayer {
        private static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put("race:name", new StringField());
        }

        public RaceLayer(final Surface surface, final String raceName) {
            super(surface);
            put("race:name", raceName);
        }
    }
}
