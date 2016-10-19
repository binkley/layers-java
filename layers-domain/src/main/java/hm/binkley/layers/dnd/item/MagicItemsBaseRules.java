package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.FullnessFunction.max;
import static hm.binkley.layers.rules.Rule.layerSet;
import static hm.binkley.layers.values.Value.ofBoth;
import static java.util.Collections.emptySet;

@MetaInfServices
public final class MagicItemsBaseRules
        implements BaseRule {
    @Override
    public Layer apply(final Surface layers) {
        final Layer layer = new Layer(layers, "Base rules for item items");
        layer.put(Attunement.class,
                ofBoth(emptySet(), layerSet(Attunement.class, max(3))));
        return layer;
    }
}
