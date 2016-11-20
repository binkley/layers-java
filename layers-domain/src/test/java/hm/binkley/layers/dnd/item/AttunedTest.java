package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.BaseRule;
import hm.binkley.layers.set.LayerSet;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.item.Attuned.attune;
import static hm.binkley.layers.dnd.item.Attuned.detune;
import static hm.binkley.layers.dnd.item.Attunement.ATTUNED;
import static hm.binkley.layers.dnd.item.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.item.Type.WONDROUS_ITEM;
import static hm.binkley.layers.set.LayerSetCommand.add;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AttunedTest {
    @Test
    void shouldDisplayNicely() {
        final String display = new Attuned(null, add("Attune Bob",
                new MagicItem(null, "Bob", "Cool bob", WONDROUS_ITEM,
                        LEGENDARY, ATTUNED, "Some notes about Bob"))).
                toString();

        assertTrue(display.contains("Attune"));
    }

    @Test
    void shouldDetuneAttunedItem() {
        final Layers[] layersHolder = new Layers[1];
        final Layer firstLayer = firstLayer(BaseRule::baseRules,
                layers -> layersHolder[0] = layers);
        final Layers layers = layersHolder[0];

        final AmuletOfHealth amuletOfHealth = firstLayer.
                saveAndNext(AmuletOfHealth::new);
        amuletOfHealth.saveAndNext(attune(amuletOfHealth)).
                saveAndNext(detune(amuletOfHealth)).
                saveAndNext(ScratchLayer::new);

        assertTrue(layers.<LayerSet<MagicItem>>get(Attuned.class).isEmpty());
    }
}
