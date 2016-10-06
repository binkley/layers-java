package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.Abilities.CHA;
import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.Abilities.DEX;
import static hm.binkley.layers.dnd.Abilities.INT;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.WIS;
import static hm.binkley.layers.dnd.Abilities.abilityScores;
import static hm.binkley.layers.dnd.Races.humanVariant;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RacesTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(Bases::baseRules,
                layers -> this.layers = layers);
    }

    @Test
    void shouldIncreaseAllAbilitiesForPlainHuman() {
        firstLayer.
                saveAndNext(Races::plainHuman).
                saveAndNext(abilityScores(15, 14, 13, 12, 10, 8)).
                saveAndNext(ScratchLayer::new);

        assertAll(() -> assertEquals((Integer) 16, layers.get(STR)),
                () -> assertEquals((Integer) 15, layers.get(DEX)),
                () -> assertEquals((Integer) 14, layers.get(CON)),
                () -> assertEquals((Integer) 13, layers.get(INT)),
                () -> assertEquals((Integer) 11, layers.get(WIS)),
                () -> assertEquals((Integer) 9, layers.get(CHA)));
    }

    @Test
    void shouldIncreaseAllAbilitiesForVariantHuman() {
        firstLayer.
                saveAndNext(humanVariant().withSTR().withCON()).
                saveAndNext(abilityScores(15, 14, 13, 12, 10, 8)).
                saveAndNext(ScratchLayer::new);

        assertAll(() -> assertEquals((Integer) 16, layers.get(STR)),
                () -> assertEquals((Integer) 14, layers.get(DEX)),
                () -> assertEquals((Integer) 14, layers.get(CON)),
                () -> assertEquals((Integer) 12, layers.get(INT)),
                () -> assertEquals((Integer) 10, layers.get(WIS)),
                () -> assertEquals((Integer) 8, layers.get(CHA)));
    }
}
