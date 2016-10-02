package hm.binkley.layers;

import hm.binkley.layers.dnd.AbilityScore;
import hm.binkley.layers.dnd.CharacterDescription;
import hm.binkley.layers.dnd.ProficiencyBonus;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static hm.binkley.layers.dnd.AbilityScore.CHA;
import static hm.binkley.layers.dnd.AbilityScore.CON;
import static hm.binkley.layers.dnd.AbilityScore.DEX;
import static hm.binkley.layers.dnd.AbilityScore.INT;
import static hm.binkley.layers.dnd.AbilityScore.STR;
import static hm.binkley.layers.dnd.AbilityScore.WIS;
import static hm.binkley.layers.dnd.AbilityScore.abilityScores;
import static hm.binkley.layers.dnd.CharacterDescription.characterDescription;
import static hm.binkley.layers.dnd.ProficiencyBonus.ACROBATICS;
import static hm.binkley.layers.dnd.ProficiencyBonus.ATHLETICS;
import static hm.binkley.layers.dnd.ProficiencyBonus.doubleProficiency;
import static hm.binkley.layers.dnd.ProficiencyBonus.proficiencyBonus;
import static java.lang.System.out;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
@SuppressWarnings("WeakerAccess")
public final class Layers {
    private final List<Layer> layers = new ArrayList<>();

    public void add(final Layer layer) {
        layers.add(0, layer);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final Object key) {
        return (T) findEntries(key).
                map(pair -> pair.getKey().value().apply(this)).
                findFirst().
                get();
    }

    public static Layer plainHuman() {
        final Layer layer = new Layer();
        layer.put(new SumAllKey(STR), 1);
        layer.put(new SumAllKey(DEX), 1);
        layer.put(new SumAllKey(CON), 1);
        layer.put(new SumAllKey(INT), 1);
        layer.put(new SumAllKey(WIS), 1);
        layer.put(new SumAllKey(CHA), 1);
        return layer;
    }

    public static Layer beltOfGiantStrength(final int _str) {
        final Layer layer = new Layer();
        layer.put(new FixedValueKey<>(STR), _str);
        return layer;
    }

    public Stream<Entry<Key, Object>> findEntries(final Object key) {
        return layers.stream().
                flatMap(layer -> layer.entrySet().stream()).
                filter(pair -> pair.getKey().key().equals(key));
    }

    public static void main(final String... args) {
        final Layers layers = new Layers();
        layers.add(characterDescription("Bob"));
        layers.add(abilityScores(8, 15, 14, 10, 13, 12));
        layers.add(plainHuman());
        layers.add(proficiencyBonus(ACROBATICS, 1));
        layers.add(proficiencyBonus(ATHLETICS, 1));
        layers.add(doubleProficiency(ACROBATICS));
        layers.add(beltOfGiantStrength(20));

        for (final CharacterDescription description : CharacterDescription
                .values())
            out.println(description + " = " + layers.get(description));

        for (final AbilityScore score : AbilityScore.values())
            out.println(score + " = " + layers.get(score));

        for (final ProficiencyBonus bonus : ProficiencyBonus.values())
            out.println(bonus + " = " + layers.get(bonus));
    }
}
