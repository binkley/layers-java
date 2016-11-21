package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.BaseRule;
import hm.binkley.layers.set.LayerSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.item.Attuned.attune;
import static hm.binkley.layers.dnd.item.Attuned.detune;
import static hm.binkley.layers.dnd.item.Attunement.ATTUNED;
import static hm.binkley.layers.dnd.item.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.item.Type.WONDROUS_ITEM;
import static hm.binkley.layers.set.LayerSetCommand.add;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AttunedTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(BaseRule::baseRules,
                layers -> this.layers = layers);
    }

    @Test
    void shouldDisplayNicely() {
        final String display = new Attuned(null, add("Attune Bob",
                new MagicItem(null, "Bob", "Cool bob", WONDROUS_ITEM,
                        LEGENDARY, ATTUNED, "Some notes about Bob"))).
                toString();

        assertTrue(display.contains("Attune"));
    }

    @Test
    void shouldStartDetuned() {
        final AmuletOfHealth amuletOfHealth = firstLayer.
                saveAndNext(AmuletOfHealth::new);

        assertFalse(amuletOfHealth.isAttuned());
    }

    @Test
    void shouldAttune() {
        final AmuletOfHealth amuletOfHealth = firstLayer.
                saveAndNext(AmuletOfHealth::new);
        amuletOfHealth.saveAndNext(attune(amuletOfHealth)).
                saveAndNext(ScratchLayer::new);

        assertTrue(amuletOfHealth.isAttuned());
    }

    @Test
    void shouldDetune() {
        final AmuletOfHealth amuletOfHealth = firstLayer.
                saveAndNext(AmuletOfHealth::new);
        amuletOfHealth.saveAndNext(attune(amuletOfHealth)).
                saveAndNext(detune(amuletOfHealth)).
                saveAndNext(ScratchLayer::new);

        assertFalse(amuletOfHealth.isAttuned());
    }

    @Test
    void shouldDetuneAttunedItem() {
        final AmuletOfHealth amuletOfHealth = firstLayer.
                saveAndNext(AmuletOfHealth::new);
        amuletOfHealth.saveAndNext(attune(amuletOfHealth)).
                saveAndNext(detune(amuletOfHealth)).
                saveAndNext(ScratchLayer::new);

        assertTrue(layers.<LayerSet<MagicItem>>get(Attuned.class).isEmpty());
    }
}
