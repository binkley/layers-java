package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.SumAllKey;

public enum AbilityScore {
    STR, DEX, CON, INT, WIS, CHA;

    public static Layer abilityScores(final int _str, final int _dex,
            final int _con, final int _int, final int _wis, final int _cha) {
        final Layer layer = new Layer();
        layer.put(new SumAllKey(STR), _str);
        layer.put(new SumAllKey(DEX), _dex);
        layer.put(new SumAllKey(CON), _con);
        layer.put(new SumAllKey(INT), _int);
        layer.put(new SumAllKey(WIS), _wis);
        layer.put(new SumAllKey(CHA), _cha);
        return layer;
    }
}
