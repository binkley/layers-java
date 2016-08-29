package hm.binkley.layers;

import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.emptyMap;

/**
 * {@code Layer} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@SuppressWarnings("WeakerAccess")
public interface Layer {
    String name();

    int size();

    boolean isEmpty();

    /** @todo How to indicate covariant return to subtype? */
    Layer put(final String key, final Object value);

    Map<String, Object> changed();

    Map<String, Object> whatIf();

    <L extends Layer> L accept(final Function<Surface, L> next,
            final Map<String, Field> fields);

    <L extends Layer> L reject(final Function<Surface, L> next,
            final Map<String, Field> fields);

    default <L extends Layer> L accept(final Function<Surface, L> next) {
        return accept(next, emptyMap());
    }

    default <L extends Layer> L reject(final Function<Surface, L> next) {
        return reject(next, emptyMap());
    }
}
