package hm.binkley.layers.dnd;

import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.rules.Rule.sumAll;

@MetaInfServices
public final class AbilitiesBaseRules
        implements BaseRule {
    @Override
    public BaseRulesLayer apply(final LayerSurface layers) {
        final BaseRulesLayer layer = new BaseRulesLayer(layers);
        for (final Abilities key : Abilities.values())
            layer.put(key, k -> sumAll());
        return layer;
    }
}
