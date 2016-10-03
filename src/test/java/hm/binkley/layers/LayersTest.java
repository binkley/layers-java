package hm.binkley.layers;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.beltOfGiantStrength;
import static hm.binkley.layers.dnd.AbilityScore.STR;
import static hm.binkley.layers.dnd.AbilityScore.abilityScores;
import static hm.binkley.layers.dnd.AbilityScore.defaultRuleAbilityScores;
import static hm.binkley.layers.dnd.CharacterDescription.NAME;
import static hm.binkley.layers.dnd.CharacterDescription.characterDescription;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LayersTest {
    @Test
    void shouldHaveNoStrengthBeforeAddingScores() {
        final Layers layers = new Layers();
        layers.add(defaultRuleAbilityScores());

        assertEquals((Integer) 0, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterGainingAbility() {
        final Layers layers = new Layers();
        layers.add(defaultRuleAbilityScores());
        layers.add(abilityScores(8, 15, 14, 10, 13, 12));
        layers.add(abilityScores(1, 0, 0, 0, 0, 0));

        assertEquals((Integer) 9, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBelt() {
        final Layers layers = new Layers();
        layers.add(defaultRuleAbilityScores());
        layers.add(abilityScores(8, 15, 14, 10, 13, 12));
        layers.add(beltOfGiantStrength(20));

        assertEquals((Integer) 20, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBeltAfterGainingAbility() {
        final Layers layers = new Layers();
        layers.add(defaultRuleAbilityScores());
        layers.add(abilityScores(8, 15, 14, 10, 13, 12));
        layers.add(beltOfGiantStrength(20));
        layers.add(abilityScores(1, 0, 0, 0, 0, 0));

        assertEquals((Integer) 20, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterRemovingBelt() {
        final Layers layers = new Layers();
        layers.add(defaultRuleAbilityScores());
        layers.add(abilityScores(8, 15, 14, 10, 13, 12));
        final Layer girdle = beltOfGiantStrength(20);
        layers.add(girdle);
        layers.add(abilityScores(1, 0, 0, 0, 0, 0));
        layers.remove(girdle);

        assertEquals((Integer) 9, layers.get(STR));
    }

    @Test
    void shouldHaveMostRecentName() {
        final Layers layers = new Layers();
        layers.add(characterDescription("Bob"));
        layers.add(characterDescription("Nancy"));

        assertEquals("Nancy", layers.get(NAME));
    }
}
