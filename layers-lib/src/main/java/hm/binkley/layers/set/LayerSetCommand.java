package hm.binkley.layers.set;

import hm.binkley.layers.Layer;

import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.lang.String.format;

public final class LayerSetCommand<L extends Layer>
        implements Consumer<LayerSet<L>> {
    private final String name;
    private final BiConsumer<LayerSet<L>, L> command;
    private final L layer;

    public static <L extends Layer> LayerSetCommand<L> add(final String name,
            final L layer) {
        return new LayerSetCommand<>(name, LayerSet::add, layer);
    }

    public static <L extends Layer> LayerSetCommand<L> remove(
            final String name, final L layer) {
        return new LayerSetCommand<>(name, (set, l) -> {
            if (!set.remove(l))
                throw new NoSuchElementException(
                        format("%s not in set: %s", l.name(), set));
        }, layer);
    }

    public String name() { return name; }

    private LayerSetCommand(final String name,
            final BiConsumer<LayerSet<L>, L> command, final L layer) {
        this.name = name;
        this.command = command;
        this.layer = layer;
    }

    @Override
    public void accept(final LayerSet<L> set) {
        command.accept(set, layer);
    }

    @Override
    public String toString() {
        return name;
    }
}
