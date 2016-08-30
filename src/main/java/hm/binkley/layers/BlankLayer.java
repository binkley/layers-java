package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.unmodifiableMap;

/**
 * {@code BlankLayer} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@RequiredArgsConstructor
@SuppressWarnings("WeakerAccess")
public class BlankLayer<L extends BlankLayer<L>>
        implements Layer<L> {
    private final Map<String, Object> map = new HashMap<>();
    private final Surface surface;
    private final String name;

    public static Function<Surface, BlankLayer> blankLayer(
            final String name) {
        return s -> new BlankLayer(s, name);
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final int size() {
        return map.size();
    }

    @Override
    public final boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public final L put(final String key, final Object value) {
        surface.check(key, value);
        map.put(key, value);
        return (L) this;
    }

    @Override
    public final Map<String, Object> changed() {
        return unmodifiableMap(map);
    }

    @Override
    public final Map<String, Object> whatIf() {
        return surface.changed(this);
    }

    @Override
    public <M extends Layer<M>> M accept(
            final Function<Surface, M> next,
            final Map<String, Field> fields) {
        surface.accept(name, this);
        return reject(next, fields);
    }

    @Override
    public final <M extends Layer<M>> M reject(
            final Function<Surface, M> next,
            final Map<String, Field> fields) {
        surface.addAll(fields);
        return next.apply(surface);
    }
}
