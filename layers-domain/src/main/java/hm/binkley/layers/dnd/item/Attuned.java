package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.set.LayerSetCommand;

import static hm.binkley.layers.set.LayerSetCommand.add;
import static hm.binkley.layers.set.LayerSetCommand.remove;

public class Attuned<L extends MagicItem<L>>
        extends Layer<Attuned<L>> {
    public static <L extends MagicItem<L>> LayerMaker<Attuned<L>> attune(
            final L layer) {
        return layers -> new Attuned<>(layers,
                add("Attune " + layer.name(), layer));
    }

    public static <L extends MagicItem<L>> LayerMaker<Attuned<L>> detune(
            final L layer) {
        return layers -> new Attuned<>(layers,
                remove("Detune " + layer.name(), layer));
    }

    public Attuned(final LayerSurface layers,
            final LayerSetCommand<L> command) {
        super(layers, "Attuned");
        put(Attuned.class, command);
    }

    @Override
    public String toString() {
        return name() + ": " + this.<LayerSetCommand<?>>get(Attuned.class).
                name();
    }
}
