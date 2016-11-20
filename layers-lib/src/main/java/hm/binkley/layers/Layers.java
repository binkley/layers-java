package hm.binkley.layers;

import hm.binkley.layers.values.Value;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static hm.binkley.layers.DisplayStyle.BRACES;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import static lombok.AccessLevel.PUBLIC;

public final class Layers {
    private final transient Map<Object, Object> cache = new LinkedHashMap<>();
    private final List<Layer> layers;

    private Layers(final List<Layer> layers) {
        this.layers = layers;
        updateCache();
    }

    private void updateCache() {
        final Map<Object, Object> updated = new LinkedHashMap<>();
        layers.stream().
                map(Layer::keys).
                flatMap(Collection::stream).
                distinct().
                forEach(key -> {
                    final Layer layer = ruleLayersFor(key).findFirst().get();
                    updated.put(key, layer.
                            get(key).
                            apply(new RuleSurface<>(key, layer)));
                });
        cache.clear();
        cache.putAll(updated);
    }

    public static <L extends Layer> L firstLayer(final LayerMaker<L> ctor,
            final Consumer<Layers> layersHolder) {
        final Layers layers = new Layers(new ArrayList<>());
        layersHolder.accept(layers);
        return ctor.apply(layers.new LayerSurface());
    }

    @SuppressWarnings("WeakerAccess")
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @SuppressWarnings("WeakerAccess")
    public int size() {
        return cache.size();
    }

    @SuppressWarnings("WeakerAccess")
    public Set<Object> keys() {
        return unmodifiableSet(cache.keySet());
    }

    @SuppressWarnings("WeakerAccess")
    public boolean containsKey(final Object key) {
        return cache.containsKey(key);
    }

    /** @todo Rethink tradeoff of no type token in arg vs safety */
    @SuppressWarnings({"TypeParameterUnusedInFormals", "unchecked"})
    public <T> T get(final Object key) {
        return (T) cache.computeIfAbsent(key, k -> {
            throw new NoSuchElementException(key.toString());
        });
    }

    @SuppressWarnings("WeakerAccess")
    public Map<Object, Object> toMap() {
        return unmodifiableMap(cache);
    }

    @SuppressWarnings("WeakerAccess")
    public Stream<LayerView> view() {
        return layers.stream().
                map(Layer::view);
    }

    public Stream<LayerView> view(final LayerFilter filter) {
        final int size = layers.size();
        return rangeClosed(1, size).
                mapToObj(i -> layers.get(size - i)).
                filter(filter).
                map(Layer::view);
    }

    @SuppressWarnings("WeakerAccess")
    public Layers whatIfWith(final Layer layer) {
        final List<Layer> scenario = new ArrayList<>();
        scenario.addAll(layers);
        scenario.add(layer);
        return new Layers(scenario);
    }

    @SuppressWarnings("WeakerAccess")
    public Layers whatIfWithout(final Layer layer) {
        final List<Layer> scenario = new ArrayList<>();
        scenario.addAll(layers);
        scenario.remove(layer);
        return new Layers(scenario);
    }

    public final class LayerSurface {
        public <L extends Layer> L saveAndNext(final Layer layer,
                final LayerMaker<L> next) {
            layers.add(layer);
            updateCache();
            return next.apply(this);
        }
    }

    /** @todo Hide constructor from public - tests use this */
    @RequiredArgsConstructor(access = PUBLIC)
    public final class RuleSurface<T> {
        private final Object key;
        private final Layer currentLayer;

        public Stream<T> plainValuesFirstToLastFor(final Object key) {
            return plainValuesFor(layers.stream(), key);
        }

        public Stream<T> plainValuesLastToFirstFor(final Object key) {
            final int size = layers.size();
            return plainValuesFor(rangeClosed(1, size).
                    mapToObj(i -> layers.get(size - i)), key);
        }

        public Object key() {
            return key;
        }

        public T valueWithout() {
            return whatIfWithout(currentLayer).get(key);
        }
    }

    @Override
    public String toString() {
        final int size = layers.size();
        return "All (" + layers.size() + "): " + BRACES.display(cache) + "\n"
                + range(0, size).
                mapToObj(i -> i + ": " + layers.get(size - i - 1)).
                collect(joining("\n"));
    }

    private Stream<Layer> ruleLayersFor(final Object key) {
        final int size = layers.size();
        return range(0, size).
                map(i -> size - i - 1).
                mapToObj(layers::get).
                filter(layer -> layer.containsKey(key)).
                filter(layer -> layer.get(key).rule().isPresent());
    }

    private static <T, R> Stream<T> plainValuesFor(final Stream<Layer> layers,
            final Object key) {
        return layers.
                filter(layer -> layer.containsKey(key)).
                map(layer -> layer.<T, R>get(key)).
                map(Value::value).
                filter(Optional::isPresent).
                map(Optional::get);
    }
}
