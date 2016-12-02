package hm.binkley.layers.set;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.RuleSurface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.NoSuchElementException;
import java.util.function.BiConsumer;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Accessors(fluent = true)
@RequiredArgsConstructor(access = PRIVATE)
public final class LayerSetCommand<L extends Layer<L>>
        implements BiConsumer<RuleSurface, LayerSet<L>> {
    @Getter
    private final String name;
    private final BiConsumer<LayerSet<L>, L> command;
    private final L layer;

    public static <L extends Layer<L>> LayerSetCommand<L> add(
            final String name, final L layer) {
        return new LayerSetCommand<>(name, LayerSet::add, layer);
    }

    public static <L extends Layer<L>> LayerSetCommand<L> add(final L layer) {
        return add("Add: " + layer.name(), layer);
    }

    public static <L extends Layer<L>> LayerSetCommand<L> remove(
            final String name, final L layer) {
        return new LayerSetCommand<>(name, (set, l) -> {
            if (!set.remove(l))
                throw new NoSuchElementException(
                        format("%s not in set: %s", l.name(), set));
        }, layer);
    }

    public static <L extends Layer<L>> LayerSetCommand<L> remove(
            final L layer) {
        return remove("Remove: " + layer.name(), layer);
    }

    @Override
    public void accept(final RuleSurface layers,
            final LayerSet<L> set) {
        if (layers.contains(layer))
            command.accept(set, layer);
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }
}
