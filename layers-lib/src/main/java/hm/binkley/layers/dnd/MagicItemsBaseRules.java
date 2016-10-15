package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerSet;
import hm.binkley.layers.Layers;
import hm.binkley.layers.Rule;
import hm.binkley.layers.Value;
import hm.binkley.layers.dnd.MagicItems.Attunement;
import org.kohsuke.MetaInfServices;

import java.util.Collection;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toCollection;

@MetaInfServices
public final class MagicItemsBaseRules
        implements BaseRule {
    @Override
    public Layer apply(final Layers.Surface layers) {
        final Layer layer = new Layer(layers, "Base rules for magic items");
        layer.put(Attunement.class,
                Value.ofBoth(emptySet(), new AttunementRule()));
        return layer;
    }

    private static class AttunementRule
            extends Rule<Set<Layer>> {
        private AttunementRule() {
            super("Attunement");
        }

        @Override
        public Set<Layer> apply(final Layers layers, final Layer layer,
                final Set<Layer> value) {
            final Set<Layer> attuned = new LayerSet() {
                @Override
                public boolean add(final Layer layer) {
                    if (3 == size())
                        throw new IllegalStateException();
                    return super.add(layer);
                }
            };
            return layers.<Set<Layer>>plainValuesFor(Attunement.class).
                    flatMap(Collection::stream).
                    collect(toCollection(() -> attuned));
        }
    }
}
