package hm.binkley.layers;

import hm.binkley.layers.dnd.AbilityScore;
import hm.binkley.layers.dnd.CharacterDescription;
import hm.binkley.layers.dnd.ProficiencyBonus;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static hm.binkley.layers.Value.ofValue;
import static hm.binkley.layers.dnd.AbilityScore.CHA;
import static hm.binkley.layers.dnd.AbilityScore.CON;
import static hm.binkley.layers.dnd.AbilityScore.DEX;
import static hm.binkley.layers.dnd.AbilityScore.INT;
import static hm.binkley.layers.dnd.AbilityScore.STR;
import static hm.binkley.layers.dnd.AbilityScore.WIS;
import static hm.binkley.layers.dnd.AbilityScore.abilityScores;
import static hm.binkley.layers.dnd.AbilityScore.defaultRuleAbilityScores;
import static hm.binkley.layers.dnd.CharacterDescription.characterDescription;
import static hm.binkley.layers.dnd.ProficiencyBonus.ACROBATICS;
import static hm.binkley.layers.dnd.ProficiencyBonus.ATHLETICS;
import static hm.binkley.layers.dnd.ProficiencyBonus
        .defaultRuleProficiencyBonuses;
import static hm.binkley.layers.dnd.ProficiencyBonus.doubleProficiency;
import static hm.binkley.layers.dnd.ProficiencyBonus.proficiencyBonus;
import static java.lang.System.out;

@SuppressWarnings("WeakerAccess")
public final class Layers {
    private final Deque<Layer> layers = new ArrayDeque<>();

    public Layer newLayer(final Function<Layers, Layer> ctor) {
        return ctor.apply(this);
    }

    public void add(final Layer layer) {
        layers.push(layer);
    }

    public void remove(final Layer layer) {
        layers.remove(layer);
    }

    public <T> T get(final Object key) {
        return this.<T>ruleValuesFor(key).
                findFirst().
                orElse(Value.mostRecent(key)).
                apply(this);
    }

    public <T> Stream<T> valuesFor(final Object key) {
        return this.<T>streamFor(key).
                map(Value::value).
                filter(Optional::isPresent).
                map(Optional::get);
    }

    public <T> Stream<Value<T>> ruleValuesFor(final Object key) {
        return this.<T>streamFor(key).
                filter(value -> value.rule().isPresent());
    }

    @SuppressWarnings("unchecked")
    private <T> Stream<Value<T>> streamFor(final Object key) {
        return layers.stream().
                filter(layer -> layer.containsKey(key)).
                map(layer -> (Value<T>) layer.<T>get(key));
    }

    public static Layer plainHuman(final Layers layers) {
        final Layer layer = layers.newLayer(Layer::new);
        layer.put(STR, ofValue(1));
        layer.put(DEX, ofValue(1));
        layer.put(CON, ofValue(1));
        layer.put(INT, ofValue(1));
        layer.put(WIS, ofValue(1));
        layer.put(CHA, ofValue(1));
        return layer;
    }

    public static Layer beltOfGiantStrength(final Layers layers,
            final int _str) {
        final Layer layer = layers.newLayer(Layer::new);
        layer.put(STR, Value.ofBoth(_str, Rule.exactly()));
        return layer;
    }

    public static void main(final String... args) {
        final Layers layers = new Layers();

        layers.add(defaultRuleAbilityScores(layers));
        layers.add(defaultRuleProficiencyBonuses(layers));

        layers.add(characterDescription(layers, "Bob"));
        layers.add(abilityScores(layers, 8, 15, 14, 10, 13, 12));
        layers.add(plainHuman(layers));
        layers.add(proficiencyBonus(layers, ACROBATICS, 1));
        layers.add(proficiencyBonus(layers, ATHLETICS, 1));
        layers.add(doubleProficiency(layers, ACROBATICS));
        layers.add(beltOfGiantStrength(layers, 20));
        layers.add(abilityScores(layers, 1, 0, 0, 0, 0, 0));

        for (final CharacterDescription description : CharacterDescription
                .values())
            out.println(description + " = " + layers.get(description));

        for (final AbilityScore score : AbilityScore.values())
            out.println(score + " = " + layers.get(score));

        for (final ProficiencyBonus bonus : ProficiencyBonus.values())
            out.println(bonus + " = " + layers.get(bonus));
    }
}
