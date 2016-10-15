package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.BaseRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.Characters.NAME;
import static hm.binkley.layers.dnd.Characters.characterDescription;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CharactersTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(BaseRule::baseRules,
                layers -> this.layers = layers);
    }

    @Test
    void shouldUseMostRecentName() {
        firstLayer.
                saveAndNext(characterDescription("Bob")).
                saveAndNext(characterDescription("Nancy")).
                saveAndNext(ScratchLayer::new);

        assertEquals("Nancy", layers.get(NAME));
    }
}
