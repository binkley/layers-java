package hm.binkley.layers;

import hm.binkley.layers.Layers.Layer;
import hm.binkley.layers.Layers.StringField;

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
        final Layer base = layers.new Layer();
        base.put("player", player);
        base.put("name", name);
        current = base.commit();
    }
}
