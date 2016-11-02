package hm.binkley.layers.set;

import hm.binkley.layers.Layer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class LayerSetCommand<L extends Layer>
        implements Consumer<LayerSet<L>> {
    private final String name;
    private final BiConsumer<LayerSet<L>, L> command;
    private final L layer;

    public static <L extends Layer> LayerSetCommand<L> add(final String name,
            final L layer) {
        return new LayerSetCommand<>(name, LayerSet::add, layer);
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
}
