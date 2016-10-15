package hm.binkley.layers.dnd;

import hm.binkley.layers.BaseRule;
import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.abilityScoreIncrease;
import static hm.binkley.layers.dnd.Abilities.abilityScores;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FixMeUpTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(ScratchLayer::new,
                layers -> this.layers = layers);
    }

    @Test
    void shouldHaveNetStrengthIfBeltNotBetter() {
        firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(abilityScores(23, 8, 8, 8, 8, 8)).
                saveAndNext(MagicItems::beltOfHillGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 23, layers.get(STR));
    }

    @Test
    void shouldHaveNoStrengthBeforeAddingScores() {
        firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 0, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterGainingAbility() {
        firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 9, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBelt() {
        firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(MagicItems::beltOfHillGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 21, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBeltAfterGainingAbility() {
        firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(MagicItems::beltOfStoneGiantStrength).
                saveAndNext(abilityScoreIncrease(STR)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 23, layers.get(STR));
    }

    @Test
    @Disabled("Pending issue #9")
    void shouldHaveNetStrengthAfterRemovingBelt() {
        final Layer girdle = firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(MagicItems::beltOfFrostGiantStrength);
        girdle.saveAndNext(abilityScoreIncrease(STR)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 11, layers.get(STR));
    }
}
