package hm.binkley.layers.dnd;

import hm.binkley.layers.Field;
import hm.binkley.layers.Field.StringField;
import hm.binkley.layers.Layers.Surface;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.unmodifiableMap;

/**
 * {@code CharacterLayer} <strong>needs documentation</strong>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation
 */
public final class CharacterLayer
        extends AnnotatedLayer<CharacterLayer> {
    public static final String CHARACTER_NAME = "character:name";

    public static final Map<String, Field> fields;

    static {
        final Map<String, Field> map = new HashMap<>();
        map.put(CHARACTER_NAME, new StringField());
        fields = unmodifiableMap(map);
    }

    public static Function<Surface, CharacterLayer> characterLayer(
            final String notes, final String name) {
        return s -> new CharacterLayer(s, notes, name);
    }

    private CharacterLayer(final Surface surface, final String notes,
            final String name) {
        super(surface, "Character", notes);
        put(CHARACTER_NAME, name);
    }
}
