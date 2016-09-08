package hm.binkley.layers.dnd;

import hm.binkley.layers.Layers;

import java.util.function.Function;

/**
 * {@code FreeFormLayer} <strong>needs documentation</strong>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation
 */
public final class FreeFormLayer
        extends AnnotatedLayer<FreeFormLayer> {
    public static Function<Layers.Surface, FreeFormLayer> freeFormLayer(
            final String name, final String notes) {
        return s -> new FreeFormLayer(s, name, notes);
    }

    private FreeFormLayer(final Layers.Surface surface, final String name,
            final String notes) {
        super(surface, name, notes);
    }
}
