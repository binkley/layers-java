package hm.binkley.layers;

import hm.binkley.layers.values.Value;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static hm.binkley.layers.DisplayStyle.BRACES;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

@SuppressWarnings("WeakerAccess")
public final class Layers {
    private final transient Map<Object, Object> cache = new LinkedHashMap<>();
    private final List<Layer> layers;

    private Layers(final List<Layer> layers) {
        this.layers = layers;
        updateCache();
    }

    /** @todo Should update for one key, not all? */
    private void updateCache() {
        // TODO: Test to show compute-then-update works
        // This ensures 1) rules see a complete cache, not partially updated
        // 2) rules relying on other values work against prior cache
        final Map<Object, Object> updated = new LinkedHashMap<>();
        layers.forEach(layer -> layer.keys().forEach(key -> {
            final Value<Object, Object> value = layer.get(key);
            value.rule().
                    map(rule -> value.apply(this, layer)).
                    map(result -> updated.putIfAbsent(key, result)).
                    isPresent();
        }));
        cache.clear();
        cache.putAll(updated);
    }

    public static <L extends Layer> L firstLayer(final LayerMaker<L> ctor,
            final Consumer<Layers> layersHolder) {
        final Layers layers = new Layers(new ArrayList<>());
        layersHolder.accept(layers);
        return ctor.apply(layers.new Surface());
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public int size() {
        return cache.size();
    }

    public Set<Object> keys() {
        return unmodifiableSet(cache.keySet());
    }

    public boolean containsKey(final Object key) {
        return cache.containsKey(key);
    }

    /** @todo Rethink tradeoff of no type token in arg vs safety */
    @SuppressWarnings({"TypeParameterUnusedInFormals", "unchecked"})
    public <T> T get(final Object key) {
        return (T) cache.get(key);
    }

    public Map<Object, Object> toMap() {
        return unmodifiableMap(cache);
    }

    public Stream<LayerView> view() {
        return layers.stream().
                map(Layer::view);
    }

    /** @todo filter can call mutators */
    public Stream<LayerView> view(final LayerFilter filter) {
        return layers.stream().
                filter(filter).
                map(Layer::view);
    }

    public <T, R> Stream<T> plainValuesFor(final Object key) {
        return this.<T, R>streamFor(key).
                map(Value::value).
                filter(Optional::isPresent).
                map(Optional::get);
    }

    public Layers whatIfWith(final Layer layer) {
        final List<Layer> scenario = new ArrayList<>();
        scenario.addAll(layers);
        scenario.add(0, layer);
        return new Layers(scenario);
    }

    public Layers whatIfWithout(final Layer layer) {
        final List<Layer> scenario = new ArrayList<>();
        scenario.addAll(layers);
        scenario.remove(layer);
        return new Layers(scenario);
    }

    public final class Surface {
        public <L extends Layer> L saveAndNext(final Layer layer,
                final LayerMaker<L> next) {
            layers.add(0, layer);
            updateCache();
            return next.apply(this);
        }

        public Layers whatIfWith(final Layer layer) {
            return Layers.this.whatIfWith(layer);
        }

        public Layers whatIfWithout(final Layer layer) {
            return Layers.this.whatIfWithout(layer);
        }
    }

    @Override
    public String toString() {
        final int size = layers.size();
        return "All (" + layers.size() + "): " + BRACES.display(cache) + "\n"
                + range(0, size).
                mapToObj(i -> (size - i) + ": " + layers.get(i)).
                collect(joining("\n"));
    }

    private <T, R> Stream<Value<T, R>> streamFor(final Object key) {
        return layers.stream().
                filter(layer -> layer.containsKey(key)).
                map(layer -> layer.<T, R>get(key));
    }
}
