package hm.binkley.layers;

import hm.binkley.layers.Field.StringField;

/**
 * {@code Character} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
public final class Character {
    private final Layers layers = new Layers();
    private Layer current;

    {
        layers.add("player", new StringField());
        layers.add("name", new StringField());
    }

    public Character(final String player, final String name) {
        current = layers.
                newLayer(surface -> new Base(surface, player, name)).
                commit(BlankLayer::new);
    }

    public static final class Base
            extends BlankLayer {
        public Base(final Surface surface, final String player,
                final String name) {
            super(surface);
            put("player", player);
            put("name", name);
        }
    }
}
