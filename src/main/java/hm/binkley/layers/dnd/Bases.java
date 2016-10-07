package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.Value;

@SuppressWarnings("WeakerAccess")
public final class Bases {
    private Bases() {}

    public static Layer baseRules(final Surface layers) {
        return new Layer(layers, "Base rules").
                blend(Bases::baseRuleCharacterDescription).
                blend(Bases::baseRuleAbilityScores).
                blend(Bases::baseRuleProficiencyBonuses);
    }

    /** @todo Are all character values strings? */
    public static Layer baseRuleCharacterDescription(final Surface layers) {
        final Layer layer = new Layer(layers,
                "Base character description rules");
        for (final Characters key : Characters.values())
            layer.put(key, Value.mostRecent(key, ""));
        return layer;
    }

    public static Layer baseRuleAbilityScores(final Surface layers) {
        final Layer layer = new Layer(layers, "Base ability scores rules");
        for (final Abilities key : Abilities.values())
            layer.put(key, Value.sumAll(key));
        return layer;
    }

    public static Layer baseRuleProficiencyBonuses(final Surface layers) {
        final Layer layer = new Layer(layers, "Base proficiency bonus rules");
        for (final Proficiencies key : Proficiencies.values())
            layer.put(key, Value.sumAll(key));
        return layer;
    }
}
