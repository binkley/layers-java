package hm.binkley.layers;

import java.util.Map;

/**
 * {@code Surface} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@SuppressWarnings("WeakerAccess")
public interface Surface {
    void check(final String key, final Object value)
        throws ClassCastException;

    void accept(final Layer layer);

    Map<String, Object> changed(final Layer layer);

    Surface addAll(final Map<String, Field> fields);
}
