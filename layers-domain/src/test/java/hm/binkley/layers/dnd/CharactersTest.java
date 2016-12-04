package hm.binkley.layers.dnd;

import hm.binkley.layers.LayersTestSupport;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.BaseRule;
import hm.binkley.layers.rules.BaseRule.BaseRulesLayer;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.Characters.NAME;
import static hm.binkley.layers.dnd.Characters.characterDescription;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CharactersTest
        extends LayersTestSupport<BaseRulesLayer> {
    CharactersTest() {
        super(BaseRule::baseRules);
    }

    @Test
    void shouldUseMostRecentName() {
        firstLayer().
                saveAndNext(characterDescription("Bob")).
                saveAndNext(characterDescription("Nancy")).
                saveAndNext(ScratchLayer::new);

        assertEquals("Nancy", layers().get(NAME));
    }
}
