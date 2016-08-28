package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.util.Collections.unmodifiableMap;

/**
 * {@code BlankLayer} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@RequiredArgsConstructor
public class BlankLayer
        implements Layer {
    private final Map<String, Object> map = new ConcurrentHashMap<>();
    private final Surface surface;

    @Override
    public Map<String, Object> changes() {
        return unmodifiableMap(map);
    }

    public Map<String, Object> whatIf() {
        return surface.changes(this);
    }

    public Layer put(final String key, final Object value) {
        map.put(key, value);
        return this;
    }

    public Layer commit(final Function<Surface, Layer> ctor) {
        surface.commit(this);
        return ctor.apply(surface);
    }

    public Layer replace(final Function<Surface, Layer> ctor) {
        return ctor.apply(surface);
    }
}
