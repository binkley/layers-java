package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;
import static lombok.AccessLevel.PRIVATE;

/**
 * {@code Layers} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@RequiredArgsConstructor(access = PRIVATE)
public final class Layers {
    private final Collection<Layer> layers = new ArrayList<>();
    private final Map<String, Field> fields = new HashMap<>();
    private final Map<String, Object> cache = new HashMap<>();

    public static <L extends Layer> Layers newLayers(
            final Function<Surface, L> next, final Consumer<L> firstLayer) {
        final Layers layers = new Layers();
        firstLayer.accept(layers.newLayer(next));
        return layers;
    }

    public <T> Layers addRule(final String key, final Field<T> field) {
        fields.put(key, field);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> Field<T> ruleFor(final String key) {
        return fields.getOrDefault(key, Field.LAST);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) cache.get(key);
    }

    public Map<String, Object> accepted() {
        return unmodifiableMap(cache);
    }

    public Stream<Map<String, Object>> history() {
        return layers.stream().
                map(Layer::changed);
    }

    private <L extends Layer> L newLayer(final Function<Surface, L> next) {
        return next.apply(new LayersSurface());
    }

    private final class LayersSurface
            implements Surface {
        @Override
        public void accept(final Layer layer) {
            layers.add(layer);
            merge(cache, layer.changed());
        }

        @Override
        public Map<String, Object> changed(final Layer layer) {
            final Map<String, Object> whatIf = new HashMap<>(cache);
            merge(whatIf, layer.changed());
            return unmodifiableMap(whatIf);
        }

        private void merge(final Map<String, Object> accepted,
                final Map<String, Object> changed) {
            changed.entrySet().
                    forEach(e -> mergeOne(accepted, e.getKey(),
                            e.getValue()));
        }

        private void mergeOne(final Map<String, Object> map, final String key,
                final Object value) {
            map.merge(key, value, ruleFor(key));
        }
    }
}
