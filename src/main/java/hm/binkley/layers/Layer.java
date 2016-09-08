package hm.binkley.layers;

import hm.binkley.layers.Layers.Surface;

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
    /**
     * Gets the name of this layer.
     *
     * @return the later name
     *
     * @todo Can the name be {@code null}?
     */
    String name();

    /**
     * Gets the count of entries in this layer.
     *
     * @return the entry count, never negative
     */
    int size();

    /**
     * Checks if this layer has no entries.
     *
     * @return {@code true} if the layer has no entries
     */
    boolean isEmpty();

    /**
     * Adds an entry and returns the layer to ease method call chaining.
     *
     * @param key the entry name, never missing
     * @param value the entry value, never missing
     *
     * @return the layer, never missing
     */
    L put(final String key, final Object value);

    /**
     * Creates an unmodifiable view of entries for this layer.
     *
     * @return a map of entries, never missing
     */
    MapView<String, Object> changed();

    /**
     * Creates an unmodifiable view of {@code Layers} as if this layer were
     * accepted.
     *
     * @return a map of all entries, never missing
     *
     * @todo Does this belong on {@code Layers} rather than {@code Layer}? The
     * thinking is for code to be handed a {@code Layer} without reference to
     * {@code Layers}, and to use {@code whatIf()} to show speculative
     * changes
     */
    MapView<String, Object> whatIf();

    /**
     * Accepts this layer and adds the <var>next</var> one, and adds
     * fields to {@link Layers} to ease method call chaining.
     *
     * @param next creates the next layer, never missing
     * @param <M> the type of the next layer
     *
     * @return the next layer, never missing
     *
     * @see Layers#add(String, Field)
     */
    <M extends Layer<M>> M accept(final Function<Surface, M> next,
            final Map<String, Field> fields);

    /**
     * Accepts this layer and adds the <var>next</var> one.
     *
     * @param next creates the next layer, never missing
     * @param <M> the type of the next layer
     *
     * @return the next layer, never missing
     */
    default <M extends Layer<M>> M accept(final Function<Surface, M> next) {
        return accept(next, emptyMap());
    }

    /**
     * Rejects this layer and adds the <var>replacement</var> one.
     *
     * @param replacement creates the replacement layer, never missing
     * @param <M> the type of the replacement layer
     *
     * @return the replacement layer, never missing
     */
    <M extends Layer<M>> M reject(final Function<Surface, M> replacement);
}
