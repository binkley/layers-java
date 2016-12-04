package hm.binkley.layers.dnd;

import hm.binkley.layers.LayersTestSupport;
import hm.binkley.layers.dnd.Proficiencies.ProficienciesLayer;
import hm.binkley.layers.rules.BaseRule;
import hm.binkley.layers.rules.BaseRule.BaseRulesLayer;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.Proficiencies.ACROBATICS;
import static hm.binkley.layers.dnd.Proficiencies.ATHLETICS;
import static hm.binkley.layers.dnd.Proficiencies.doubleProficiency;
import static hm.binkley.layers.dnd.Proficiencies.proficiencyBonus;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProficienciesTest
        extends LayersTestSupport<BaseRulesLayer> {
    ProficienciesTest() {
        super(BaseRule::baseRules);
    }

    @Test
    void shouldDoubleProficiencies() {
        final ProficienciesLayer layer = firstLayer().
                saveAndNext(BaseRule::baseRules).
                saveAndNext(proficiencyBonus(ACROBATICS, 1)).
                saveAndNext(proficiencyBonus(ATHLETICS, 1)).
                saveAndNext(doubleProficiency(ACROBATICS));

        assertEquals((Integer) 2, layers().whatIfWith(layer).get(ACROBATICS));
    }
}
