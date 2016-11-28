package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.XEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.unmodifiableList;

public class Characters
        extends XEnum<Characters> {
    private static final AtomicInteger ordinal = new AtomicInteger();
    private static final List<Characters> values = new ArrayList<>();

    public static final Characters NAME = new Characters("NAME");

    public static List<Characters> values() {
        return unmodifiableList(values);
    }

    protected Characters(final String name) {
        super(name, ordinal.getAndIncrement());
        values.add(this);
    }

    public static LayerMaker<CharactersLayer> characterDescription(
            final String name) {
        return layers -> new CharactersLayer(layers).
                put(NAME, name);
    }

    public static final class CharactersLayer
            extends Layer<CharactersLayer> {
        private CharactersLayer(final LayerSurface layers) {
            super(layers, "Character description");
        }
    }
}
