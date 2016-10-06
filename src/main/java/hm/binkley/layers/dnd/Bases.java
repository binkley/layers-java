package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.Value;

public final class Bases {
    private Bases() {}

    public static Layer baseRules(final Surface layers) {
        return new Layer(layers, "Base rules").
                blend(Bases::baseRuleAbilityScores).
                blend(Bases::baseRuleProficiencyBonuses);
    }

    public static Layer baseRuleAbilityScores(final Surface layers) {
        final Layer layer = new Layer(layers, "Base ability scores rules");
        for (final Abilities ability : Abilities.values())
            layer.put(ability, Value.sumAll(ability));
        return layer;
    }

    public static Layer baseRuleProficiencyBonuses(final Surface layers) {
        final Layer layer = new Layer(layers, "Base proficiency bonus rules");
        for (final Proficiencies proficiency : Proficiencies.values())
            layer.put(proficiency, Value.sumAll(proficiency));
        return layer;
    }
}
