package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.rules.Rule;

import static hm.binkley.layers.dnd.item.Volume.SPACELESS;

final class SumVolumeRule
        extends Rule<Volume> {
    SumVolumeRule() {
        super("Sum volume");
    }

    @Override
    public Volume apply(final RuleSurface layers) {
        return layers.<Volume>values().
                reduce(SPACELESS, Volume::add);
    }
}
