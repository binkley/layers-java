package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.Value.mostRecent;
import static java.util.Collections.emptySet;

@MetaInfServices
public final class BaseRuleMagicItems
        implements BaseRule {
    @Override
    public Layer apply(final Layers.Surface layers) {
        final Layer layer = new Layer(layers, "Base magic items rules");
        layer.put(MagicItems.Attunement.class,
                mostRecent(MagicItems.Attunement.class, emptySet()));
        return layer;
    }
}
