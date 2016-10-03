package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.Value;

import static hm.binkley.layers.Value.ofValue;

public enum AbilityScore {
    STR,
    DEX,
    CON,
    INT,
    WIS,
    CHA;

    public static Layer abilityScores(final Layers layers, final int _str,
            final int _dex, final int _con, final int _int, final int _wis,
            final int _cha) {
        final Layer layer = layers.newLayer(Layer::new);
        layer.put(STR, ofValue(_str));
        layer.put(DEX, ofValue(_dex));
        layer.put(CON, ofValue(_con));
        layer.put(INT, ofValue(_int));
        layer.put(WIS, ofValue(_wis));
        layer.put(CHA, ofValue(_cha));
        return layer;
    }

    public static Layer defaultRuleAbilityScores(final Layers layers) {
        final Layer layer = layers.newLayer(Layer::new);
        for (final AbilityScore ability : AbilityScore.values())
            layer.put(ability, Value.sumAll(ability));
        return layer;
    }
}
