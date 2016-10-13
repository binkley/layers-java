package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.Value.sumAll;

@MetaInfServices
public final class AbilitiesBaseRules
        implements BaseRule {
    @Override
    public Layer apply(final Layers.Surface layers) {
        final Layer layer = new Layer(layers,
                "Base rules for ability scores");
        for (final Abilities key : Abilities.values())
            layer.put(key, sumAll(key));
        return layer;
    }
}
