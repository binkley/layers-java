package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.rules.Rule.sumAll;

@MetaInfServices
public final class ProficienciesBaseRules
        implements BaseRule {
    @Override
    public Layer apply(final LayerSurface layers) {
        final Layer layer = new Layer(layers, "Base rules for proficiency");
        for (final Proficiencies key : Proficiencies.values())
            layer.put(key, k -> sumAll());
        return layer;
    }
}
