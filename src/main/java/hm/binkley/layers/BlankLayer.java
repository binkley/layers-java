package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;

/**
 * {@code BlankLayer} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@RequiredArgsConstructor
@SuppressWarnings("WeakerAccess")
public class BlankLayer
        implements Layer {
    private final Map<String, Object> map = new HashMap<>();
    private final Surface surface;

    @Override
    public final int size() {
        return map.size();
    }

    @Override
    public final boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public final Layer put(final String key, final Object value) {
        map.put(key, value);
        return this;
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
    public final <L extends Layer> L accept(final Function<Surface, L> next,
            final Stream<Entry<String, Field>> fields) {
        surface.accept(this);
        return reject(next, fields);
    }

    @Override
    public final <L extends Layer> L reject(final Function<Surface, L> next,
            final Stream<Entry<String, Field>> fields) {
        fields.forEach(e -> surface.add(e.getKey(), e.getValue()));
        return next.apply(surface);
    }
}
