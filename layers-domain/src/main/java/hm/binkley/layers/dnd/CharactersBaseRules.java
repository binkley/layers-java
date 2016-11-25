package hm.binkley.layers.dnd;

import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.rules.Rule.mostRecent;

@MetaInfServices
public final class CharactersBaseRules
        implements BaseRule {
    @Override
    public BaseRulesLayer apply(final LayerSurface layers) {
        final BaseRulesLayer layer = new BaseRulesLayer(layers);
        for (final Characters key : Characters.values())
            layer.put(key, k -> mostRecent(""));
        return layer;
    }
}
