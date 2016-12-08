package hm.binkley.layers;

import hm.binkley.layers.rules.Rule;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static hm.binkley.layers.DisplayStyle.BRACES;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import static lombok.AccessLevel.PRIVATE;

public final class Layers {
    private final transient Map<Object, Object> cache = new LinkedHashMap<>();
    private final List<Layer<?>> layers;

    private Layers(final List<Layer<?>> layers) {
        this.layers = layers;
        updateCache();
    }

    @SuppressWarnings("unchecked")
    private void updateCache() {
        final Set<Object> updated = new HashSet<>();
        layers.stream().
                map(Layer::keys).
                flatMap(Collection::stream).
                distinct().
                sorted(Layers::classTokensFirst).
                peek(updated::add).
                forEach(key -> cache.put(key, value(key)));
        cache.keySet().retainAll(updated);
    }

    public static <L extends Layer<L>> L firstLayer(
            final Function<LayerSurface, L> ctor,
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
    public Layers whatIfWith(final Layer<?> layer) {
        final List<Layer<?>> scenario = new ArrayList<>();
        scenario.addAll(layers);
        scenario.add(layer);
        return new Layers(scenario);
    }

    @SuppressWarnings("WeakerAccess")
    public Layers whatIfWithout(final Layer<?> layer) {
        final List<Layer<?>> scenario = new ArrayList<>();
        scenario.addAll(layers);
        scenario.remove(layer);
        return new Layers(scenario);
    }

    @RequiredArgsConstructor(access = PRIVATE)
    public final class LayerSurface {
        public <L extends Layer<L>> L saveAndNext(final Layer<?> layer,
                final Function<LayerSurface, L> next) {
            layers.add(layer);
            updateCache();
            return next.apply(this);
        }

        @SuppressWarnings("TypeParameterUnusedInFormals")
        public <T> T get(final Object key) {
            return Layers.this.get(key);
        }
    }

    @RequiredArgsConstructor(access = PRIVATE)
    public final class RuleSurface {
        private final Layer<?> layer;
        private final Object key;

        public <T> Stream<T> values() {
            return allValues(layers.stream(), key);
        }

        public <T> Stream<T> reverseValues() {
            final int size = layers.size();
            return allValues(rangeClosed(1, size).
                    mapToObj(i -> layers.get(size - i)), key);
        }

        @SuppressWarnings("TypeParameterUnusedInFormals")
        public <S> S get(final Object key) {
            return Layers.this.get(key);
        }

        @SuppressWarnings("TypeParameterUnusedInFormals")
        public <R> R getWithout() {
            return whatIfWithout(layer).get(key);
        }

        public boolean contains(final Layer<?> layer) {
            return layers.contains(layer);
        }
    }

    @Override
    public String toString() {
        final int size = layers.size();
        return "All (" + size + "): " + BRACES.display(cache) + "\n" + range(
                0, size).
                mapToObj(i -> (size - i) + ": " + layers.get(size - i - 1)).
                collect(joining("\n"));
    }

    private static int classTokensFirst(final Object a, final Object b) {
        if (a instanceof Class)
            return b instanceof Class ? a.toString().compareTo(b.toString())
                    : -1;
        else
            return b instanceof Class ? 1
                    : a.toString().compareTo(b.toString());
    }

    @SuppressWarnings("TypeParameterUnusedInFormals")
    private <L extends Layer<L>> L ruleLayer(final Object key) {
        return this.<L>allRuleLayers(key).
                findFirst().
                orElseThrow(() -> new NoSuchElementException(
                        "No rule for key: " + key));
    }

    private <L extends Layer<L>> Object value(final Object key) {
        final L layer = ruleLayer(key);
        return layer.<Rule<?>>get(key).
                apply(new RuleSurface(layer, key));
    }

    @SuppressWarnings("unchecked")
    private <L extends Layer<L>> Stream<L> allRuleLayers(final Object key) {
        final int size = layers.size();
        return range(0, size).
                map(i -> size - i - 1).
                mapToObj(layers::get).
                filter(layer -> layer.containsKey(key)).
                filter(layer -> layer.get(key) instanceof Rule).
                map(layer -> (L) layer);
    }

    @SuppressWarnings("unchecked")
    private static <T> Stream<T> allValues(final Stream<Layer<?>> layers,
            final Object key) {
        return layers.
                filter(layer -> layer.containsKey(key)).
                map(layer -> layer.get(key)).
                filter(value -> !(value instanceof Rule)).
                map(value -> (T) value);
    }
}
