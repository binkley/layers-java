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

    public Character(final String player, final String name) {
        layers = newLayers(surface -> new BaseLayer(surface, player, name),
                l -> current = l, BaseLayer.fields.entrySet().stream());
        current = current.accept(BlankLayer::new);
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
}
