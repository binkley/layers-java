package hm.binkley.layers;

import hm.binkley.layers.Field.StringField;
import hm.binkley.layers.Layers.NewLayers;

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
        final NewLayers<BaseLayer> initial = newLayers(
                surface -> new BaseLayer(surface, player, name));
        layers = initial.layers;
        layers.addRule("player", new StringField());
        layers.addRule("name", new StringField());
        current = initial.firstLayer.commit(BlankLayer::new);
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
