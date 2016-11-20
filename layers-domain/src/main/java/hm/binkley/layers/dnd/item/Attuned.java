package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.set.LayerSetCommand;

import static hm.binkley.layers.set.LayerSetCommand.add;

public class Attuned
        extends Layer {
    public static LayerMaker<Attuned> attune(final MagicItem layer) {
        return layers -> new Attuned(layers,
                add("Attune " + layer.name(), layer));
    }

    public Attuned(final LayerSurface layers,
            final LayerSetCommand<MagicItem> magicItem) {
        super(layers, "Attuned");
        put(Attuned.class, magicItem);
    }

    @Override
    public String toString() {
        return name() + ": " + this.<LayerSetCommand<Layer>>get(Attuned.class).
                name();
    }
}
