package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.Abilities.STR;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MagicItemsTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayersAndFirstLayer() {
        firstLayer = firstLayer(Bases::baseRules,
                layers -> this.layers = layers);
    }

    @Test
    void shouldGrant21StrengthFromHillGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfHillGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 21, layers.get(STR));
    }

    @Test
    void shouldGrant23StrengthFromStoneGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfStoneGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 23, layers.get(STR));
    }

    @Test
    void shouldGrant23StrengthFromFrostGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfFrostGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 23, layers.get(STR));
    }

    @Test
    void shouldGrant25StrengthFromFireGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfFireGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 25, layers.get(STR));
    }

    @Test
    void shouldGrant27StrengthFromCloudGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfCloudGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 27, layers.get(STR));
    }

    @Test
    void shouldGrant29StrengthFromStormGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfStormGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 29, layers.get(STR));
    }

    @Test
    void shouldCreateAdamantineArmor() {
        assertEquals("Adamantine Armor",
                firstLayer.saveAndNext(MagicItems::adamantineArmor).name());
    }
}
