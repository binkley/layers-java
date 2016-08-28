package hm.binkley.layers;

import java.util.Map;
import java.util.function.Function;

/**
 * {@code Layer} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
public interface Layer {
    Map<String, Object> changed();

    Map<String, Object> whatIf();

    Layer put(final String key, final Object value);

    <L extends Layer> L accept(final Function<Surface, L> next);

    <L extends Layer> L reject(final Function<Surface, L> next);
}
