package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.rules.Rule.mostRecent;

@MetaInfServices
public final class CharactersBaseRules
        implements BaseRule {
    @Override
    public Layer apply(final LayerSurface layers) {
        final Layer layer = new Layer(layers,
                "Base rules for character descriptions");
        for (final Characters key : Characters.values())
            layer.put(key, k -> mostRecent(""));
        return layer;
    }
}
