package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.dnd.MagicItems.Attunement;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.Value.ofBoth;
import static hm.binkley.layers.rules.Rule.layerSet;
import static java.util.Collections.emptySet;

@MetaInfServices
public final class MagicItemsBaseRules
        implements BaseRule {
    @Override
    public Layer apply(final Layers.Surface layers) {
        final Layer layer = new Layer(layers, "Base rules for magic items");
        layer.put(Attunement.class, ofBoth(emptySet(),
                layerSet("Attunement", Attunement.class, 3)));
        return layer;
    }
}
