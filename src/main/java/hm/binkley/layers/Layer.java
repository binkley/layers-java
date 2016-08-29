package hm.binkley.layers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * {@code Layer} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@SuppressWarnings("WeakerAccess")
public interface Layer {
    int size();

    boolean isEmpty();

    /** @todo How to indicate covariant return to subtype? */
    Layer put(final String key, final Object value);

    Map<String, Object> changed();

    Map<String, Object> whatIf();

    <L extends Layer> L accept(final Function<Surface, L> next,
            final Stream<Entry<String, Field>> fields);

    <L extends Layer> L reject(final Function<Surface, L> next,
            final Stream<Entry<String, Field>> fields);

    default <L extends Layer> L accept(final Function<Surface, L> next) {
        return accept(next, Stream.empty());
    }

    default <L extends Layer> L reject(final Function<Surface, L> next) {
        return reject(next, Stream.empty());
    }
}
