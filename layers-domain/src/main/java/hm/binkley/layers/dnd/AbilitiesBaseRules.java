package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.rules.Rule.sumAll;

@MetaInfServices
public final class AbilitiesBaseRules
        implements BaseRule {
    @Override
    public Layer apply(final LayerSurface layers) {
        final Layer layer = new Layer(layers,
                "Base rules for ability scores");
        for (final Abilities key : Abilities.values())
            layer.put(key, k -> sumAll());
        return layer;
    }
}
