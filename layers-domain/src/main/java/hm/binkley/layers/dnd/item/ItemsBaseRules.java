package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import hm.binkley.layers.set.FullnessFunction;
import hm.binkley.layers.set.LayerSetRule;
import org.kohsuke.MetaInfServices;

import static hm.binkley.layers.dnd.item.SumFractionsRule.sumFractions;
import static hm.binkley.layers.dnd.item.Volume.SPACELESS;
import static hm.binkley.layers.dnd.item.Weight.WEIGHTLESS;
import static hm.binkley.layers.rules.Rule.layerSet;

@MetaInfServices
public final class ItemsBaseRules
        implements BaseRule {
    @Override
    public BaseRulesLayer apply(final LayerSurface layers) {
        return new BaseRulesLayer(layers).
                put(Weight.class, sumFractions("Sum weight", WEIGHTLESS)).
                put(Volume.class, sumFractions("Sum volume", SPACELESS)).
                put(Attunement.class, attunedMagicItems());
    }

    private static <L extends MagicItem<L>> LayerSetRule<L>
    attunedMagicItems() {
        return layerSet(FullnessFunction.<L>max(3));
    }
}
