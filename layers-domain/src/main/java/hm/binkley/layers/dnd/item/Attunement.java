package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.set.LayerSetCommand;

import static hm.binkley.layers.set.LayerSetCommand.add;
import static hm.binkley.layers.set.LayerSetCommand.remove;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class Attunement
        extends Layer<Attunement> {
    private final LayerSetCommand command;

    Attunement(final LayerSurface layers, final LayerSetCommand command) {
        super(layers, command.name());
        put(Attunement.class, command);
        this.command = command;
    }

    @Override
    public String toString() {
        return command.name();
    }

    public static LayerMaker<Attunement> attune(final AttunementItem layer) {
        return layers -> new Attunement(layers,
                add("Attune: " + layer.name(), layer));
    }

    public static LayerMaker<Attunement> detune(final AttunementItem layer) {
        return layers -> new Attunement(layers,
                remove("Detune: " + layer.name(), layer));
    }
}
