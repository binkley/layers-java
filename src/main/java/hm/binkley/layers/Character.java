package hm.binkley.layers;

import hm.binkley.layers.Field.StringField;

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
                l -> current = l);
        layers.addRule("player", new StringField());
        layers.addRule("name", new StringField());
        current = current.accept(BlankLayer::new);
    }

    public static final class BaseLayer
            extends BlankLayer {
        public BaseLayer(final Surface surface, final String player,
                final String name) {
            super(surface);
            put("player", player);
            put("name", name);
        }
    }
}
