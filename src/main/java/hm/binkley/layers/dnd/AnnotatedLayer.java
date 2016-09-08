package hm.binkley.layers.dnd;

import hm.binkley.layers.BlankLayer;
import hm.binkley.layers.Layers;

import java.time.ZonedDateTime;

import static java.lang.String.format;

/**
 * {@code AnnotatedLayer} <strong>needs documentation</strong>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation
 */
public class AnnotatedLayer<L extends AnnotatedLayer<L>>
        extends BlankLayer<L> {
    private final String notes;
    private ZonedDateTime accepted;

    protected AnnotatedLayer(final Layers.Surface surface, final String name,
            final String notes) {
        super(surface, name);
        this.notes = notes;
    }

    @Override
    protected void beforeAccept() {
        accepted = ZonedDateTime.now();
    }

    @Override
    public String toString() {
        return format("{notes=%s,accepted=%s,%s}", notes, accepted,
                changed());
    }

    public final String notes() {
        return notes;
    }

    public final ZonedDateTime accepted() {
        return accepted;
    }
}
