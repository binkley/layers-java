package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.rules.Rule.layerSet;
import static hm.binkley.layers.set.FullnessFunction.max;
import static hm.binkley.layers.values.Value.ofRule;

@MetaInfServices
public final class MagicItemsBaseRules
        implements BaseRule {
    @Override
    public Layer apply(final Surface layers) {
        final Layer layer = new Layer(layers, "Base rules for magic items");
        layer.put(Attunement.class,
                ofRule(layerSet(Attunement.class, max(3))));
        return layer;
    }
}
