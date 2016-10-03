package hm.binkley.layers;

import hm.binkley.layers.dnd.AbilityScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.beltOfGiantStrength;
import static hm.binkley.layers.Layers.newLayers;
import static hm.binkley.layers.dnd.AbilityScore.STR;
import static hm.binkley.layers.dnd.AbilityScore.abilityScores;
import static hm.binkley.layers.dnd.CharacterDescription.NAME;
import static hm.binkley.layers.dnd.CharacterDescription.characterDescription;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LayersTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        layers = newLayers(AbilityScore::baseRuleAbilityScores,
                layer -> firstLayer = layer);
    }

    @Test
    void shouldHaveNoStrengthBeforeAddingScores() {
        firstLayer.commit(Layer::new);

        assertEquals((Integer) 0, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterGainingAbility() {
        firstLayer.
                commit(abilityScores(8, 15, 14, 10, 13, 12)).
                commit(abilityScores(1, 0, 0, 0, 0, 0)).
                commit(Layer::new);

        assertEquals((Integer) 9, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBelt() {
        firstLayer.
                commit(abilityScores(8, 15, 14, 10, 13, 12)).
                commit(abilityScores(1, 0, 0, 0, 0, 0)).
                commit(beltOfGiantStrength(20)).
                commit(Layer::new);

        assertEquals((Integer) 20, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBeltAfterGainingAbility() {
        firstLayer.
                commit(abilityScores(8, 15, 14, 10, 13, 12)).
                commit(abilityScores(1, 0, 0, 0, 0, 0)).
                commit(beltOfGiantStrength(20)).
                commit(abilityScores(1, 0, 0, 0, 0, 0)).
                commit(Layer::new);

        assertEquals((Integer) 20, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterRemovingBelt() {
        final Layer girdle = firstLayer.
                commit(abilityScores(8, 15, 14, 10, 13, 12)).
                commit(abilityScores(1, 0, 0, 0, 0, 0)).
                commit(beltOfGiantStrength(20));
        girdle.commit(abilityScores(1, 0, 0, 0, 0, 0)).
                commit(Layer::new);
        girdle.rollback();

        assertEquals((Integer) 10, layers.get(STR));
    }

    @Test
    void shouldHaveMostRecentName() {
        firstLayer.
                commit(characterDescription("Bob")).
                commit(characterDescription("Nancy")).
                commit(Layer::new);

        assertEquals("Nancy", layers.get(NAME));
    }
}
