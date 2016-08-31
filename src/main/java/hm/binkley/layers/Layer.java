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
public interface Layer<L extends Layer<L>> {
    String name();

    int size();

    boolean isEmpty();

    L put(final String key, final Object value);

    Map<String, Object> changed();

    Map<String, Object> whatIf();

    <M extends Layer<M>> M accept(final Function<Surface, M> next,
            final Map<String, Field> fields);

    default <M extends Layer<M>> M accept(final Function<Surface, M> next) {
        return accept(next, emptyMap());
    }

    <M extends Layer<M>> M reject(final Function<Surface, M> next);
}
