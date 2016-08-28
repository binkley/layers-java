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
    public Map<String, Object> changed() {
        return unmodifiableMap(map);
    }

    @Override
    public Map<String, Object> whatIf() {
        return surface.changed(this);
    }

    @Override
    public Layer put(final String key, final Object value) {
        map.put(key, value);
        return this;
    }

    @Override
    public <L extends Layer> L accept(final Function<Surface, L> next) {
        surface.accept(this);
        return reject(next);
    }

    @Override
    public <L extends Layer> L reject(final Function<Surface, L> next) {
        return next.apply(surface);
    }
}
