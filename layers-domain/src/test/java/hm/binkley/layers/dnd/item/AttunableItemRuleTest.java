package hm.binkley.layers.dnd.item;

import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.BaseRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AttunableItemRuleTest {
    private AttunableItem<?> firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(BaseRule::baseRules, layers -> {}).
                saveAndNext(AmuletOfHealth::new).
                asThis();
    }

    @Test
    void shouldDisplayAttuned() {
        firstLayer.attuneSaveAndNext(ScratchLayer::new);

        assertTrue(firstLayer.toString().contains("(attuned)"));
    }

    @Test
    void shouldDisplayUnattuned() {
        firstLayer.saveAndNext(ScratchLayer::new);

        assertTrue(firstLayer.toString().contains("(unattuned)"));
    }
}