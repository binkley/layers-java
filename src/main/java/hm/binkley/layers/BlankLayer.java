package hm.binkley.layers;

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
@SuppressWarnings("WeakerAccess")
public class BlankLayer<L extends BlankLayer<L>>
        implements Layer<L> {
    private final Map<String, Object> map = new HashMap<>();
    private Surface surface;
    private final String name;

    public static Function<Surface, BlankLayer> blankLayer(
            final String name) {
        return s -> new BlankLayer(s, name);
    }

    protected BlankLayer(final Surface surface, final String name) {
        this.surface = surface;
        this.name = name;
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
        checkAccepted();
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

    protected void beforeAccept() {}

    @Override
    public final <M extends Layer<M>> M accept(
            final Function<Surface, M> next,
            final Map<String, Field> fields) {
        checkAccepted();
        beforeAccept();
        final Surface surface = this.surface;
        this.surface = null;
        surface.accept(name, this);
        surface.addAll(fields);
        return next.apply(surface);
    }

    @Override
    public final <M extends Layer<M>> M reject(
            final Function<Surface, M> next) {
        checkAccepted();
        final Surface surface = this.surface;
        this.surface = null;
        return next.apply(surface);
    }

    private void checkAccepted() {
        if (null == surface)
            throw new IllegalStateException("Already accepted or rejected");
    }
}
