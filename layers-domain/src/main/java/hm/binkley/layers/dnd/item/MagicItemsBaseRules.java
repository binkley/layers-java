package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import hm.binkley.layers.set.FullnessFunction;
import hm.binkley.layers.set.LayerSetRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.rules.Rule.layerSet;

@MetaInfServices
public final class MagicItemsBaseRules
        implements BaseRule {
    @Override
    public BaseRulesLayer apply(final LayerSurface layers) {
        final BaseRulesLayer layer = new BaseRulesLayer(layers);
        layer.put(Attuned.class, attunedMagicItems());
        return layer;
    }

    private static <L extends MagicItem<L>> LayerSetRule<L>
    attunedMagicItems() {
        return layerSet(FullnessFunction.<L>max(3));
    }
}
