package hm.binkley.layers;

import hm.binkley.layers.Layer.LayerView;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings("WeakerAccess")
@RequiredArgsConstructor(access = PRIVATE)
public final class Layers {
    private final List<Layer> layers;

    public static Layer firstLayer(final LayerMaker ctor,
            final Consumer<Layers> layersHolder) {
        final Layers layers = new Layers(new ArrayList<>());
        layersHolder.accept(layers);
        return ctor.apply(layers.new Surface());
    }

    public boolean isEmpty() {
        return !layers.stream().
                anyMatch(layer -> !layer.isEmpty());
    }

    public int size() {
        return (int) layers.stream().
                flatMap(layer -> layer.keys().stream()).
                distinct().
                count();
    }

    public Set<Object> keys() {
        return unmodifiableSet(layers.stream().
                flatMap(layer -> layer.keys().stream()).
                collect(toSet()));
    }

    public boolean containsKey(final Object key) {
        return streamFor(key).
                findAny().
                isPresent();
    }

    /** @todo Rethink tradeoff of no type token in arg vs safety */
    @SuppressWarnings("TypeParameterUnusedInFormals")
    public <T> T get(final Object key) {
        return this.<T>ruleValueFor(key).
                apply(this);
    }

    public Map<Object, Object> toMap() {
        return unmodifiableMap(keys().stream().
                map(key -> new SimpleImmutableEntry<>(key, get(key))).
                collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
    }

    public Stream<LayerView> history() {
        return layers.stream().
                map(Layer::view);
    }

    public <T> Stream<T> plainValuesFor(final Object key) {
        return this.<T>streamFor(key).
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
        public Layer saveAndNext(final Layer layer, final LayerMaker next) {
            layers.add(0, layer);
            return next.apply(this);
        }

        public void discard(final Layer discard) {
            layers.remove(discard);
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
        return IntStream.range(0, size).
                mapToObj(i -> (size - i) + ": " + layers.get(i)).
                collect(joining("\n"));
    }

    private <T> Value<T> ruleValueFor(final Object key) {
        return this.<T>streamFor(key).
                filter(value -> value.rule().isPresent()).
                findFirst().
                orElse(Value.mostRecent(key));
    }

    private <T> Stream<Value<T>> streamFor(final Object key) {
        return layers.stream().
                filter(layer -> layer.containsKey(key)).
                map(layer -> layer.<T>get(key));
    }
}
