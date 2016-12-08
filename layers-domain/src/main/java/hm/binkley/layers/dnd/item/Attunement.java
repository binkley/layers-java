package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.set.LayerSetCommand;

import java.util.function.Function;

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

    public static Function<LayerSurface, Attunement> attune(
            final AttunementItem layer) {
        return layers -> new Attunement(layers,
                add("Attune: " + layer.name(), layer));
    }

    public static Function<LayerSurface, Attunement> detune(
            final AttunementItem layer) {
        return layers -> new Attunement(layers,
                remove("Detune: " + layer.name(), layer));
    }
}
