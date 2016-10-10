package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.Value.mostRecent;

@MetaInfServices
public final class BaseRuleCharacterDescription
        implements BaseRule {
    @Override
    public Layer apply(final Layers.Surface layers) {
        final Layer layer = new Layer(layers,
                "Base character description rules");
        for (final Characters key : Characters.values())
            layer.put(key, mostRecent(key, ""));
        return layer;
    }
}
