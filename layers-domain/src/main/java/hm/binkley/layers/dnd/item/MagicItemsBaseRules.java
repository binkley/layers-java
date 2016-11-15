package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.rules.Rule.layerSet;
import static hm.binkley.layers.set.FullnessFunction.max;

@MetaInfServices
public final class MagicItemsBaseRules
        implements BaseRule {
    @Override
    public Layer apply(final LayerSurface layers) {
        final Layer layer = new Layer(layers, "Base rules for magic items");
        layer.put(Attuned.class, layerSet(Attuned.class, max(3)));
        return layer;
    }
}
