package hm.binkley.layers.dnd;

import hm.binkley.layers.BaseRule;
import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.Value.mostRecent;

@MetaInfServices
public final class CharactersBaseRules
        implements BaseRule {
    @Override
    public Layer apply(final Layers.Surface layers) {
        final Layer layer = new Layer(layers,
                "Base rules for character descriptions");
        for (final Characters key : Characters.values())
            layer.put(key, mostRecent(key, ""));
        return layer;
    }
}
