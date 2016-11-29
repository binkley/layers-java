package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.set.LayerSetCommand;

import static hm.binkley.layers.set.LayerSetCommand.add;
import static hm.binkley.layers.set.LayerSetCommand.remove;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class Attuned
        extends Layer<Attuned> {
    private final LayerSetCommand command;

    Attuned(final LayerSurface layers, final LayerSetCommand command) {
        super(layers, command.name());
        put(Attuned.class, command);
        this.command = command;
    }

    @Override
    public String toString() {
        return command.name();
    }

    public static LayerMaker<Attuned> attune(final AttunableItem layer) {
        return layers -> new Attuned(layers,
                add("Attune: " + layer.name(), layer));
    }

    public static LayerMaker<Attuned> detune(final AttunableItem layer) {
        return layers -> new Attuned(layers,
                remove("Detune: " + layer.name(), layer));
    }
}
